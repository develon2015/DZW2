<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="common.*"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 修改信息</title>
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
	<p class="title">修改您的账号信息</p>
	<h3 id="warning">${ info }</h3>
	
	<form action="?request=update" method="post">
		<table style="margin: auto">
			<tr>
				<td>*用户名:</td>
				<td rowspan="2"><input width="auto" type="text" required="required"
					value="${ user.name }"
					autocomplete="off" id="name" name="name"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">4-20个字符</font></td>
			</tr>
			<tr>
				<td>*手机号:</td>
				<td rowspan="2"><input width="auto" type="number" required="required"
					value="${ user.phone }"
					autocomplete="tel" id="phone" name="phone"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">11位数的手机号码</font></td>
			</tr>
			<tr>
				<td>&nbsp;邮箱:</td>
				<td rowspan="2"><input width="auto" type="email"
					value="${ user.email }"
					autocomplete="email" id="email" name="email"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">输入您的邮箱(可选)</font></td>
			</tr>
		</table>
		<br>
		<button type="submit">修改</button>
	</form>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true"%>
