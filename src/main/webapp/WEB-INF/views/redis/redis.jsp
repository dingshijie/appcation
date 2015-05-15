<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>redis test</title>
</head>
<body>
	添加一个记录
	<form action="${webRoot}/redis/add" method="post">
		<input type="text" name="key">
		<br>
		<input type="text" name="value">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>查找记录
	<form action="${webRoot}/redis/find" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>删除一个记录
	<form action="${webRoot}/redis/delete" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>
	redis list operation
	<br>
	添加记录
	<form action="${webRoot}/redis/addtolist" method="post">
		<input type="text" name="key">
		<br>
		<input type="text" name="value">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>查找记录
	<form action="${webRoot}/redis/findlist" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>左侧/队首移出记录
	<form action="${webRoot}/redis/lpop" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>右侧/队尾移出记录
	<form action="${webRoot}/redis/rpop" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>
	redis set operation
	<br>
	添加记录
	<form action="${webRoot}/redis/addtoset" method="post">
		<input type="text" name="key">
		<br>
		<input type="text" name="value">
		<br>
		<input type="submit" value="Submit">
	</form>
	<br>查找记录
	<form action="${webRoot}/redis/findset" method="post">
		<input type="text" name="key">
		<br>
		<input type="submit" value="Submit">
	</form>
</body>
</html>