<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<% 
		//현재  uri에서  contextRoot 제외한 경로를 문자열로 만들기
		String caller = request.getRequestURI()
				.substring(request.getContextPath().length()+1);
		/* 
			로그인 했으면 게시판 출력할건데 
			로그안 안돼있으면 login.jsp로 이동
		*/
		String id = (String)session.getAttribute("login");
		boolean notLogin = (id == null) ? true: false;
		if(notLogin) response.sendRedirect("login.jsp?from="+ caller);
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	board 게시판
	<a href="index.jsp">go home</a>
</body>
</html>