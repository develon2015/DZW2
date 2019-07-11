<%@page import="controller.LoginController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="common.*"%>
<%@page import="em.*"%>
<%
	// SysUtil.test();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网</title>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css"/>
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

</style>
</head>
<body>
	<h1 id="title">寻找旅行中的家</h1>
	<%
		//User user = (User) request.getAttribute("user");
		User user = (User) LoginController.getUser(request, response);
		if (user == null) {
	%>
			<a href="${ pageContext.request.contextPath }/user/login.html">登录</a>&nbsp;/&nbsp;<a href="${ pageContext.request.contextPath }/user/register.html">注册</a>
	<% } else { %>
			<a href="${ pageContext.request.contextPath }/user/show.html"><%= user.getName() %></a>
			&nbsp;
			<a href="${ pageContext.request.contextPath }/user/logout.html">退出</a>
			&nbsp;
			<a href="${ pageContext.request.contextPath }/lease.html">免费发布房间</a>
	<% } %>
	<p/>
	<div>
	<form id="search-form" action="${ pageContext.request.contextPath }/search.html">
      <input id="search" autocomplete="off" autofocus="autofocus"
      	placeholder="请输入要搜索的内容, 如&quot;北京 四合院&quot;、&quot;中南海 别墅&quot;、&quot;武汉 旅馆&quot; _" name="q">
      <button id="search-icon" type="submit">
      	<font>搜索</font>
      </button>
    </form>
    </div>
	
	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
	<script type="text/javascript" defer="defer">
		var a = document.getElementById("search");
		//console.log(a.placeholder);
		
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
		
		var b = document.getElementById("title");
		//console.log(b.innerText);
		
		var title2 = b.innerText; // 完整标题
		var i2 = 1;

		function t2() {
			b.innerText = title2.substr(0, i2);
			//console.log(b.innerText);
			i2 ++ ;
			if (i2 >= title2.length + 60)
				i2 = 1;
			setTimeout(t2, 100);
		}
		
		setTimeout(t2, 1);
	</script>
</html>
<%@page trimDirectiveWhitespaces="true"%>