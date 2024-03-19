package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {
	
	private static final String DB_NAME = "restaurant";
	private static final String DB_IP= "127.0.0.1";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	public static Connection getDBConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_IP+"/"+DB_NAME, DB_USER, DB_PASSWORD);
		} catch (Exception e) {
			System.err.println("connection to database failed");
		}
		
		return connection;
	}
	

}
