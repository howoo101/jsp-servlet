<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
		/* 
			로그인 했으면 게시판 출력할건데 
			로그안 안돼있으면 login.jsp로 이동
		*/
		String id = (String)session.getAttribute("login");
		boolean notLogin = (id == null) ? true: false;
		if(notLogin) response.sendRedirect("/login.jsp?from=board.jsp");
	%>
	board 게시판
	<a href="/">go home</a>
</body>
</html>