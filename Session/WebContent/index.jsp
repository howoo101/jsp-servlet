<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
	/*  session에 로그인 유무에  대한 정보가 담겨 있어야한다.  */
	String id = (String)session.getAttribute("login");
	boolean notLogin = (id == null) ? true : false;
	//로그인 안되어 있으면
	String url = ""; //어디로 전송하는지
	String view = ""; //화면에보여줄 글자
	// 로그인 안되어있으면 Login 링크 보여주기 
	// login 되어있으면 Logout 링크 보여주기 
	if(notLogin) {
		url = "login.jsp?from=index.jsp";
		view = "Login";
	}else {
		url = "logoutAction";
		view = "LogOut";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<a href=<%=""%>>Home</a>
		<a href=<%=url %> ><%=view %></a>
		<a href=<%="board.jsp" %>>Board</a>
	</div>
</body>
</html>