<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<title>轻松短租网 - 登录</title>
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
	<h1>登录到您的短租账户</h1>
	<%-- <h1>${pageContext.request.contextPath }</h1> --%>
	<form action="?request=login" method="post">
		<table style="margin: auto">
			<tr>
				<td>*用户名:</td>
				<td rowspan="2"><input width="auto" type="text" required="required"
					autocomplete="username" id="name" name="name"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">4-20个字符</font></td>
			</tr>
			<tr>
				<td>*密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					autocomplete="new-password" id="passwd" name="passwd"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">6-12个字符</font></td>
			</tr>
		</table>
		<br>
		<button type="submit">登录</button>
	</form>
	<br>
	<div style="padding: 20xp; margin: 20xp">
		没有账号? 立即<a href="${ pageContext.request.contextPath }/user/register.html">注册</a>
	</div>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>