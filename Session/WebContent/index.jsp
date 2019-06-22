<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		/*  session에 로그인 유무에  대한 정보가 담겨 있어야한다.  */
		String id = (String)session.getAttribute("login");
		boolean notLogin = (id == null) ? true : false;
	
	%>
	<div>
		<a href=<%=""%>>Home</a>
		<!-- 로그인 안되어있으면 Login 링크 보여주기 -->
		<%if(notLogin) {%>
		<a href=<%="login.jsp?from=index.jsp" %> >Login</a>
		<%} else { %>
		<!-- login 되어있으면 Logout 링크 보여주기 -->
		<a href=<%="logoutAction" %>>LogOut</a>
		<%} %>
		<a href=<%="board.jsp" %>>Board</a>
	</div>
</body>
</html>