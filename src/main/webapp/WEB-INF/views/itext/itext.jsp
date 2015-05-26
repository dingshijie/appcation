<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>pdf test</title>
	
	<script type="text/javascript" src="../resources/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src='../resources/js/itext/itext.js'></script>
</head>
<body>
	
	<a href="${webRoot }/itext/pdf">创建pdf</a>

	<br>
	<input type="checkbox" name="items" value="985"/>985
	<input type="checkbox" name="items" value="211"/>211
	<input type="checkbox" name="items" value="独立院校"/>独立院校
	<input type="checkbox" name="items" value="科研机构"/>科研机构
	<br>
	<input type="button" name="selectAll" value="全选">
	<input type="button" name="selectNor" value="全不选">
	<input type="button" name="selectObs" value="反选">
	<input type="button" name="selectSub" value="提交">
	 
</body>
</html>