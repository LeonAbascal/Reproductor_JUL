package database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Access {
	// USAREMOS EL JDBC PARA GESTIONAR LA BASE DE DATOS. SIN EMBARGO, NECESITAMOS EL DRIVER
	// https://github.com/xerial/sqlite-jdbc/releases
	private static String databasePath = "database/JUL_database.db";
	
	public static boolean test() {
		boolean opSuccess;
		if (loadDriver()) {
			
			try { 
				// AHORA NOS CONECTAMOS A LA BASE DE DATOS
				// FORMATO: jdbc:nuestroTipoDeBdDD:IP/path
				// PARA BASES DE DATOS HAY QUE ASEGURARNOS QUE IMPORTA LAS COSAS DE JAVA.SQL
				Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
				// CREAR UN OBJETO QUE SIRVE DE CONSOLA DE COMANDOS PARA LA BASE DE DATOS
				Statement stmt = conn.createStatement();
				
				// rs from JAVA.SQL
				ResultSet rs =  stmt.executeQuery("select * from user_t;");
				
				while (rs.next()) {
					System.out.println(rs.getString("nombre"));
					System.out.println(rs.getString("apellido"));
					
					int rows = stmt.executeUpdate("insert into user_t values ('Albert', 'Einstein', 69)");
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
		// LO PRIMERO QUE TENEMOS QUE HACER AHORA ES CARGAR EL DRIVER EN MEMORIA
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
