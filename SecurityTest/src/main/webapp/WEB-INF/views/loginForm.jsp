<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LoginForm</title>
</head>
<body>
<h1>LoginForm</h1>
<h2><c:out value="${error }"/></h2>
<h2><c:out value="${logout }"/></h2>
<form method="post" action="/login">
	username : <input type="text" name="username" value="admin"/><br>
	password : <input type="text" name="password" value="admin"/><br>
	<input type="submit" value="로그인">
	<sec:csrfInput/>
</form>
</body>
</html>