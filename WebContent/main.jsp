<%@page import="common.*"%>
<%@page import="user.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	SysUtil.test();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>轻松短租网</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css"/>
<style type="text/css">
	#title {
		color: pink;
		text-shadow: -1px 0 black, 0 2px black, 1px 0 black, 0 -1px black;
	}
	
	font {
		color: white;
		text-shadow: black 0.1em 0.1em 0.2em;
	}
	
	#search {
		height: 24px;
		width: 600px;
		margin-top: 20px;
	}
	
	#search-icon{
		text-align: center;
		background: none;
		color: #ffffff;
		font-size: 16px;
		white-space: normal;
		margin-top: 0px;
		border: none;
		border-color: #4CAF50;
		border-radius: 0px;
		outline: none;
	}
</style>
</head>
<body>
	<h1 id="title">寻找旅行中的家</h1>
	<%
		User user = (User) request.getAttribute("user");
		if (user == null) {
	%>
			<a href="${ pageContext.request.contextPath }/user/login.html">登录</a>&nbsp;/&nbsp;<a href="${ pageContext.request.contextPath }/user/register.html">注册</a>
	<% } else { %>
			<a href="${ pageContext.request.contextPath }/user/logout.html">${ user.name }</a>
			&nbsp;
			<a href="${ pageContext.request.contextPath }/user/logout.html">退出</a>
			&nbsp;
			<a href="${ pageContext.request.contextPath }/user/logout.html">免费发布房间</a>
	<% } %>
	<p/>
	<div>
	<form id="search-form" action="${ pageContext.request.contextPath }/search.html">
      <input id="search" autocomplete="text" placeholder="请输入要搜索的内容" name="q">
      <button id="search-icon" type="submit">
      	<font>搜索</font>
      </button>
    </form>
    </div>
	
	<div class="footer">
		<a href="${ pageContext.request.contextPath }/index.html">首页</a><br>
	</div>
</body>
</html>
<%@page trimDirectiveWhitespaces="true"%>