<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Array"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="db.Connector"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// db연결
//	Connection conn = new Connector().getConn();
//	ResultSet rs = null;
//
//	Cookie[] cookies = request.getCookies();
//	
//	// 쿼리문 실행 결과 받아온 값들을 받을 리스트
//	List menu = new ArrayList(); // 메뉴명
//	List totRevenue = new ArrayList(); // 총 수입
//	List lastOrder = new ArrayList(); // 최근 주문일
//	List cnt = new ArrayList(); // 총 주문 수량
//	
//	String tmp = "";
//	String storeName = "";
//	int sum = 0;
//	
//	// loginAction.java에서 manage.jsp로 storeCode 넘겨준 것 받기
//	if(cookies!=null && cookies.length>0) {
//		for(int i=0; i<cookies.length; i++) {
//			String getCookieName = cookies[i].getName();
//			// 받아온 쿠키의 이름이 storeCode인지 확인
//			if(getCookieName.equals("storeCode")) {
//				// db에서 storeCode 기준으로 그 가게에 해당하는
//				// 메뉴명, 판매 수량(>> 나중에는 단위기간(한달, 일주일, 하루 등....)당 판매수량으로 고칠 것), 판매 총액(이것도 단위기간당으로 고칠 것), 최근 구매일 받아온다
//				
//				// (전체 주문에 대한)메뉴명, 최근 주문일, 총 주문 수량, 총 수입, 가게코드
//				String query = "select menuName, last_order, cnt, cnt*menuPrice as totalRevenue, m.storeCode from (select menuCode, max(date_format(rDate, '%Y.%m.%d')) as last_order, count(*) as cnt from revenue group by menuCode) r1, menu m where r1.menuCode = m.menuCode";
//				
//				// 특정 가게에 대한 위의 컬럼값들
//				PreparedStatement pstmt = conn.prepareStatement("select * from ("+query+") mm where mm.storeCode=?;");
//				pstmt.setString(1, cookies[i].getValue()); // 쿠키값으로 넘겨받은 storeCode를 쿼리문 조건에 넣는다.
//				
//				
//				rs = pstmt.executeQuery();
//				// 쿼리문으로 받아온 값들을 리스트에 각각 저장한다.
//				while (rs.next()) {
//					menu.add(rs.getString("menuName"));
//					totRevenue.add(rs.getInt("totalRevenue"));
//					lastOrder.add(rs.getString("last_order"));
//					cnt.add(rs.getString("cnt"));
//					
//					sum += rs.getInt("totalRevenue");
//				}
//				
//				// 조회한 내용들을 테이블 형식으로 만들어 String 변수에 넣는다.
//				for(int j=0; j<menu.size(); j++) {
//					tmp += "<tr><td>"+menu.get(j)+"</td><td>"+cnt.get(j)+"</td><td>"+totRevenue.get(j)+"</td><td>"+lastOrder.get(j)+"</td></tr>";
//				}
//				
//				// 가게명을 받아오는 쿼리
//				PreparedStatement pstmt2 = conn.prepareStatement("select storeName from store where storeCode=?;");
//				pstmt2.setString(1, cookies[i].getValue()); // 쿠키값으로 넘겨받은 storeCode를 쿼리문 조건에 넣는다.(storeCode에 맞는 가게명이 나올 것)
//				ResultSet rs2 = pstmt2.executeQuery();
//				
//				if(rs2.next()) {
//					storeName = rs2.getString("storeName");
//				}
//			}
//		}
//	}
%>


<%
	Cookie[] cookies = request.getCookies();
	String[] newInfo = new String[3];
	if(cookies!=null && cookies.length>0) {
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("newInsert")) {
				String insertInfo = URLDecoder.decode(cookies[i].getValue(), "utf-8");
				// totRevenue, storeName, tableRows 순서로 들어있음
				newInfo = insertInfo.split(",");
			}
		}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
table {
	border: 1px solid black;
	width: 80%;
	border-collapse: collapse;
}

td {
	border: 1px solid black;
}

thead {
	background-color: gray;
}
</style>
</head>
<body>
	<a href="/login/logout">로그아웃</a>
	<h1><%= newInfo[1] %>의 매출 현황</h1><br><br>
	<table>
		<thead>
			<tr>
				<td>메뉴명</td>
				<td>매출 수량</td>
				<td>총 매출</td>
				<td>last order</td>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	총 수입 : <%=newInfo[0] %>원
	
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script>
         $(document).ready(function() {
           	$('tbody').append('<%=newInfo[2]%>');
        })
    </script>

</body>
</html>