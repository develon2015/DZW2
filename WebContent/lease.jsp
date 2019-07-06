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
	
	#textarea {
		background-color: pink;
		font-size: 0.9em;
		font-family: 宋体;
		color: black;
	}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<h2>发布出租屋</h2>
	<h3 class="warning">${ result }</h3>
	
	<form method="post" action="?request=upload" enctype="multipart/form-data">
		<br>
		<table style="margin: auto">
			<tr>
				<th>*房屋名称:</th>
				<td><input style="width: 500px;" type="text" autocomplete="off" required="required" name="name"></td>
				<th>*可住人数:</th>
				<td><input type="number" step="1" min="1" autocomplete="off" required="required" name="pn">&nbsp;人</td>
			</tr>
			<tr>
				<th>*最短时间:</th>
				<td><input type="number" min="1" autocomplete="off" required="required" name="time_short">&nbsp;天</td>
				<th>*最长时间:</th>
				<td><input type="number" min="1" autocomplete="off" required="required" name="time_long">&nbsp;天</td>
			</tr>
			<tr>
				<th>*面积:</th>
				<td><input type="number" step="0.01" min="1" autocomplete="off" required="required" name="area">&nbsp;m<sup>2</sup></td>
				<th>*价格:</th>
				<td><input type="number" step="0.01" min="1" autocomplete="off" required="required" name="price">&nbsp;元/天</td>
			</tr>
			<tr>
				<th>*地址:</th>
				<td><input style="width: 500px;" autocomplete="address" required="required" name="address"></td>
			</tr>
			<tr>
				<th colspan="1">*详细描述:</th>
				<td>
					<textarea id="textarea" rows="10" cols="69" required="required" name="info"></textarea>
				</td>
			</tr>
			<tr>
				<th>*联系人:</th>
				<td>
					<input style="width: 200px;" type="text" autocomplete="off" required="required" maxlength="5" name="tel_name">
				</td>
			</tr>
			<tr>
				<th>*联系方式:</th>
				<td>
					<input style="width: 200px;" type="number" min="1" autocomplete="off" required="required" name="tel_num">
				</td>
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
	    <button type="submit">确认</button>
	</form>
	

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>