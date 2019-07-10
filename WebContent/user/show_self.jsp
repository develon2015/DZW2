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
	<p class="title">${ user }的短租账户</p>

	<p style="text-align: left;">&lt;账户信息&gt;&nbsp;<a href="${ pageContext.request.contextPath }/user/update.html">修改信息</a>&nbsp;<a href="">修改密码</a></p>
	<div class="info">
		<hr>
			<p style="text-align: left;">姓名: ${ user.name }&nbsp;&nbsp;联系方式: ${ user.phone }&nbsp;&nbsp;email: ${ user.email }</p>
		<hr>
	</div>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>