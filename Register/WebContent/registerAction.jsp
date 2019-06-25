<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.*" %>
<jsp:useBean id="member" scope="request" class="member.MemberInfo"/>
<jsp:setProperty property="*" name="member"/>
<%
	System.out.println(member);
%>
<!-- db랑 연결하고 나서 id가 있으면 다시 레지스터 페이지로 가는거고 없으면 회원가입 완료했으니까  show 하면된다.-->
	
<%
		UserDao userdao = UserDao.getInstance();
		int result = userdao.insertUser(member);
		String url = "";
		if(result == 0) url = "registerUser.jsp?join=false";
		else url = "showUserInfo.jsp";
%>

<jsp:forward page="<%=url %>" />

