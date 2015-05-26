<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${initParam.resourceRoot}/js/hchart/highcharts.js"></script> 
<script type="text/javascript" src="${initParam.resourceRoot}/js/index.js"></script>
<title>Insert title here</title>
</head>
<body>
  <h4>index</h4>
  
  <br>
  <h5><a href="${webRoot }/redis/redis.html">redis</a></h5>
  
  <br>
  <h5><a href="${webRoot }/itext/itext.html">itext</a></h5>
  
  
  <div id="container" style="min-width:700px;height:400px"></div>
</body>
</html>