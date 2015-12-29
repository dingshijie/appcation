<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>authorize</title>
<link rel="stylesheet" href="${initParam.resourceRoot}/css/bootstrap/bootstrap.min.css">

<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${initParam.resourceRoot}/js/bootstrap/bootstrap.min.js"></script>
</head>
<body>
	warn:
	登陆成功，点击授权则表示您同意该网站获取您的身份信息。
	<form action="${webRoot }/oltu/accessToken" method="post">
		<input type="hidden" name="client_id" value="test">
		<input type="hidden" name="client_secret" value="HJ$Ggd54">
		<input type="hidden" name="grant_type" value="authorization_code">
		<input type="hidden" name="code" value="43e066e1d8e488063028489abc1bf0c5">
		<input type="hidden" name="redirect_uri" value="http://localhost:8080/oltu/result.html">
		<input type="submit" value="authorize">
	</form>
	<script type="text/javascript">
		$(function(){
			//此一段代码作为测试使用，获取url上的code
			var reg = new RegExp("(^|&)code=([^&]*)(&|$)");
			console.log(reg);
			var r = window.location.search.substr(1).match(reg);
			if(r!=null){
				$('input[name="code"]').val(r[2]);
			}
		});
	</script>
</body>
</html>