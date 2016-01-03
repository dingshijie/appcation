<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	<title>outh login</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row col-md-10 col-md-offset-1">
			<div class="page-header">
			  <label>applications</label>  <small>oltu</small> <a class="index col-md-offset-2" href="${webRoot }/index.html" title="点击返回首页">首页</a>
			</div>
			<div class="row col-md-4 col-md-offset-4">
	      	<form class="form-signin" action="${webRoot }/oltu/login" method="post">
		        <h2 class="form-signin-heading">Please sign in</h2>
		        <label for="inputUsername" class="sr-only">username</label>
		        <input type="text" id="username" name="username" class="form-control" placeholder="username" required autofocus>
		        <label for="inputPassword" class="sr-only">password</label>
		        <input type="password" id="password" name="password" class="form-control" placeholder="password" required>
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	     	 </form>
	     	 </div>
	     	 <div class="row col-md-4 col-md-offset-4 text-center" >
	     	 	<hr class="divide">
				使用其他方式登录
				<a href="/oltu/oauth2login.html">监测系统</a>
	     	 </div>
		 </div>
    </div> <!-- /container -->
	
</body>
</html>
