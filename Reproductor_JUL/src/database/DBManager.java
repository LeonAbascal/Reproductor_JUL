package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import reproductor.mainClasses.User;


public class DBManager {
	
	private static String databasePath = "database/JUL_database.db";
	private static Connection conn;
	static Logger logger = Logger.getLogger(DBManager.class.getName());

	private static boolean loadDriver() {
		boolean opSuccess;
		try { 
			Class.forName("org.sqlite.JDBC"); 
			logger.info("Database drivers were loaded successfully.");
			
			opSuccess = true;

		} catch (ClassNotFoundException e) { 
			logger.severe("Database drivers could not be loaded.");
			opSuccess = false;
		}

		return opSuccess;

	}

	private static boolean connect() {
		boolean opSuccess;
		
		if (loadDriver()) {
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
				opSuccess = true;
			} catch (SQLException e) {
				logger.severe("Error connecting to the database due to a SQL exception: ");
				logger.info(e.toString());
				opSuccess = false;
			}
		} else {
			opSuccess = false;
			logger.severe("Database driver could not be loaded, thus, the connection can not be opened.");
		}
		
		
		return opSuccess;
	}
	
	private static boolean disconnect() {
		boolean opSuccess;
		try {
			conn.close();
			logger.info("Connection successfully closed.");
			opSuccess = true;
		} catch (SQLException e) {
			logger.severe("Error closing the connection.");
			opSuccess = false;
		}
		
		return opSuccess;
	}
	
	
	public static String getDatabaseFilePath() {
		return DBManager.databasePath;
	}


	public static List<User> getAllUsers() {
		connect();

		List<User> users = new ArrayList<User>();

		try (Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery("SELECT * FROM user_t");

			while(rs.next()) {
				User user = new User();
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("pass"));
				users.add(user);
			}
			
			logger.info("All users were obtained succesfully");

		} catch (SQLException e) {
			logger.severe("Error retrieving all users from database.");
			logger.info(e.toString());
		}
		
		disconnect();
		return users;
	}
	public static void store(User user) {
		connect();
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_t (username,pass) VALUES (?, ?)")){

			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Could not store the user into the database");
			logger.info(e.toString());
		}
		disconnect();
	}

}
