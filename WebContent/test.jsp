<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>教你如何更改网页的默认alert弹窗</title>
</head>
<body>
Hello
</body>
<script src="js/alert.js"></script>
<script type="text/javascript">
var info = "a<br>b";
if (info !== "")
	alert(info, function() { ${ action } });
</script>
</html>