<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="em.*" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>
	<p class="title">标题</p>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
<script src="${ pageContext.request.contextPath }/js/alert.js"></script>
<script type="text/javascript">
var info = "${ info }";
if (info !== "")
	alert(info, function() { ${ action } });
</script>
</html>
<%@ page trimDirectiveWhitespaces="true" %>