<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Update Batch</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2>Update Batch</h2>
    <!-- This form expects the batch to be loaded and _method=PUT to be used. Use JavaScript to populate or call /batches?id= -->
    <%
      com.yamin.model.Batch batch = (com.yamin.model.Batch) request.getAttribute("batch");
      String idVal = (batch != null) ? String.valueOf(batch.getId()) : request.getParameter("id");
        String nameVal = (batch != null) ? (batch.getName()==null?"":batch.getName()) : (request.getParameter("name")!=null?request.getParameter("name") : "");
        String timingVal = (batch != null) ? (batch.getTiming()==null?"":batch.getTiming()) : (request.getParameter("timing")!=null?request.getParameter("timing") : "");
        String trainerVal = (batch != null) ? (batch.getTrainer()==null?"":batch.getTrainer()) : (request.getParameter("trainer")!=null?request.getParameter("trainer") : "");
    %>
    <form method="post" action="batches">
      <input type="hidden" name="_method" value="PUT">
      <label>ID: <input type="number" name="id" required value="<%= idVal %>"></label>
      <label>Name: <input type="text" name="name" value="<%= nameVal.replace("\"","&quot;") %>"></label>
      <label>Timing: <input type="text" name="timing" value="<%= timingVal.replace("\"","&quot;") %>"></label>
      <label>Trainer: <input type="text" name="trainer" value="<%= trainerVal.replace("\"","&quot;") %>"></label>
      <button type="submit">Update Batch</button>
    </form>
  </div>
</body>
</html>
