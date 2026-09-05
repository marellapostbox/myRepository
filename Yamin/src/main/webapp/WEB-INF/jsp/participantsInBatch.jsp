<%@ page import="java.util.List" %>
<%@ page import="com.yamin.model.Participant" %>
<%@ page import="com.yamin.model.Batch" %>
<html>
<head>
  <title>Participants in Batch</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <%
      Batch batch = (Batch) request.getAttribute("batch");
      // servlet should provide a List<Batch> as request attribute "batches" for selection
      List<Batch> batches = (List<Batch>) request.getAttribute("batches");
      // participants list for the selected batch
      List<Participant> participants = (List<Participant>) request.getAttribute("participants");
    %>

    <h2>Participants in Batch</h2>   <a href="<%=request.getContextPath()%>/index.jsp">Home</a>

    <!-- Display BatchId and BatchName as static read-only information -->
    <div style="margin-bottom:1em;">
      <p><strong>Batch ID:</strong> <%= (batch!=null? batch.getId() : "-") %></p>
      <p><strong>Batch Name:</strong> <%= (batch!=null && batch.getName()!=null ? batch.getName() : "-") %></p>
      <p class="small">Timing: <%= (batch!=null? batch.getTiming() : "-") %></p>
    </div>

    <!-- Dropdown to switch to another batch quickly -->
    <form action="participants" method="get" style="margin-bottom:1em;">
      <label>Choose Batch:
        <select id="batchSelect" name="batchId" onchange="this.form.submit();">
          <option value="">-- Select Batch --</option>
          <% if (batches != null) {
               for (Batch b : batches) {
          %>
            <option value="<%= b.getId() %>" <%= (batch!=null && batch.getId()==b.getId())? "selected" : "" %>><%= b.getName() %></option>
          <%   }
             } %>
        </select>
      </label>
      <noscript>
        <button type="submit" class="btn" style="margin-left:1em;">Show Participants</button>
      </noscript>
    </form>

    <a class="btn" href="batches">Back to batches</a>
    <hr>

    <% if (participants != null && !participants.isEmpty()) { %>
      <table border="1" style="width:100%; border-collapse:collapse;">
        <thead>
          <tr>
            <th style="padding:6px; text-align:left;">ID</th>
            <th style="padding:6px; text-align:left;">Name</th>
            <th style="padding:6px; text-align:left;">Email</th>
            <th style="padding:6px; text-align:left;">Batch ID</th>
          </tr>
        </thead>
        <tbody>
          <% for (Participant p : participants) { %>
            <tr>
              <td style="padding:6px;"><%= p.getId() %></td>
              <td style="padding:6px;"><%= p.getName() %></td>
              <td style="padding:6px;"><%= p.getEmail() %></td>
              <td style="padding:6px;"><%= p.getBatchId() != null ? p.getBatchId() : "-" %></td>
            </tr>
          <% } %>
        </tbody>
      </table>
    <% } else { %>
      <p>No participants found for this batch.</p>
    <% } %>

  </div>
</body>
</html>