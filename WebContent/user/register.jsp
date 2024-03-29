<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="common.*"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 注册</title>
<style type="text/css">
body {
	text-align: center;
}

th, td {
	text-align: left;
	padding: 8px 4px;
}

#warning {
	color: red;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>

<body>
	<p class="title">注册新的短租账号</p>
	<h3 id="warning">${ register_result }</h3>
	
	<form action="?request=register" method="post">
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
					autocomplete="new-password" id="passwd" name="passwd"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">6-12个字符</font></td>
			</tr>
			<tr>
				<td>*确认密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					autocomplete="new-password" id="passwd2" name="passwd2"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">重复一次密码</font></td>
			</tr>
			<tr>
				<td>*手机号:</td>
				<td rowspan="2"><input width="auto" type="number" required="required"
					autocomplete="tel" id="phone" name="phone"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">11位数的手机号码</font></td>
			</tr>
			<tr>
				<td>&nbsp;邮箱:</td>
				<td rowspan="2"><input width="auto" type="email"
					autocomplete="email" id="email" name="email"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">输入您的邮箱(可选)</font></td>
			</tr>
		</table>
		<br>
		<button type="submit">注册</button>
	</form>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true"%>
