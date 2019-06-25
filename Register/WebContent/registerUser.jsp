<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="member" scope="request" class="member.MemberInfo"/>
<%  
	//회원가입 실패해서 다시돌아왔는지...
	String join = request.getParameter("join");

	String id = "";
	String pwd = "";
	String mail = "";
	String name = "";
	
	if(join != null) {
	 id = member.getId();
	 pwd = member.getPwd();
	 mail = member.getMail();
	 name = member.getName();
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%if(join != null) {%>
	<script>
		alert("실패 이미 있는 id입니다.");
	</script>
	<%} %>
	<form action="registerAction.jsp" method="post">
		id: <input type="text" name="id" value="<%=id %>"><br> 
		이름: <input type="text" name="name" value="<%=name %>"><br> 
		pwd: <input type="text" name="pwd"><br>
		email: <input type="text" name="mail" value="<%=mail %>"><br>
		<input type="submit" value="가입">
	</form>
</body>
</html>