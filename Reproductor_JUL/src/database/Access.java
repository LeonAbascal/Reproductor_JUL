package database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Access {
	// https://github.com/xerial/sqlite-jdbc/releases
	private static String databasePath = "database/JUL_database.db";
	
	public static boolean test() {
		boolean opSuccess;
		if (loadDriver()) {
			
			try { 
				Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
				Statement stmt = conn.createStatement();
				
				ResultSet rs =  stmt.executeQuery("select * from user_t;");
				
				while (rs.next()) {
					System.out.println(rs.getString("nombre"));
					System.out.println(rs.getString("apellido"));
					
					int rows = stmt.executeUpdate("insert into user_t values ('Albert', 'Einstein')");
					System.out.println(rows);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				opSuccess = true;
			} catch (SQLException e) { 
				e.printStackTrace(); 
				opSuccess = false;
				}
			
		} else {
			opSuccess = false;
		}
		
		return opSuccess;
	}
	
	private static boolean loadDriver() {
		boolean opSuccess;
		try { 
			Class.forName("org.sqlite.JDBC"); 
			System.out.println("Los drivers se cargaron correctamente."); 
			opSuccess = true;
			
		} catch (ClassNotFoundException e) { 
			System.err.println("No se pudo cargar el driver para la base de datos."); 
			opSuccess = false;
			}
		
		return opSuccess;
				
	}
	
	public static String getDatabaseFilePath() {
		return Access.databasePath;
	}
}
