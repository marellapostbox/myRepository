<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yamin.repo.Repository, com.yamin.model.Batch, com.yamin.model.Participant, java.util.List" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Add Participant</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <%
      // support add and edit in same JSP
      String idParam = request.getParameter("id");
      Participant existing = null;
      if (idParam != null && !idParam.isEmpty()) {
        try { existing = Repository.participants().findById(Integer.parseInt(idParam)); } catch (Exception ex) { existing = null; }
      }
    %>

    <h2><%= (existing != null) ? "Edit Participant" : "Add Participant" %></h2>
    <form method="post" action="participants">
      <% if (existing != null) { %>
        <input type="hidden" name="_method" value="PUT">
        <input type="hidden" name="id" value="<%= existing.getId() %>">
      <% } %>
      <label>Name: <input type="text" name="name" value="<%= existing != null ? existing.getName() : "" %>" required></label>
      <label>Email: <input type="email" name="email" value="<%= existing != null ? existing.getEmail() : "" %>" required></label>
      <label>Password: <input type="password" name="password" <%= existing != null ? "" : "required" %>></label>
   <%
      List<Batch> batches = null;
      try {
        batches = Repository.batches().findAll();
        if (batches == null) batches = java.util.Collections.emptyList();
        out.println("<label>Batch: <select name=\"batchId\">\n");
        out.println("<option value=\"\">-- choose a batch (optional) --</option>\n");
        for (Batch b : batches) {
          String sel = "";
          if (existing != null && existing.getBatchId() != null && existing.getBatchId().intValue() == b.getId()) sel = " selected";
          out.println("<option value=\"" + b.getId() + "\"" + sel + ">" + b.getName() + " (" + b.getTiming() + ")</option>\n");
        }
        out.println("</select></label>\n");
      } catch (Exception ex) {
        batches = java.util.Collections.emptyList();
      }
      %>
      <button type="submit"><%= (existing != null) ? "Update Participant" : "Add Participant" %></button>
    </form>
    <p><a class="btn" href="participants">Back to participants</a></p>
  </div>
</body>
</html>
