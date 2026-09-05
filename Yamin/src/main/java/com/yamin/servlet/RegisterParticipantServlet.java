package com.yamin.servlet;

import com.yamin.model.Participant;
import com.yamin.repo.Repository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegisterParticipantServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String batchIdS = req.getParameter("batchId");

            // basic validation
            if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?error=missing#register");
                return;
            }

            // check duplicate email
            if (Repository.participants().findByEmail(email) != null) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?error=exists#register");
                return;
            }

            Participant p = new Participant();
            p.setName(name);
            p.setEmail(email);
            p.setPassword(password);
            if (batchIdS != null && !batchIdS.isEmpty()) p.setBatchId(Integer.parseInt(batchIdS));

            Repository.participants().create(p);

            resp.sendRedirect(req.getContextPath() + "/index.jsp?registered=1#register");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
