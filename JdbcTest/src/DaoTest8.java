import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

//[DAO패턴 연습8] Dao클래스를 작성하고 UserDao가 상속받도록 변경하시오.
public class DaoTest8 {
	public static void main(String[] args) throws SQLException {
		UserDao udao = new UserDao();
		List<User7> list = udao.selectAllUsers();
		List<User7> list2 = udao.selectAllUsers();
		System.out.println(list);
		System.out.println(list2);

	} // main()의 끝

} // 클래스의 끝

class Dao {
	Connection conn = null;
	String tableName = "";
	
	static String DB_URL = "jdbc:mysql://localhost:3306/jsptest?useUnicode=true&characterEncoding=utf8&useSSL=false"; // DB이름인
	static String DB_USER = "root"; // DB의 userid와 pwd를 알맞게 변경
	static String DB_PASSWORD = "1234";
	
	Dao() {
		this(null, "");
	}

	Dao(String tableName) {
		this(null, tableName);
	}

	Dao(Connection conn, String tableName) {
		this.tableName = tableName;
		this.conn = conn;
	}
	
	void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void rollback() {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {

			}
		}
	}

	void close(AutoCloseable... acs) {
		for(AutoCloseable tmp : acs) {
			try {
				tmp.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

class UserDao extends Dao{
	 private UserDao userDao;
	 
	 PreparedStatement pstmt = null;
	 ResultSet rs = null;
	 public UserDao getInstance() {
		if(userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}
	 
	public UserDao() {
		super("user_info");
	}
	 /*
		 * 6.user_info테이블에 있는 모든 행을 반환하는 selectAllUsers메서드를 작성하시오.
		 */
		 List<User7> selectAllUsers() throws SQLException
		{
	    	String query = "select * from "+ tableName;
	    	List<User7> list = new ArrayList<>();
	    	try {
	    		connect();
	    		pstmt =  (PreparedStatement)conn.prepareStatement(query);    // Statement를 가져온다.
		        rs = pstmt.executeQuery(query);
		        while(rs.next()) {
		        	User7 user = new User7();
		        	user.setId(rs.getString("user_id"));
		        	user.setName(rs.getString("user_name"));
		        	user.setMail(rs.getString("user_mail"));
		        	user.setPwd(rs.getString("user_pwd"));
		        	list.add(user);
		        }
	    	} catch (Exception e) {
				e.printStackTrace();
				rollback();
			} finally {
				close(pstmt,conn);
			}
	    	
	    	return list;
	    }

		/*
		 * 5. user_id를 매개변수로 받아 user_info테이블에서 해당 사용자의 정보를 가져오는 selectUser메서드를 작성하시오
		 */
		 int deleteUser(String userId) throws SQLException {
			String query = "DELETE FROM"+ tableName+" where user_id = ?";

			try {
				// 드라이버를 로딩한다.
				connect();
				pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
				pstmt.setString(1, userId);
				return pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				rollback();
			} finally {
				close(pstmt,conn);
			}
			return 0;
		}

		/*
		 * 4. user_id를 매개변수로 받아 user_info테이블에서 해당 사용자의 정보를 가져오는 selectUser메서드를 작성하시오.
		 */
		 User7 selectUser(String userId) throws SQLException {
			String query = "select * from"+ tableName+" where user_id = ?";

			try {
				// 드라이버를 로딩한다
				connect();
				pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					User7 user = new User7();
					user.setId(rs.getString("user_id"));
					user.setName(rs.getString("user_name"));
					user.setPwd(rs.getString("user_pwd"));
					user.setMail(rs.getString("user_mail"));
					return user;
				}

			} catch (Exception e) {
				e.printStackTrace();
				rollback();
			} finally {
				close(pstmt,conn);
			}
			return null;

		}

		/*
		 * 3. User객체를 매개변수로 받아 DB의 user_info테이블에 update하는 updateUser()메서드를 작성하시오. (실제로
		 * 데이터가 잘저장되었는지 DB에서 확인하시오)
		 */
		 int updateUser(User7 u) throws SQLException {

			String query = "update"+tableName+" set user_id=?, user_name=? , user_pwd=? ," + "user_mail=? where user_id = ?";
			try {
				// 드라이버를 로딩한다.
				connect();
				pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.

				pstmt.setString(1, u.id);
				pstmt.setString(2, u.name);
				pstmt.setString(3, u.pwd);
				pstmt.setString(4, u.mail);
				pstmt.setString(5, u.id);
				return pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				rollback();
			} finally {
				close(pstmt,conn);
			}
			return 0;
		}

		/*
		 * 2. User객체를 매개변수로 받아 DB의 user_info테이블에 insert하는 insertUser()메서드를 작성하시오. (실제로
		 * 데이터가 잘저장되었는지 DB에서 확인하시오)
		 */
		 int insertUser(User7 u) throws SQLException {
			/*
			 * insertUser()메서드를 작성하시오.(PreparedStatement)
			 */
			String query = "insert into user_info (user_id, user_name, user_pwd, user_mail)" + "values(?,?,?,?)";
			try {
				// 드라이버를 로딩한다.
				connect();
				pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
				pstmt.setString(1, u.id);
				pstmt.setString(2, u.name);
				pstmt.setString(3, u.pwd);
				pstmt.setString(4, u.mail);
				return pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				rollback();
			} finally {
				close(pstmt,conn);
			}

			return 0;
		}
	
	}

class User7 {
	String id;
	String name;
	String pwd;
	String mail;

	User7() {

	}

	User7(String id, String name, String pwd, String mail) {
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