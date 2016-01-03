<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/application.css">
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/bootstrap/bootstrap.min.js"></script>
	<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		
	 	 <div class="row col-md-10 col-md-offset-1">
			<h4>${result }</h4>
		</div>
	</div>
</body>
</html>