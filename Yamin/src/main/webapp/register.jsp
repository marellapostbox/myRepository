<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yamin.repo.Repository, com.yamin.model.Batch, java.util.List" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Participant Registration</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h1>Participant Registration</h1>
    <p>Fill in your details to register as a participant.</p>
    <%
      List<Batch> batches = null;
      try {
        batches = Repository.batches().findAll();
        // If no batches exist yet, create a set of default slots so users can register
        if (batches == null || batches.isEmpty()) {
          com.yamin.model.Batch b1 = new com.yamin.model.Batch(); b1.setName("Early Morning"); b1.setTiming("06:00-07:00"); b1.setName("Alice"); Repository.batches().create(b1);
          com.yamin.model.Batch b2 = new com.yamin.model.Batch(); b2.setName("Morning"); b2.setTiming("08:00-09:00"); b2.setName("Bob"); Repository.batches().create(b2);
          com.yamin.model.Batch b3 = new com.yamin.model.Batch(); b3.setName("Evening"); b3.setTiming("18:00-19:00"); b3.setName("Carol"); Repository.batches().create(b3);
          // re-fetch after seeding
          batches = Repository.batches().findAll();
        }
      } catch (Exception ex) {
        batches = java.util.Collections.emptyList();
      }
      String reg = request.getParameter("registered");
      String err = request.getParameter("error");
    %>

    <form method="post" action="register">
      <label>Name: <input type="text" name="name" required></label>
      <label>Email: <input type="email" name="email" required></label>
      <label>Password: <input type="password" name="password" required></label>
      <label>Batch:
        <select name="batchId">
          <option value="">-- choose a batch (optional) --</option>
          <%
            for (Batch b : batches) {
          %>
            <option value="<%= b.getId() %>"><%= b.getName() %> (<%= b.getTiming() %>)</option>
          <%
            }
          %>
        </select>
      </label>
      <button type="submit">Register</button>
    </form>

    <%
      if (reg != null) {
    %>
      <p class="small">Registration successful. You may now <a href="participant-login">login</a>.</p>
    <%
      } else if (err != null) {
        if ("exists".equals(err)) {
    %>
      <p class="small" style="color:crimson">A user with that email already exists.</p>
    <%
        } else if ("missing".equals(err)) {
    %>
      <p class="small" style="color:crimson">Please provide name, email and password.</p>
    <%
        } else {
    %>
      <p class="small" style="color:crimson">Registration failed. Please try again.</p>
    <%
        }
      }
    %>

    <p><a href="index.jsp">Back to home</a></p>
  </div>
</body>
</html>
