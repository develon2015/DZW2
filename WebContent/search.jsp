<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="em.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<title>轻松短租网</title>
<style type="text/css">
body {
	text-align: center;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h1>搜索"${ q }"结果</h1>
	
	<%
		for (HouseItem h : (List<HouseItem>) request.getAttribute("list")) {
			out.println("<h2>" + h.name + "</h2><br>");
		}
	%>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>