<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>pdf test</title>
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/application.css">
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>


	<script type="text/javascript" src="${initParam.resourceRoot}/js/bootstrap/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		
	  <div class="row col-md-10 col-md-offset-1">
		<div class="page-header">
		  <label>applications</label>  <small>itext</small> <a class="index col-md-offset-2" href="${webRoot }/index.html" title="点击返回首页">首页</a>
		</div>
		<a href="${webRoot }/itext/pdf">创建pdf</a>
	  </div>
	</div>
	
</body>
</html>
