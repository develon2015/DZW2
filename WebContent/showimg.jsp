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
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<p class="title">查看大图</p>
	<br>
	<img width="100%" src="${ url }"/>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>