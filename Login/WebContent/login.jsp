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
	boolean chk = false;
		
	Cookie[] cookies = request.getCookies();
	String id = "";
	
		for(int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals("mem")) {
				if(cookies[i].getValue().equals("ok")){
					chk = true;
				}
			}
			if(cookies[i].getName().equals("id")) {
				id = cookies[i].getValue();
			}
		}			
		
		
%>
<%=id %>
<%=chk %>
	<form action="/loginAction" method="get">
		<% 
			//쿠키의 mem 값이 ok(check)면 아이디에 값 넣어줄꺼임 
				if(chk) {
		%>
		id: <input type="text" placeholder="id" name="id" value=<%= id %> > <br />
		pwd: <input type="password"  placeholder="pwd" name="pwd"/> <br />
			<input type="checkbox" name="mem" value="ok" checked/> id기억하기 <br />
		<%
				}
				else {
		%>
					id: <input type="text" placeholder="id" name="id"><br />
					pwd: <input type="password"  placeholder="pwd" name="pwd"/> <br />
						<input type="checkbox" name="mem" value="ok" /> id기억하기 <br />
		
		<%
				}
		%>
		 <input type="submit" value="제출">
	</form>
</body>
</html>