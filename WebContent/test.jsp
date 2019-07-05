<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style type="text/css">
        *{
            margin:0;padding:0;list-style: none;text-decoration: none;
        }
        body{
            background: #ccc;
        }
        #container{
            background-image: url(backgroundImage.jpg);width: 1613px;height: 700px;margin:0 auto;
        }
        #logo{
            float: left;margin:-5px 5px 0 0;
        }
        form{
            background: white;float: left;
        }
        #search-input{
            outline:none;width:500px;height: 50px;line-height: 50px;float: left;border:0;
 
        }
        #search-button{
            background-image: url('/favicon.jpg');background-repeat: no-repeat;background-color: white;width:40px;height: 30px;float: left;border:0;margin:8px;
        }
        #wrapper{
            position: absolute;top:200px; left: 300px;
        }
    </style>
</head>
<body>
    <div id="container">
        <div id="wrapper">
            <div id="logo">  <img src="必应.png" alt="tupian" id="logo"> </div>
            <form action="https://cn.bing.com/search" target="_self" method="get"  >
                <input type="text" id="search-input" name="q" placeholder="请输入要搜索的内容：" autofocus>
                <input type="submit" id="search-button" value="">
            </form>
        </div>
    </div>   
</body>
</html>
