package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
					
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//1. 사용할 jdbc 드라이버를 등록하라
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			//2. 드라이버를 사용하여 mysql 서버와 연결하라
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studydb?serverTimezone=UTC"
					,"root", "1234");
			 System.out.println(con);
			//3. 커넥션 객체로부터 sql을 던질 객체를 준비하라
			 stmt = con.createStatement();
			//4. sql을 던지는 객체를 사용하여 서버에 질의하라
			 rs = stmt.executeQuery(
					"select MNO,MNAME,EMAIL,CRE_DATE" +
					" from MEMBERS"+
					" order by MNO ASC");
			//5. 서버에 가져온 데이터를 사용하여 html을 만들어서 웹브라우저로 출력하라.
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head><body>");
			while(rs.next()) {
				out.println(rs.getInt("mno") + "," +
						rs.getString("MNAME") + "," +
						rs.getString("email") + "," +
						rs.getDate("cre_date") + "<br>");
				
			}
			out.println("</body></html>");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException();
		} finally {
			try{rs.close();} catch(Exception e) {}
			try{stmt.close();} catch(Exception e) {}
			try{con.close();} catch(Exception e) {}
		}
		
	}

}
