<%@page import="em.HouseItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网</title>
<style type="text/css">
body {
	text-align: center;
}

th, td {
	text-align: left;
	padding: 8px 4px;
	background-color: #e4f8e1;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
<style type="text/css">
button {
	border-radius: 20px;
	width: 200px;
	margin: 20px;
}

font.title {
	color: #444;
	text-shadow: none;
	font-family: 楷体;
}

div.info {
	background-color: #e4f8e1;
}
</style>
</head>
<body>
	<% HouseItem h = (HouseItem) request.getAttribute("house"); %>
	<h2><font class="title"><%= h.name %></font></h2><hr>
	
	<table style="margin: auto">
		<tr>
			<td><div>地址</div></td>
			<td><div><%= h.address %></div></td>
		<tr>
			<td><div>面积</div></td>
			<td><div><%= h.area %>&nbsp;m<sup>2</sup></div></td>
		<tr>
			<td><div>人数</div></td>
			<td><div>可住<%= h.pn %>人</div></td>
		<tr>
			<td><div>天数</div></td>
			<td><div>可入住<%= h.time_short %>~<%= h.time_long %>天</div></td>
		<tr>
			<td><div>价格</div></td>
			<td><div><%= h.price %>元/天</div></td>
		</tr>
		<tr>
			<td><div>联系人</div></td>
			<td><div><%= h.tel_name %></div></td>
		</tr>
		<tr>
			<td><div>联系方式</div></td>
			<td><div><%= h.tel_num %></div></td>
		</tr>
	</table>
	
	<div><button onclick="location='${ pageContext.request.contextPath }/buy.html?id=<%= h.id %>'">预定</button></div>
	
	<div class="info">
		<hr>
			<p><%= h.info.replace("\n", "<br>") %></p>
		<hr>
	</div>
	<div><img height="150px" src='<%= h.icon %>'/><img height="150px" src='<%= h.icon %>'/><img height="150px" src='<%= h.icon %>'/></div>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>