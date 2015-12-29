<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/application.css">
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${initParam.resourceRoot}/js/hchart/highcharts.js"></script> 
	
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="${initParam.resourceRoot}/js/index.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		
	  <div class="row col-md-10 col-md-offset-1">
		<div class="page-header">
		  <h3>applications</h3>
		</div>
		<div class="col-md-3">
			<ol>
				<li><a href="${webRoot }/redis/redis.html" title="go to redis">redis</a></li>
				<li><a href="${webRoot }/itext/itext.html" title="go to itext">itext</a></li>
				<li><a href="${webRoot }/oltu/login.html" title="go to oltu oauth2">apache oltu / oauth2</a></li>
			</ol>
		</div>
		
	  </div>
	  <div class="row col-md-10 col-md-offset-1">
	  	<div id="container" style="min-width:700px;height:400px"></div>
	  </div>
	</div>
</body>
</html>
