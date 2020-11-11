package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import reproductor.mainClasses.User;


public class DBManager {
	
	private static String databasePath = "database/JUL_database.db";
	private static Connection conn; 

	private static boolean loadDriver() {
		boolean opSuccess;
		try { 
			Class.forName("org.sqlite.JDBC"); 
			System.out.println("Drivers were loaded successfully."); 
			opSuccess = true;

		} catch (ClassNotFoundException e) { 
			System.err.println("Drivers could not be loaded."); 
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
				System.err.println("Error connecting to the database.");
				opSuccess = false;
			}
		} else {
			opSuccess = false;
			System.err.println("Driver could not be loaded, thus, the connection can not be opened.");
		}
		
		
		return opSuccess;
	}
	
	private static boolean disconnect() {
		boolean opSuccess;
		try {
			conn.close();
			System.out.println("Connection closed.");
			opSuccess = true;
		} catch (SQLException e) {
			System.err.println("Error closing the connection.");
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

		} catch (SQLException e ) {
			System.out.println("Error obteniendo todos los usuarios'");
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
			e.printStackTrace();
			System.out.println("No se pudo guardar el usuario en la BD");
		}
		disconnect();
	}

}
