package com.yamin.servlet;

import com.yamin.dao.AdminDAO;
import com.yamin.model.Admin;

 import jakarta.servlet.ServletException;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {
    private final AdminDAO adminDAO = new AdminDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("adminLogin.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        try {
            boolean ok = adminDAO.verifyPassword(user, pass);
            if (ok) {
                Admin a = adminDAO.findByUsername(user);
                HttpSession s = req.getSession(true);
                s.setAttribute("admin", a);
                resp.sendRedirect(req.getContextPath() + "/addBatch.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin-login?error=1");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
