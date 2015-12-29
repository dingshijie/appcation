<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>outh2login</title>
</head>
<body>
	outh2login
	<form method="post" action="${webRoot }/oltu/authorize?client_id=test&response_type=code&redirect_uri=http://localhost:8080/oltu/authorize.html">
		username:<input type="text" name="username"><br>
		password:<input type="password" name="password"><br>
		<input type="submit" value="Sign In">
	</form>
</body>
</html>