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
	<h1 id="title">寻找旅行中的家</h1>
	
	<div>
	<form id="search-form" action="${ pageContext.request.contextPath }/search.html">
      <input id="search" autocomplete="off" autofocus="autofocus"
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
		&nbsp;(<%= p %>/${ pn })
	</p>
	
	<% if (request.getAttribute("nothing") != null) { %>
	<h2>${ nothing }</h2>
	<% } else { %>
	
	<table style="margin: auto">
		<tr>
			<th style="text-align: left;"><div>图片</div></th>
			<th><div>名称</div></th>
			<th><div>地址</div></th>
			<th><div>面积</div></th>
			<th><div>人数</div></th>
			<th><div>时间</div></th>
			<th><div>价格</div></th>
			<th style="text-align: right;"><div>链接</div></th>
		</tr>
	<% List<?> list = (List<?>) request.getAttribute("list");
		for (Object e : list) {
			HouseItem h = (HouseItem) e;%>
		<tr>
			<td style="text-align: left;"><div><img height="250px" src='<%= h.icon %>'/></div></td>
			<td><div><%= h.name %></div></td>
			<td><div><%= h.address %></div></td>
			<td><div><%= h.area %>&nbsp;m<sup>2</sup></div></td>
			<td><div>可住<%= h.pn %>人</div></td>
			<td><div>可入住<%= h.time_short %>~<%= h.time_long %>天</div></td>
			<td><div><%= h.price %>元/天</div></td>
			<td style="text-align: right;"><div><a href="${ pageContext.request.contextPath }/house.html?id=<%= h.id %>">查看</a></div></td>
		</tr>
		<tr>
		<td colspan="8"><hr></td>
		</tr>
	<% } %>
	</table>
	
	<p>
		<a href="?q=${ q }">首页</a>
		<a href="?q=${ q }&page=<%= (p - 1) < 1 ? 1 : (p - 1) + "" %>">上一页</a>
		<a href="?q=${ q }&page=<%= (p + 1) >= pn  ? (pn == 0 ? 1 : pn) : (p + 1) + "" %>">下一页</a>
		<a href="?q=${ q }&page=${ pn == 0 ? 1 : pn }">尾页</a>
		&nbsp;(<%= p %>/${ pn })
	</p>
	<% } %>

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
<%@ page trimDirectiveWhitespaces="true" %>