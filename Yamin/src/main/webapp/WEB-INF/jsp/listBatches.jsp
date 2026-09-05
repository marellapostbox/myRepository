<%@ page import="java.util.List" %>
<%@ page import="com.yamin.model.Batch" %>
<html>
<head>
  <title>Batches</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2>Batches</h2>
    <%-- show Add Batch link only to admins --%>
    <%
      Object adminObj = session.getAttribute("admin");
      boolean isAdmin = adminObj != null;
    %>
    <% if (isAdmin) { %>
      <a class="btn" href="<%=request.getContextPath()%>/addBatch.jsp">Add Batch</a> |
    <% } %>
    <a href="<%=request.getContextPath()%>/index.jsp">Home</a>
    <hr>
  <%
    List<Batch> batches = (List<Batch>) request.getAttribute("batches");
    if (batches != null) {
  %>
  <table border="1">
    <tr><th>ID</th><th>Name</th><th>Timing</th><th>Actions</th></tr>
    <%
      for (Batch b : batches) {
    %>
    <tr>
      <td><%=b.getId()%></td>
      <td><%=b.getName()%></td>
      <td><%=b.getTiming()%></td>
      <td>
        <% if (isAdmin) { %>
        <form method="post" action="<%=request.getContextPath()%>/batches" style="display:inline">
          <input type="hidden" name="_method" value="DELETE">
          <input type="hidden" name="id" value="<%=b.getId()%>">
          <button type="submit">Delete</button>
        </form>
        <% } %>
        <%-- Only admins may view participants for a batch --%>
        <% if (isAdmin) { %>
          <a href="<%=request.getContextPath()%>/participants?batchId=<%=b.getId()%>">View Participants</a>
        <% } %>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
    <p>No batches found.</p>
  <% } %>
  </div>
</body>
</html>
