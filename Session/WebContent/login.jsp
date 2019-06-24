<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%
	//어디서 login페이지로 왔는지?
	String caller = request.getParameter("from");
	
	//체크박스쿠키가 체크되어있을때 true가된다.
		Cookie[] cookies = request.getCookies();
		String id = "";
		String chk = "";
			if(cookies != null) { //쿠키 있을때  안에있는 쿠키들을 확인한다.
			for(int i = 0; i < cookies.length; i++) {
				//체크박스에대한 쿠키이름은 mem이다.
				if(cookies[i].getName().equals("mem")) {
					//쿠키가 있으면 id에 값을 받아서 넣어주고 체크유무를 checked로 바꿔준다.
					chk = "checked";
					id= cookies[i].getValue();
				}
			}			
		}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href=<%= request.getContextPath() %>>go Home</a>
	<form action=<%=request.getContextPath()+ "/loginAction"%> method="post">
		
		id: <input type="text" placeholder="id" name="id" value=<%= id %> > <br />
		pwd: <input type="password"  placeholder="pwd" name="pwd"/> <br />
			 <input type="hidden" name="caller" value="<%=caller%>">
			<input type="checkbox" name="mem" value="ok" <%=chk %> > id기억하기 <br />
			 <input type="submit" value="제출">
	</form>
</body>
</html>