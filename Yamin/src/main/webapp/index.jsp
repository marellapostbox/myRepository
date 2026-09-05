<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yamin.repo.Repository, com.yamin.model.Batch, java.util.List" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Yamin Zumba - Welcome</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h1>Welcome to Yamin's Zumba Management</h1>
    <%
      Object adminObj = session.getAttribute("admin");
      Object participantObj = session.getAttribute("participant");
      boolean isAdmin = adminObj != null;
      boolean isParticipant = participantObj != null;
    %>
    <nav>
      <ul>
        <% if (!isAdmin && !isParticipant) { %>
          <li><a href="<%=request.getContextPath()%>/admin-login">Admin Login</a></li>
          <li><a href="<%=request.getContextPath()%>/register.jsp">Participant Registration</a></li>
          <li><a href="<%=request.getContextPath()%>/participant-login">Participant Login</a></li>
        <% } else { %>
          <% if (isAdmin) { %>
            <li><a href="<%=request.getContextPath()%>/addBatch.jsp">Add Batch</a></li>
            <li><a href="<%=request.getContextPath()%>/batches">Manage/View Batches</a></li>
            <li><a href="<%=request.getContextPath()%>/participants">Manage/View Participants</a></li>
          <% } else if (isParticipant) { %>
            <li><a href="<%=request.getContextPath()%>/batches">View Batches</a></li>
            <li><a href="<%=request.getContextPath()%>/participants">My Batch / Participants</a></li>
          <% } %>
          <li><a href="<%=request.getContextPath()%>/logout">Logout</a></li>
        <% } %>
      </ul>
    </nav>
  </div>
</body>
</html>
