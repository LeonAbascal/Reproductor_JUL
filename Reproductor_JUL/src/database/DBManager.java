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
	
private Connection conn = null; 
	
	public void connect(String dbPath) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (ClassNotFoundException e) {
			System.out.println("Error cargando el driver de la BD");
		} catch (SQLException e) {
			System.out.println("Error conectando a la BD");
		}
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error cerrando la conexión con la BD");
		}
	}
	
	public List<User> getAllUsers() {
		
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
		
		return users;
	}
	public void store(User user) {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_t (username,pass) VALUES (?, ?)")){
				
				stmt.setString(1, user.getName());
				stmt.setString(2, user.getPassword());
				
				stmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("No se pudo guardar el usuario en la BD");
			}
	}
	
}
