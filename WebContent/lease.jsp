<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 发布出租屋</title>
<style type="text/css">
	body {
		text-align: center;
	}
	
	th, td {
		text-align: left;
		padding: 8px 4px;
		font-family: 宋楷;
		font-size: 0.9em;
	}
	
	#textarea {
		background-color: white;
		font-size: 0.9em;
		font-family: 宋楷;
		color: black;
	}
	
	b {
		color: black;
		font-size: 0.9em;
		font-family: 宋楷;
	}
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<p class="title">发布出租屋</p>
	<h3 class="warning">${ result }</h3>
	
	<form method="post" action="?request=upload" enctype="multipart/form-data">
		<br>
		<table style="margin: auto">
			<tr>
				<th>*房屋名称:</th>
				<td>
					<input value="aa" style="width: 500px;" type="text" autocomplete="off" required="required" name="name"
					placeholder="用一句话介绍你的房间吧">
				</td>
				<th>*可住人数:</th>
				<td>
					<input type="number" step="1" min="1" autocomplete="off" required="required" name="pn"
						placeholder="例如&quot;2&quot;">&nbsp;<b>人</b></td>
			</tr>
			<tr>
				<th>*最短可租:</th>
				<td>
					<input type="number" min="1" autocomplete="off" required="required" name="time_short"
						placeholder="例如&quot;1&quot;">&nbsp;<b>天</b>
				</td>
				<th>*最长可租:</th>
				<td>
					<input type="number" min="1" autocomplete="off" required="required" name="time_long"
						placeholder="例如&quot;365&quot;">&nbsp;<b>天</b>
				</td>
			</tr>
			<tr>
				<th>*面积:</th>
				<td>
					<input type="number" step="0.01" min="1" autocomplete="off" required="required" name="area"
						placeholder="例如&quot;100.86&quot;">&nbsp;<b>m<sup>2</sup></b></td>
				<th>*价格:</th>
				<td>
					<input type="number" step="0.01" min="1" autocomplete="off" required="required" name="price"
						placeholder="例如&quot;60.20&quot;">&nbsp;<b>元/天</b></td>
			</tr>
			<tr>
				<th>*地址:</th>
				<td>
					<input style="width: 500px;" autocomplete="address" required="required" name="address"
						placeholder="详细地址">
				</td>
			</tr>
			<tr>
				<th colspan="1">*详细描述:</th>
				<td>
					<textarea id="textarea" rows="10" cols="69" required="required" name="info"
					placeholder="请在此处详细介绍您要出租的房屋, 如使用条款、注意事项"></textarea>
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