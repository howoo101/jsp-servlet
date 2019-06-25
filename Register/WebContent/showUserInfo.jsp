<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="member" scope="request" class="member.MemberInfo"/>
<%request.setCharacterEncoding("utf-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table width="400" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td>id</td>
			<td>name</td>
			<td>pwd</td>
			<td>mail</td>
		</tr>
		<tr>
			<td><jsp:getProperty property="id" name="member"/></td>
			<td><jsp:getProperty property="name" name="member"/></td>
			<td><jsp:getProperty property="pwd" name="member"/></td>
			<td><jsp:getProperty property="mail" name="member"/></td>
		</tr>
	</table>
</body>
</html>