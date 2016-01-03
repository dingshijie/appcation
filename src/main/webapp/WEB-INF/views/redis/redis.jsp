<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="webRoot" value="${pageContext.request.contextPath }" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${initParam.resourceRoot}/css/application.css">
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="${initParam.resourceRoot}/js/bootstrap/bootstrap.min.js"></script>
	
	<title>redis test</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row col-md-10 col-md-offset-1">
			<div class="page-header">
			  <label>applications</label>  <small>redis</small> <a class="index col-md-offset-2" href="${webRoot }/index.html" title="点击返回首页">首页</a>
			</div>
			<dl class="dl-horizontal ">
				<dt>1、basic operation</dt>
				<dd></dd>
				<hr class="devide">
				<dt>
					1)、添加一个记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/add" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td rowspan="2"><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
								<tr>
									<td><label>value:</label></td>
									<td><input type="text" class="form-control" name="value"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					2)、查找单个记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/find" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					3)、删除一个记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/delete" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<hr class="devide">
				<dt>2、redis list operation</dt>
				<dd></dd>
				<hr class="devide">
				<dt>
					1)、添加记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/addtolist" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td rowspan="2"><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
								<tr>
									<td><label>value:</label></td>
									<td><input type="text" class="form-control" name="value"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					2)、查找记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/findlist" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					3)、左侧/队首移出记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/lpop" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					4)、右侧/队尾移出记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/rpop" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<hr class="devide">
				<dt>3、redis set operation</dt>
				<dd></dd>
				<hr class="devide">
				<dt>
					1)、添加记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/addtoset" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td rowspan="2"><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
								<tr>
									<td><label>value:</label></td>
									<td><input type="text" class="form-control" name="value"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
				<dt>
					2)、查找记录
				</dt>
				<dd>
					<form action="${webRoot}/redis/findset" method="post">
						<table class="table">
							<colgroup><col width="5%"><col width="20%"> </colgroup>
							<tbody>
								<tr>
									<td><label>key:</label> </td>
									<td><input type="text" class="form-control" name="key"></td>
									<td><input type="submit" class="btn btn-sm btn-primary" value="Submit"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</dd>
			</dl>
   		<hr class="devide">
		 </div>
    </div>
</body>
</html>