package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	Connection conn = null;
	
	public Connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcDriver = "jdbc:mysql://192.168.0.23/foodcourt";
			String dbUser = "devfood";
			String dbPass = "1234";
			try {
				conn = DriverManager.getConnection(jdbcDriver,dbUser,dbPass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public Connection getConn() {
		return conn;
	}
	
}
