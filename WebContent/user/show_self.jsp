<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - ${ user }</title>
<style type="text/css">
body {
	text-align: center;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h2>${ user }的短租账户</h2>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>