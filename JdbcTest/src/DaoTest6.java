import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class DaoTest6 {
	static String DB_URL = "jdbc:mysql://localhost:3306/jsptest?useUnicode=true&characterEncoding=utf8"; // DB이름인
																											// book_ex를
																											// 적절히 변경
	static String DB_USER = "root"; // DB의 userid와 pwd를 알맞게 변경
	static String DB_PASSWORD = "1234";

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) throws SQLException {

		List<User6> list = selectAllUsers();
		System.out.println(list);
	}

	/*
	 * 6.user_info테이블에 있는 모든 행을 반환하는 selectAllUsers메서드를 작성하시오.
	 */
	static List<User6> selectAllUsers() throws SQLException
	{
    	String query = "select * from user_info";
    	List<User6> list = new ArrayList<>();
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
	        pstmt = (PreparedStatement) conn.prepareStatement(query);    // Statement를 가져온다.
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	        	User6 user = new User6();
	        	user.setId(rs.getString("user_id"));
	        	user.setName(rs.getString("user_name"));
	        	user.setMail(rs.getString("user_mail"));
	        	user.setPwd(rs.getString("user_pwd"));
	        	list.add(user);
	        }
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
    	
    	return list;
    }

	/*
	 * 5. user_id를 매개변수로 받아 user_info테이블에서 해당 사용자의 정보를 가져오는 selectUser메서드를 작성하시오
	 */
	static int deleteUser(String userId) throws SQLException {
		String query = "DELETE FROM user_info where user_id = ?";

		try {
			// 드라이버를 로딩한다.
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
			pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
			pstmt.setString(1, userId);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return 0;
	}

	/*
	 * 4. user_id를 매개변수로 받아 user_info테이블에서 해당 사용자의 정보를 가져오는 selectUser메서드를 작성하시오.
	 */
	static User6 selectUser(String userId) throws SQLException {
		String query = "select * from user_info where user_id = ?";

		try {
			// 드라이버를 로딩한다.
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
			pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				User6 user = new User6();
				user.setId(rs.getString("user_id"));
				user.setName(rs.getString("user_name"));
				user.setPwd(rs.getString("user_pwd"));
				user.setMail(rs.getString("user_mail"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return null;

	}

	/*
	 * 3. User객체를 매개변수로 받아 DB의 user_info테이블에 update하는 updateUser()메서드를 작성하시오. (실제로
	 * 데이터가 잘저장되었는지 DB에서 확인하시오)
	 */
	static int updateUser(User6 u) throws SQLException {

		String query = "update user_info set user_id=?, user_name=? , user_pwd=? ," + "user_mail=? where user_id = ?";
		try {
			// 드라이버를 로딩한다.
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
			pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.

			pstmt.setString(1, u.id);
			pstmt.setString(2, u.name);
			pstmt.setString(3, u.pwd);
			pstmt.setString(4, u.mail);
			pstmt.setString(5, u.id);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return 0;
	}

	/*
	 * 2. User객체를 매개변수로 받아 DB의 user_info테이블에 insert하는 insertUser()메서드를 작성하시오. (실제로
	 * 데이터가 잘저장되었는지 DB에서 확인하시오)
	 */
	static int insertUser(User6 u) throws SQLException {
		/*
		 * insertUser()메서드를 작성하시오.(PreparedStatement)
		 */

		String query = "insert into user_info (user_id, user_name, user_pwd, user_mail)" + "values(?,?,?,?)";
		try {
			// 드라이버를 로딩한다.
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 설정한다.
			pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.

			pstmt.setString(1, u.id);
			pstmt.setString(2, u.name);
			pstmt.setString(3, u.pwd);
			pstmt.setString(4, u.mail);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}

		return 0;
	}

} // end of class

class User6 {

	String id;
	String name;
	String pwd;
	String mail;

	User6() {

	}

	User6(String id, String name, String pwd, String mail) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.mail = mail;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPwd() {
		return pwd;
	}

	public String getMail() {
		return mail;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String toString() {
		return "id: " + id + " name: " + name + "pwd: " + pwd + "mail: " + mail;
	}
}