<%@page import="common.*"%>
<%@page import="user.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	SysUtil.test();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>轻松短租网</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css"/>
<style type="text/css">
	#title {
		color: pink;
		text-shadow: -1px 0 black, 0 2px black, 1px 0 black, 0 -1px black}
		
	}
</style>
</head>
<body>
	<h1 id="title">寻找旅行中的家</h1>
	<%
		User user = (User) request.getAttribute("user");
		if (user == null) {
	%>
			<a href="${ pageContext.request.contextPath }/user/register.html">注册</a>&nbsp;/&nbsp;<a href="${ pageContext.request.contextPath }/user/login.html">登录</a>
	<% } else { %>
			<a href="${ pageContext.request.contextPath }/user/logout.html">退出</a>
	<% } %>
	
	<div class="footer">
		<a href="${ pageContext.request.contextPath }/index.html">首页</a><br>
	</div>
</body>
</html>
<%@page trimDirectiveWhitespaces="true"%>