<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<style type="text/css">
	#warning {
		color: red;
	}
</style>
</head>
<body>
<div id="div_footer">
	<h2>登录到您的短租账户</h2>
	<h3 id="warning">${ login_result }</h3>
	<form action="?request=login" method="post">
		<table style="margin: auto">
			<tr>
				<td>*用户名:</td>
				<td rowspan="2"><input width="auto" type="text" required="required"
					autocomplete="off" id="name" name="name"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">4-20个字符</font></td>
			</tr>
			<tr>
				<td>*密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					autocomplete="password" id="passwd" name="passwd"></td>
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
</div>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>