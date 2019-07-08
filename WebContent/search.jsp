<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="em.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轻松短租网 - ${ q }</title>
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
</style>
<link rel="icon" href="${ pageContext.request.contextPath }/favicon.ico">
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/style.css">
</head>
<body>
	<div>
	<form id="search-form" action="${ pageContext.request.contextPath }/search.html">
      <input id="search" autocomplete="off" 
      	placeholder="请输入要搜索的内容, 如&quot;北京 四合院&quot;、&quot;中南海 别墅&quot;、&quot;武汉 旅馆&quot; _" name="q">
      <button id="search-icon" type="submit">
      	<font>搜索</font>
      </button>
    </form>
    </div>
	<%
		String strpage = request.getParameter("page");
		int p = 1;
		Integer pn = (Integer) request.getAttribute("pn");
		if (strpage != null && !"".equals(strpage))
			p = Integer.parseInt(strpage);
	%>
	<p>
		<a href="?q=${ q }">首页</a>
		<a href="?q=${ q }&page=<%= (p - 1) < 1 ? 1 : (p - 1) + "" %>">上一页</a>
		<a href="?q=${ q }&page=<%= (p + 1) >= pn  ? (pn == 0 ? 1 : pn) : (p + 1) + "" %>">下一页</a>
		<a href="?q=${ q }&page=${ pn == 0 ? 1 : pn }">尾页</a>
	</p>
	
	<p>
		<h2>${ nothing }</h2>
	</p>
	
	<%
		for (HouseItem h : (List<HouseItem>) request.getAttribute("list")) {
			out.println("<div>" + h.name + "</div>");
		}
	%>

	<jsp:include page="/css/footer.jsp"></jsp:include>
</body>
	<script type="text/javascript" defer="defer">
		var a = document.getElementById("search");
		a.value = '${ q }';
		
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
<%@ page trimDirectiveWhitespaces="true" %>