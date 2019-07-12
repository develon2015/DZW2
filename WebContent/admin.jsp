<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="em.*" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 系统管理</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>
	<p class="title">系统管理</p>
		
	<div style="text-align: left;">
		<p class="title3" style="text-align: left;">管理发布</p>
	</div>
	<div class="info">
		<hr>
<table>
	<% List<?> list = (List<?>) request.getAttribute("list");
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
			
			<% if (h.enable == 0) { %>
				<td><div>等待审核</div></td>
			<% } %>
			<% if (h.enable == 1) { %>
				<td><div style="color: blue;">已通过审核</div></td>
			<% } %>
			<% if (h.enable == 2) { %>
				<td><div class="warning">审核未通过</div></td>
			<% } %>
			<% if (h.enable == 3) { %>
				<td><div class="warning">商家已下架</div></td>
			<% } %>
			<% if (h.enable == 4) { %>
				<td><div class="warning">管理员下架</div></td>
			<% } %>
			<td style="text-align: center;"><div><a target="_blank" href="${ pageContext.request.contextPath }/lease_show.html?id=<%= h.id %>">查看</a></div></td>

			<% if (h.enable == 0) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mgr.html?id=<%= h.id %>&r=1">通过</a></div></td>
				<td><div><a href="${ pageContext.request.contextPath }/mgr.html?id=<%= h.id %>&r=2">拒绝</a></div></td>
			<% } %>
			<% if (h.enable == 1) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mgr.html?id=<%= h.id %>&r=4">下架</a></div></td>
			<% } %>
			<% if (h.enable == 2) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mgr.html?id=<%= h.id %>&r=0">重新审核</a></div></td>
			<% } %>
			<% if (h.enable == 4) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mgr.html?id=<%= h.id %>&r=1">重新上架</a></div></td>
			<% } %>
			
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
<script src="${ pageContext.request.contextPath }/js/alert.js"></script>
<script type="text/javascript">
var info = "${ info }";
if (info !== "")
	alert(info, function() { ${ action } });
</script>
</html>
<%@ page trimDirectiveWhitespaces="true" %>