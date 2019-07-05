<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<title>轻松短租网 - 发布出租屋</title>
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
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h2>发布出租屋</h2>
	<h2 class="warning">${ result }</h2>
	
	<form method="post" action="?request=upload" enctype="multipart/form-data">
		<br>
		<table style="margin: auto">
			<tr>
				<th>*房屋名称:</th>
				<td><input style="width: 500px;" type="text" autocomplete="off" name="name"></td>
				<th>*价格:</th>
				<td><input autocomplete="off" name="name">元/天</td>
			</tr>
			<tr>
				<th>*地址:</th>
				<td><input style="width: 500px;" autocomplete="address" name="name"></td>
			</tr>
			<tr>
				<th>&nbsp;&nbsp;图片:</th>
				<td>
				    <input type="file" accept="image/png, image/jpeg, image/*" name="img1" /><br>
				    <input type="file" accept="image/png, image/jpeg, image/*" name="img2" /><br>
				    <input type="file" accept="image/png, image/jpeg, image/*" name="img3" />
				</td>
			</tr>
		</table>
		<br>
	    <input type="submit" value="发布" />
	</form>
	

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>