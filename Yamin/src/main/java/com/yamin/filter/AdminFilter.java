package com.yamin.filter;

import com.yamin.model.Admin;

 import jakarta.servlet.*;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();
        String path = req.getRequestURI();

        // For the addBatch.jsp page we require admin even for GET (it's the admin form)
        boolean isAddBatchPage = path != null && path.endsWith("/addBatch.jsp");

        HttpSession session = req.getSession(false);
        Object admin = session != null ? session.getAttribute("admin") : null;

        // Allow safe GET requests for public listing endpoints (e.g., /batches, /participants),
        // but require admin for state-changing methods (POST/PUT/DELETE) or for admin pages like addBatch.jsp
        if (!isAddBatchPage && "GET".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        // For all other cases (non-GET or admin-only pages) require an Admin in session
        if (admin instanceof Admin) {
            chain.doFilter(request, response);
        } else {
            String loginUrl = req.getContextPath() + "/admin-login";
            resp.sendRedirect(loginUrl);
        }
    }

    @Override
    public void destroy() { }
}
