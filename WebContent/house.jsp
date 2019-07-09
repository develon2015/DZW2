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
	<p class="title"><%= h.name %></p>
	
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
	
	<div>
	<br>
	<input id="d1" type="date">~<input id="d2" type="date"><br>
	<button onclick="buy()">立即预定</button>
	</div>
	
	<p class="title">描述</p>
	<div class="info">
		<hr>
			<p style="text-align: left;"><%= h.info.replace("\n", "<br>") %></p>
		<hr>
	</div>
	
	<p class="title">图片展示</p>
	<div class="info">
	<hr>
		<% if (h.imgs.length == 0) { %>
		<p>暂无图片</p>
		<% } %>
		<% for (int i = 0; i < h.imgs.length; i ++ ) { %>
		<a href="${ pageContext.request.contextPath }/showimg.html?url=<%= h.imgs[i] %>"><img title="点击查看大图" width="33%" src='<%= h.imgs[i] %>'/></a>
		<% } %>
	<hr>
	</div>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	var d1 = document.getElementById('d1');
	var d2 = document.getElementById('d2');

	function buy() {
		location='${ pageContext.request.contextPath }/buy.html?id=<%= h.id %>';
	}
</script>
</html>
<%@ page trimDirectiveWhitespaces="true" %>