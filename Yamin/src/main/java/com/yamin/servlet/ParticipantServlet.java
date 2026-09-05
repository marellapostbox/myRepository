package com.yamin.servlet;

import com.yamin.model.Participant;
import com.yamin.repo.Repository;

 import jakarta.servlet.ServletException;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ParticipantServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String batchIdParam = req.getParameter("batchId");
        try {
            System.out.println("ParticipantServlet: doGet called with email=" + email);
            System.out.println("ParticipantServlet: doGet called with batchId=" + batchIdParam);

            // If a batchId is provided, show participants for that batch
            if (batchIdParam != null && !batchIdParam.isEmpty()) {
                int batchId = Integer.parseInt(batchIdParam);

                // Determine if the requester is an admin or a logged-in participant
                HttpSession s = req.getSession(false);
                Object admin = s != null ? s.getAttribute("admin") : null;
                Participant sessionParticipant = s != null ? (Participant) s.getAttribute("participant") : null;

                List<Participant> list;
                if (admin != null) {
                    // Admins can see all participants for the batch
                    list = Repository.participants().findByBatchId(batchId);
                } else if (sessionParticipant != null) {
                    // Logged-in participants should NOT see other participants.
                    // If the requested batch matches their own batch, only show themselves;
                    // otherwise show an empty list (or you may choose to redirect/deny access).
                    if (sessionParticipant.getBatchId() != null && sessionParticipant.getBatchId() == batchId) {
                        list = java.util.Collections.singletonList(sessionParticipant);
                    } else {
                        list = java.util.Collections.emptyList();
                    }
                } else {
                    // Unauthenticated users: preserve existing behaviour and show all
                    list = Repository.participants().findByBatchId(batchId);
                }

                req.setAttribute("participants", list);
                // also provide batch details (name, timing)
                com.yamin.model.Batch batch = Repository.batches().findById(batchId);
                req.setAttribute("batch", batch);
                // also provide all batches for the dropdown in the JSP
                List<com.yamin.model.Batch> allBatches = Repository.batches().findAll();
                req.setAttribute("batches", allBatches);
                req.getRequestDispatcher("WEB-INF/jsp/participantsInBatch.jsp").forward(req, resp);
                return;
            }

            // If email not provided, try to get from session (logged-in user)
            if (email == null || email.isEmpty()) {
                HttpSession s = req.getSession(false);
                if (s != null) {
                    email = (String) s.getAttribute("email");
                    System.out.println("ParticipantServlet: doGet retrieved email from session: " + email);
                }
            }

            if (email != null && !email.isEmpty()) {
                // Show the single participant matching the email, but apply access control:
                // - Admins can view any participant
                // - Logged-in participants can view participants only if they belong to the same batch
                // - Unauthenticated users preserve existing behaviour and can view the participant
                HttpSession s = req.getSession(false);
                Object admin = s != null ? s.getAttribute("admin") : null;
                Participant sessionParticipant = s != null ? (Participant) s.getAttribute("participant") : null;

                Participant target = Repository.participants().findByEmail(email);
                boolean allowed = false;
                if (admin != null) {
                    allowed = true;
                } else if (sessionParticipant != null) {
                    if (target != null && target.getBatchId() != null && sessionParticipant.getBatchId() != null
                            && target.getBatchId().intValue() == sessionParticipant.getBatchId().intValue()) {
                        allowed = true;
                    }
                    // also allow participant to view their own record even if batchId is null
                    if (!allowed && sessionParticipant.getEmail() != null && sessionParticipant.getEmail().equalsIgnoreCase(email)) {
                        allowed = true;
                    }
                } else {
                    // unauthenticated: preserve existing behaviour
                    allowed = true;
                }

                if (allowed) {
                    req.setAttribute("participant", target);
                    System.out.println("ParticipantServlet: doGet found participant with email=" + email);
                } else {
                    // Not allowed to view this participant
                    req.setAttribute("participant", null);
                    req.setAttribute("participants", java.util.Collections.emptyList());
                    System.out.println("ParticipantServlet: doGet access denied for email=" + email);
                }
            } else {
                // No email or batch specified: list all participants (admins & unauthenticated preserved)
                // If a logged-in participant without admin, show only participants in their own batch
                HttpSession s = req.getSession(false);
                Object admin = s != null ? s.getAttribute("admin") : null;
                Participant sessionParticipant = s != null ? (Participant) s.getAttribute("participant") : null;

                List<Participant> list;
                if (admin != null) {
                    list = Repository.participants().findAll();
                } else if (sessionParticipant != null) {
                    // participants should see other participants in their own batch only
                    if (sessionParticipant.getBatchId() != null) {
                        list = Repository.participants().findByBatchId(sessionParticipant.getBatchId());
                    } else {
                        list = java.util.Collections.singletonList(sessionParticipant);
                    }
                } else {
                    // unauthenticated: preserve original behaviour
                    list = Repository.participants().findAll();
                }

                req.setAttribute("participants", list);
                System.out.println("ParticipantServlet: doGet found " + (list != null ? list.size() : 0) + " participants");
            }

            req.getRequestDispatcher("WEB-INF/jsp/listParticipants.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if (method != null) {
            if (method.equalsIgnoreCase("PUT")) { doPut(req, resp); return; }
            if (method.equalsIgnoreCase("DELETE")) { doDelete(req, resp); return; }
        }

        try {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String batchIdS = req.getParameter("batchId");
            Participant p = new Participant();
            p.setName(name);
            p.setEmail(email);
            p.setPassword(password);
            if (batchIdS != null && !batchIdS.isEmpty()) p.setBatchId(Integer.parseInt(batchIdS));
            Repository.participants().create(p);
            resp.sendRedirect(req.getContextPath() + "/participants");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String batchIdS = req.getParameter("batchId");
            Participant p = new Participant();
            p.setId(id);
            p.setName(name);
            p.setEmail(email);
            p.setPassword(password);
            if (batchIdS != null && !batchIdS.isEmpty()) p.setBatchId(Integer.parseInt(batchIdS));
            Repository.participants().update(p);
            resp.sendRedirect(req.getContextPath() + "/participants");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Repository.participants().delete(id);
            resp.sendRedirect(req.getContextPath() + "/participants");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
