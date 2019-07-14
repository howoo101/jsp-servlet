<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title>Document</title>
</head>
<body>
	<a href="index.jsp">홈으로</a>
    <form action="/login/loginAction" method="post">
        id : <input type="text" name='id'>
        pwd : <input type="password" name="pwd">
        <input type="submit" value="로그인">
    </form>
</body>
</html>