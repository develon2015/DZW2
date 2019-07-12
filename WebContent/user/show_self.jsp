<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="em.*" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - ${ user }</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
<style type="text/css">
	body {
		text-align: left;
	}
	
	p {
		padding: 20px;
		margin: 6px 2px;
	}
	
	table {
		padding: 20px;
	}

	td, th {
		padding: 0px 20px;
	}
</style>
</head>
<body>
	<p class="title2">${ user }的短租账户</p>

	
	<div style="text-align: left;">
		<p class="title3" style="text-align: left;">账户信息</p>
	</div>
	<div class="info">
		<hr>
			<p style="text-align: left;">姓名: ${ user.name }&nbsp;&nbsp;联系方式: ${ user.phone }&nbsp;&nbsp;email: ${ user.email }&nbsp;&nbsp;
			<a href="${ pageContext.request.contextPath }/user/update.html">修改信息</a>&nbsp;<a href="${ pageContext.request.contextPath }/user/uppswd.html">修改密码</a>
			</p>
		<hr>
	</div>
	
	<div style="text-align: left;">
		<p class="title3" style="text-align: left;">我的订单</p>
	</div>
	<div class="info">
		<hr>
<table>
	
</table>
		<hr>
	</div>

	<div style="text-align: left;">
		<p class="title3" style="text-align: left;">我的发布</p>
	</div>
	<div class="info">
		<hr>
<table>
	<% List<?> list = (List<?>) request.getAttribute("listlease");
		int i = 1;
		if (list != null)
		for (Object e : list) {
			HouseItem h = (HouseItem) e;%>
		<tr>
			<td style="text-align: left;"><%= i++ %></td>
			<td><div><%= h.name %></div></td>
			<td><div><%= h.address %></div></td>
			<td><div><%= h.area %>&nbsp;m<sup>2</sup></div></td>
			<td><div>可住<%= h.pn %>人</div></td>
			<td><div>可入住<%= h.time_short %>~<%= h.time_long %>天</div></td>
			<td><div><%= h.price %>元/天</div></td>
			<td><div>发布于<%= h.date %></div></td>
			<% if (h.enable == 0) %>
			<td><div>正在审核中</div></td>
			<% if (h.enable == 1) %>
			<td><div style="color: blue;">已通过审核</div></td>
			<% if (h.enable == 2) %>
			<td><div class="warning">审核未通过</div></td>
			<td style="text-align: right;"><div><a href="${ pageContext.request.contextPath }/lease_show.html?id=<%= h.id %>">查看</a></div></td>
		</tr>
		<tr>
		<td colspan="15"><hr></td>
		</tr>
	<% } %>
</table>
		<hr>
	</div>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>