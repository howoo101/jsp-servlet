package member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//[DAO패턴 연습8] Dao클래스를 작성하고 UserDao가 상속받도록 변경하시오.

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

 public class UserDao extends Dao{
	 private static UserDao userDao;
	 
	 PreparedStatement pstmt = null;
	 ResultSet rs = null;
	 public static UserDao getInstance() {
		if(userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}
	 
	private UserDao() {
		super("user_info");
	}
	 /*
		 * 6.user_info테이블에 있는 모든 행을 반환하는 selectAllUsers메서드를 작성하시오.
		 */
		 public List<MemberInfo> selectAllUsers() throws SQLException
		{
	    	String query = "select * from "+ tableName;
	    	List<MemberInfo> list = new ArrayList<>();
	    	try {
	    		connect();
	    		pstmt =  (PreparedStatement)conn.prepareStatement(query);    // Statement를 가져온다.
		        rs = pstmt.executeQuery(query);
		        while(rs.next()) {
		        	MemberInfo user = new MemberInfo();
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
		 MemberInfo selectUser(String userId) throws SQLException {
			String query = "select * from"+ tableName+" where user_id = ?";

			try {
				// 드라이버를 로딩한다
				connect();
				pstmt = (PreparedStatement) conn.prepareStatement(query); // Statement를 가져온다.
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					MemberInfo user = new MemberInfo();
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
		 public int updateUser(MemberInfo u) throws SQLException {

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
		 public int insertUser(MemberInfo u) throws SQLException {
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
				System.out.println(2);
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
