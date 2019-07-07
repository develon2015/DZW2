<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<title>轻松短租网 - 发布成功</title>
<style type="text/css">
	body {
		text-align: center;
	}
	
	body {
		text-align: center;
	}
	
	th, td {
		text-align: left;
		padding: 8px 4px;
	}
	
	#textarea {
		background-color: pink;
		font-size: 0.9em;
		font-family: 宋体;
		color: black;
	}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h2>恭喜您!</h2>
	<h2>发布成功, 继续<a href="${ pageContext.request.contextPath }/lease.html">发布</a></h2>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>