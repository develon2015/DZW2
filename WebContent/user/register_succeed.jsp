<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="common.*"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<title>轻松短租网 - 注册成功</title>
<style type="text/css">
body {
	text-align: center;
}
th, td {
	text-align: left;
	padding: 8px 4px;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>

<body>
	<h2>恭喜您!</h2>
	<h2>注册成功, 立即<a href="${ pageContext.request.contextPath }/user/login.html">登录</a></h2>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true"%>
