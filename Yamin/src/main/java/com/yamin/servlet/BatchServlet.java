package com.yamin.servlet;

import com.yamin.model.Batch;
import com.yamin.repo.Repository;

 import jakarta.servlet.ServletException;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BatchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Batch b = Repository.batches().findById(id);
                req.setAttribute("batches", b != null ? java.util.Arrays.asList(b) : java.util.Collections.emptyList());
            } else {
                List<Batch> list = Repository.batches().findAll();
                req.setAttribute("batches", list);
            }
            req.getRequestDispatcher("WEB-INF/jsp/listBatches.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Support method override via _method
        String method = req.getParameter("_method");
        if (method != null) {
            if (method.equalsIgnoreCase("PUT")) { doPut(req, resp); return; }
            if (method.equalsIgnoreCase("DELETE")) { doDelete(req, resp); return; }
        }

        try {
            String name = req.getParameter("name");
            String timing = req.getParameter("timing");
            Batch b = new Batch();
            b.setName(name);
            b.setTiming(timing);
            Repository.batches().create(b);
            resp.sendRedirect(req.getContextPath() + "/batches");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String timing = req.getParameter("timing");
            Batch b = new Batch();
            b.setId(id);
            b.setName(name);
            b.setTiming(timing);
            Repository.batches().update(b);
            resp.sendRedirect(req.getContextPath() + "/batches");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Repository.batches().delete(id);
            resp.sendRedirect(req.getContextPath() + "/batches");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
