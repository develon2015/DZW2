<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>轻松短租网</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
</head>
<body>
</body>
<script src="${ pageContext.request.contextPath }/js/alert.js"></script>
<script type="text/javascript">
alert("${ info }", function() {
    ${ action }
});
</script>
</html>
<%@ page trimDirectiveWhitespaces="true" %>