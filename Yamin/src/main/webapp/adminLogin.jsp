<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Admin Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <div class="container">
    <h2>Admin Login</h2>
    <form method="post" action="admin-login">
      <label>Username: <input type="text" name="username" required></label>
      <label>Password: <input type="password" name="password" required></label>
      <button type="submit">Login</button>
    </form>
  </div>
</body>
</html>
