<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Jcalendar</title>
    <link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./css/Jcalendar.css">
</head>
<style>
    #date{
        margin-top: 30px;
        margin-left: 50px;
    }
</style>
<body>
    <form class="form-inline">
        <div class="form-group">
            <input id="date" class="form-control"  type="text" value="" placeholder="请选择时间" readonly/>
        </div>
    </form>
</body>
<script src="./js/Jcalendar.js"></script>
<script>
    new Jcalendar({
        input:'date',
    });
</script>
</html>
