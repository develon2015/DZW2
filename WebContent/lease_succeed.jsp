<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 发布成功</title>
<style type="text/css">
	body {
		text-align: center;
	}

	h1, h2, h3 {
		font-family: 楷体;
	}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h2>恭喜您!</h2>
	<h3>发布成功, 正在等待审核, 您可以</h3>
	<h2><a href="${ pageContext.request.contextPath }/lease.html">继续发布</a></h2>
	<h2><a href="${ pageContext.request.contextPath }/lease_show.html?id=${ id }">立即预览</a></h2>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>