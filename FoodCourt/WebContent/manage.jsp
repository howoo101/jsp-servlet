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
	// db����
//	Connection conn = new Connector().getConn();
//	ResultSet rs = null;
//
//	Cookie[] cookies = request.getCookies();
//	
//	// ������ ���� ��� �޾ƿ� ������ ���� ����Ʈ
//	List menu = new ArrayList(); // �޴���
//	List totRevenue = new ArrayList(); // �� ����
//	List lastOrder = new ArrayList(); // �ֱ� �ֹ���
//	List cnt = new ArrayList(); // �� �ֹ� ����
//	
//	String tmp = "";
//	String storeName = "";
//	int sum = 0;
//	
//	// loginAction.java���� manage.jsp�� storeCode �Ѱ��� �� �ޱ�
//	if(cookies!=null && cookies.length>0) {
//		for(int i=0; i<cookies.length; i++) {
//			String getCookieName = cookies[i].getName();
//			// �޾ƿ� ��Ű�� �̸��� storeCode���� Ȯ��
//			if(getCookieName.equals("storeCode")) {
//				// db���� storeCode �������� �� ���Կ� �ش��ϴ�
//				// �޴���, �Ǹ� ����(>> ���߿��� �����Ⱓ(�Ѵ�, ������, �Ϸ� ��....)�� �Ǹż������� ��ĥ ��), �Ǹ� �Ѿ�(�̰͵� �����Ⱓ������ ��ĥ ��), �ֱ� ������ �޾ƿ´�
//				
//				// (��ü �ֹ��� ����)�޴���, �ֱ� �ֹ���, �� �ֹ� ����, �� ����, �����ڵ�
//				String query = "select menuName, last_order, cnt, cnt*menuPrice as totalRevenue, m.storeCode from (select menuCode, max(date_format(rDate, '%Y.%m.%d')) as last_order, count(*) as cnt from revenue group by menuCode) r1, menu m where r1.menuCode = m.menuCode";
//				
//				// Ư�� ���Կ� ���� ���� �÷�����
//				PreparedStatement pstmt = conn.prepareStatement("select * from ("+query+") mm where mm.storeCode=?;");
//				pstmt.setString(1, cookies[i].getValue()); // ��Ű������ �Ѱܹ��� storeCode�� ������ ���ǿ� �ִ´�.
//				
//				
//				rs = pstmt.executeQuery();
//				// ���������� �޾ƿ� ������ ����Ʈ�� ���� �����Ѵ�.
//				while (rs.next()) {
//					menu.add(rs.getString("menuName"));
//					totRevenue.add(rs.getInt("totalRevenue"));
//					lastOrder.add(rs.getString("last_order"));
//					cnt.add(rs.getString("cnt"));
//					
//					sum += rs.getInt("totalRevenue");
//				}
//				
//				// ��ȸ�� ������� ���̺� �������� ����� String ������ �ִ´�.
//				for(int j=0; j<menu.size(); j++) {
//					tmp += "<tr><td>"+menu.get(j)+"</td><td>"+cnt.get(j)+"</td><td>"+totRevenue.get(j)+"</td><td>"+lastOrder.get(j)+"</td></tr>";
//				}
//				
//				// ���Ը��� �޾ƿ��� ����
//				PreparedStatement pstmt2 = conn.prepareStatement("select storeName from store where storeCode=?;");
//				pstmt2.setString(1, cookies[i].getValue()); // ��Ű������ �Ѱܹ��� storeCode�� ������ ���ǿ� �ִ´�.(storeCode�� �´� ���Ը��� ���� ��)
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
				// totRevenue, storeName, tableRows ������ �������
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
	<a href="/login/logout">�α׾ƿ�</a>
	<h1><%= newInfo[1] %>�� ���� ��Ȳ</h1><br><br>
	<table>
		<thead>
			<tr>
				<td>�޴���</td>
				<td>���� ����</td>
				<td>�� ����</td>
				<td>last order</td>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	�� ���� : <%=newInfo[0] %>��
	
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script>
         $(document).ready(function() {
           	$('tbody').append('<%=newInfo[2]%>');
        })
    </script>

</body>
</html>