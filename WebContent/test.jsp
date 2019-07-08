<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - 四川</title>
<style type="text/css">
body {
	text-align: center;
}
</style>
<style type="text/css">
	#title {
		color: pink;
		text-shadow: -1px 0 black, 0 2px black, 1px 0 black, 0 -1px black;
		font-size: 3em;
	}
	
	font {
		color: white;
		text-shadow: black 0.1em 0.1em 0.2em;
	}
	
	#search {
		height: 28px;
		width: 31.25%;
		margin-top: 20px;
	}
	
	#search-icon{
		text-align: center;
		background: none;
		color: #ffffff;
		font-size: 16px;
		white-space: normal;
		margin-top: 0px;
		border: none;
		border-color: #4CAF50;
		border-radius: 0px;
		outline: none;
	}
	
	div.list {
		background-color: white;
		text-align: center;
	}
</style>
<link rel="icon" href="/Demo/favicon.ico">
<link rel="stylesheet" href="/Demo/css/style.css">
</head>
<body>
	<div>
	<form id="search-form" action="/Demo/search.html">
      <input id="search" autocomplete="off" autofocus="autofocus"
      	placeholder="请输入要搜索的内容, 如&quot;北京 四合院&quot;、&quot;中南海 别墅&quot;、&quot;武汉 旅馆&quot; _" name="q">
      <button id="search-icon" type="submit">
      	<font>搜索</font>
      </button>
    </form>
    </div>
	<p>
		<a href="?q=四川">首页</a>
		<a href="?q=四川&page=1">上一页</a>
		<a href="?q=四川&page=1">下一页</a>
		<a href="?q=四川&page=1">尾页</a>
	</p>
	
	<h2></h2>
	
	<hr>
	<div class="list">
		<table>
			<tr>
			</tr>
		</table>
	<img width="200px" height="150px" src='/Demo/upload/124_Lighthouse.jpg'/><span>四川土坯房124</span>
	</div>
	<hr>
	
	<p>
		<a href="?q=四川">首页</a>
		<a href="?q=四川&page=1">上一页</a>
		<a href="?q=四川&page=1">下一页</a>
		<a href="?q=四川&page=1">尾页</a>
	</p>

	<div style="height: 100px;"></div>
	<div class="footer">
		<a href="/Demo/index.html">轻松短租网</a><br>
		<font>©2019</font>
	</div>
</body>
	<script type="text/javascript" defer="defer">
		var a = document.getElementById("search");
		a.value = '四川';
		
		var title = a.placeholder; // 完整标题
		var i = 0;

		function t() {
			a.placeholder = title.substr(0, i);
			i ++ ;
			if (i >= title.length + 10)
				i = 0;
			setTimeout(t, 100);
		}
		
		setTimeout(t, 100);
	</script>
</html>
