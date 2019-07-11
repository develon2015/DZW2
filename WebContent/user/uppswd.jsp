<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="common.*"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 修改密码</title>
<style type="text/css">
body {
	text-align: center;
}

th, td {
	text-align: left;
	padding: 8px 10px;
}

#warning {
	color: red;
}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>

<body>
	<p class="title">修改您的账号密码</p>
	
	<form action="?request=update" method="post">
		<table style="margin: auto">
			<tr>
				<td>*当前密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					autocomplete="off" id="name" name="po"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">身份验证密码</font></td>
			</tr>
			<tr>
				<td>*新的密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					value="${ user.phone }"
					autocomplete="tel" id="phone" name="p1"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">6-12个字符</font></td>
			</tr>
			<tr>
				<td>*新的密码:</td>
				<td rowspan="2"><input width="auto" type="password" required="required"
					value="${ user.phone }"
					autocomplete="tel" id="phone" name="p2"></td>
			</tr>
			<tr>
				<td rowspan="1"><font size="1sp">重复一次密码</font></td>
			</tr>
			<tr>
		</table>
		<br>
		<button type="submit">修改</button>
	</form>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
<script src="${ pageContext.request.contextPath }/js/alert.js"></script>
<script type="text/javascript">
var info = "${ info }";
if (info !== "")
	alert(info, function(){ ${ action } });
</script>
</html>
<%@ page trimDirectiveWhitespaces="true"%>
