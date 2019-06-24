import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DaoTest2 {
	public static void main(String[] args) {
		User2 u = new User2("ff22f", "남궁성", "1234", "aaa@aaa.com");
		System.out.println(u);
		insertUser(u); // 객체 u에 저장된 값을 DB의 user_info테이블에 insert하는 메서드
	}

	/*
	 * 2. User객체를 매개변수로 받아 DB의 user_info테이블에 insert하는 insertUser()메서드를 작성하시오. (실제로
	 * 데이터가 잘저장되었는지 DB에서 확인하시오)
	 */
	static int insertUser(User2 u) {
		/*
		 * insertUser()메서드를 작성하시오.(PreparedStatement)
		 */
		String DB_URL = "jdbc:mysql://localhost:3306/jsptest?useUnicode=true&characterEncoding=utf8"; // DB이름인 book_ex를 적절히 변경 
	     String DB_USER = "root";  // DB의 userid와 pwd를 알맞게 변경 
	     String DB_PASSWORD = "1234"; 
	     
	     Connection conn = null;
	     PreparedStatement pstmt = null;
	     
	     String query = "insert into user_info (user_id, user_name, user_pwd, user_mail)"
	     		+ "values(?,?,?,?)";
	     try {
	         // 드라이버를 로딩한다.
	         Class.forName("com.mysql.jdbc.Driver");
	         
	         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
	         pstmt = (PreparedStatement) conn.prepareStatement(query);             // Statement를 가져온다.

	         pstmt.setString(1,u.id);
	         pstmt.setString(2,u.name);
	         pstmt.setString(3,u.pwd);
	         pstmt.setString(4,u.mail);
	         return pstmt.executeUpdate();
	         
	        } catch ( Exception e ) {
	         e.printStackTrace();
	        } finally {
	         try {
	          pstmt.close();
	          conn.close();
	         } catch ( SQLException e ) {}
	        }
	       
		return 1;
	}

} // end of class

class User2 {
	String id;
	String name;
	String pwd;
	String mail;

	User2(String id, String name, String pwd, String mail) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.mail = mail;
	}

	public String toString() {
		return "id: " + id + "name: " + name + "pwd: " + pwd + "mail: " + mail;
	}
}