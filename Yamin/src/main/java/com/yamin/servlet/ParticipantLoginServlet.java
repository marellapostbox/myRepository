package com.yamin.servlet;

import com.yamin.model.Participant;
import com.yamin.repo.Repository;

 import jakarta.servlet.ServletException;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class ParticipantLoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("participantLogin.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            Participant p = Repository.participants().findByEmailAndPassword(email, password);
            if (p != null) {
                HttpSession s = req.getSession(true);
                s.setAttribute("participant", p);
                s.setAttribute("email", email);
                // redirect to participant's own dashboard: allow to add training
                resp.sendRedirect(req.getContextPath() + "/participants");
            } else {
                resp.sendRedirect(req.getContextPath() + "/participant-login?error=1");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
