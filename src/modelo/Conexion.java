package modelo;

import java.sql.*;
//import org.postgresql.*;

public class Conexion {

	Connection conexion = null;
	String cadenaConexion = "jdbc:postgresql://localhost:5432/entidadfinancieradb";
	
	public Conexion() {			
		try {
			
	        Class.forName("org.postgresql.Driver");	        
			conexion = DriverManager.getConnection(cadenaConexion,"postgres","postgres1");
			
			//Test
//			boolean valid = conexion.isValid(50000);
//	        System.out.println(valid ? "TEST OK" : "TEST FAIL");	        
	        
		}catch(ClassNotFoundException ex) {
			System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
		}catch (java.sql.SQLException sqle) {
	        System.out.println("Error: " + sqle);
	    }		
	}
	
	public Connection getConexion() {
		return conexion;		
	}
	public void desconectar() {
		conexion = null;
	}
	
	
}
