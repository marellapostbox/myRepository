<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Update Participant</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2>Update Participant</h2>
    <%
      com.yamin.model.Participant participant = (com.yamin.model.Participant) request.getAttribute("participant");
      String pid = (participant != null) ? String.valueOf(participant.getId()) : request.getParameter("id");
      String pname = (participant != null) ? (participant.getName()==null?"":participant.getName()) : (request.getParameter("name")!=null?request.getParameter("name") : "");
      String pemail = (participant != null) ? (participant.getEmail()==null?"":participant.getEmail()) : (request.getParameter("email")!=null?request.getParameter("email") : "");
      String pbid = (participant != null && participant.getBatchId()!=null) ? String.valueOf(participant.getBatchId()) : (request.getParameter("batchId")!=null?request.getParameter("batchId") : "");
    %>
    <form method="post" action="participants">
      <input type="hidden" name="_method" value="PUT">
      <label>ID: <input type="number" name="id" required value="<%= pid %>"></label>
      <label>Name: <input type="text" name="name" value="<%= pname.replace("\"","&quot;") %>"></label>
      <label>Email: <input type="email" name="email" value="<%= pemail.replace("\"","&quot;") %>"></label>
      <label>Password: <input type="password" name="password"></label>
      <label>Batch ID: <input type="number" name="batchId" value="<%= pbid %>"></label>
      <button type="submit">Update Participant</button>
    </form>
  </div>
</body>
</html>
