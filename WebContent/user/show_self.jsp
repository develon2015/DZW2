<%@page import="controller.LoginController"%>
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
		padding: 0px 10px;
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
	<% User u = LoginController.getUser(request, response); %>
	<% List<?> listorder = (List<?>) request.getAttribute("listorder");
		int i2 = 1;
		if (listorder != null)
		for (Object e : listorder) {
			Order o = (Order) e;
			HouseItem h = new HouseItem(o.hid);
			User guest = new User(o.uid);
			%>
		<tr>
			<td style="text-align: left;"><%= i2++ %></td>
			
			<% if (h.uid == u.getUid()) { %>
				<td><div><%= "租客订单" %></div></td>
				<td><div>订单号:&nbsp;<%= o.id %></div></td>
				<td><a href="${ pageContext.request.contextPath }/lease_show.html?id=<%= h.id %>"><%= h.name %></a></td>
				<td><%= o.times %> ~ <%= o.timee %></td>
				<td style="text-align: right;"><%= o.price %>元</td>
				<td>联系人: <%= guest.getName() %>(<%= guest.getPhone() %>)</td>
				<td><div>下单时间:&nbsp;<%= o.date %></div></td>

				<% if (o.status == 0) { %>
					<td>待处理</td>
					<td><a href="${ pageContext.request.contextPath }/omgr.html?id=<%= o.id %>&r=1">同意</a></td>
					<td><a href="${ pageContext.request.contextPath }/omgr.html?id=<%= o.id %>&r=2">拒绝</a></td>
				<% } %>
				<% if (o.status == 1) { %>
					<td colspan="2" style="color: blue;">已同意订单</td>
					<td><a href="${ pageContext.request.contextPath }/omgr.html?id=<%= o.id %>&r=2">取消</a></td>
				<% } %>
				<% if (o.status == 2) { %>
					<td colspan="2" style="color: red;">已取消订单</td>
				<% } %>
			
			
			<% } else { %>
				<td><div><%= "我的订单" %></div></td>
				<td><div>订单号:&nbsp;<%= o.id %></div></td>
				<td><a href="${ pageContext.request.contextPath }/house.html?id=<%= h.id %>"><%= h.name %></a></td>
				<td><%= o.times %> ~ <%= o.timee %></td>
				<td style="text-align: right;"><%= o.price %>元</td>
				<td>联系人: <%= h.tel_name %>(<%= h.tel_num %>)</td>
				<td><div>下单时间:&nbsp;<%= o.date %></div></td>

				<% if (o.status == 0) { %>
					<td colspan="1">等待处理</td>
					<td><a href="${ pageContext.request.contextPath }/omgr.html?id=<%= o.id %>&r=2">取消预定</a></td>
				<% } %>
				<% if (o.status == 1) { %>
					<td colspan="1" style="color: blue;">预定成功</td>
					<td><a href="${ pageContext.request.contextPath }/omgr.html?id=<%= o.id %>&r=2">退订</a></td>
				<% } %>
				<% if (o.status == 2) { %>
					<td colspan="2" style="color: red;">交易已取消</td>
				<% } %>
			<% } %>
		</tr>
		
		<tr>
			<td colspan="15"><hr></td>
		</tr>
	<% } %>
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
			<td><div>No.<%= h.id %></div></td>
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
			
			<td style="text-align: right;"><div><a href="${ pageContext.request.contextPath }/lease_show.html?id=<%= h.id %>">查看</a></div></td>
			
			<% if (h.enable == 3) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mmgr.html?id=<%= h.id %>&r=0">重新审核</a></div></td>
			<% } %>
			<% if (h.enable == 1) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mmgr.html?id=<%= h.id %>&r=3">下架</a></div></td>
			<% } %>
			<% if (h.enable == 0) { %>
				<td><div><a href="${ pageContext.request.contextPath }/mmgr.html?id=<%= h.id %>&r=3">取消</a></div></td>
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
</html>
<%@ page trimDirectiveWhitespaces="true" %>