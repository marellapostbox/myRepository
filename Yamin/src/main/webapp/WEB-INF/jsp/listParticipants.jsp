<%@ page import="java.util.List" %>
<%@ page import="com.yamin.model.Participant" %>
<html>
<head>
  <title>Participants</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2> Participants </h2>
    <%-- Show Add Participant only to logged-in admin users --%>
    <% 
      // Do not use the implicit 'session' (it may create a session). Instead
      // obtain the session without creating one and use a distinct variable
      // name to avoid duplicate-local-variable issues in JSP.
      jakarta.servlet.http.HttpSession currentSession = request.getSession(false);
      if (currentSession != null && currentSession.getAttribute("admin") != null) {
    %>
      <a class="btn" href="${pageContext.request.contextPath}/addParticipant.jsp">Add Participant</a> |
    <% } %>
    <a href="index.jsp">Home</a>
    <hr>
  <%
    // support either a single participant (attribute "participant") or a list (attribute "participants")
    Participant participant = (Participant) request.getAttribute("participant");
    List<Participant> participants = (List<Participant>) request.getAttribute("participants");

    if (participant != null) {
  %>
    <table border="1">
      <tr><th>ID</th><th>Name</th><th>Email</th><th>Batch ID</th><th>Actions</th></tr>
      <tr>
        <td><%=participant.getId()%></td>
        <td><%=participant.getName()%></td>
        <td><%=participant.getEmail()%></td>
        <td><%=participant.getBatchId()%></td>
        <td>
          <a href="${pageContext.request.contextPath}/addParticipant.jsp?id=<%=participant.getId()%>">Edit</a>
          <form method="post" action="participants" style="display:inline; margin-left:0.5em;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="id" value="<%=participant.getId()%>">
            <button type="submit">Delete</button>
          </form>
        </td>
      </tr>
    </table>
  <% } else if (participants != null && !participants.isEmpty()) { %>
    <table border="1" style="width:100%; border-collapse:collapse;">
      <thead>
        <tr>
          <th style="padding:6px; text-align:left;">ID</th>
          <th style="padding:6px; text-align:left;">Name</th>
          <th style="padding:6px; text-align:left;">Email</th>
          <th style="padding:6px; text-align:left;">Batch ID</th>
          <th style="padding:6px; text-align:left;">Actions</th>
        </tr>
      </thead>
      <tbody>
        <% for (Participant p : participants) { %>
          <tr>
            <td style="padding:6px;"><%= p.getId() %></td>
            <td style="padding:6px;"><%= p.getName() %></td>
            <td style="padding:6px;"><%= p.getEmail() %></td>
            <td style="padding:6px;"><%= p.getBatchId() != null ? p.getBatchId() : "-" %></td>
            <td style="padding:6px;">
              <a href="${pageContext.request.contextPath}/addParticipant.jsp?id=<%=p.getId()%>">Edit</a>
              <form method="post" action="participants" style="display:inline; margin-left:0.5em;">
                <input type="hidden" name="_method" value="DELETE">
                <input type="hidden" name="id" value="<%=p.getId()%>">
                <button type="submit">Delete</button>
              </form>
            </td>
          </tr>
        <% } %>
      </tbody>
    </table>
  <% } else { %>
    <p>No participants found.</p>
  <% } %>
  </div>
</body>
</html>
