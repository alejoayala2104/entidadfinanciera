package appEntidadFinanciera;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientesControlador {	 
	
	//Conexion a base de datos
	Conexion conexion = new Conexion();
	Connection cn = conexion.getConexion();	
	
	//Creacion de la sentencia 
	String sentenciaSQL;
	Statement sentencia=null;
	//Registrar cliente
	@FXML TextField cedulaTF;
	@FXML TextField nombresTF;
	@FXML TextField telefonoTF;
	@FXML TextField direccionTF;
	@FXML TextField emailTF;

	
	//Consultar cliente
	@FXML TextField buscarCedulaTF;
	@FXML TextField consCedulaTF;
	@FXML TextField consNombresTF;
	@FXML TextField consTelefonoTF;
	@FXML TextField consDireccionTF;
	@FXML TextField consEmailTF;
	
	
	//Actualizar cliente
	@FXML TextField actBuscarCedulaTF;
	@FXML TextField actCedulaTF;
	@FXML TextField actNombresTF;
	@FXML TextField actTelefonoTF;
	@FXML TextField actEmailTF;
	@FXML TextField actDireccionTF;
	
	@FXML
	public void registrarCliente(ActionEvent event)throws SQLException{
		
		sentenciaSQL = "INSERT INTO cliente(cedula, nombres, telefono, email, direccion)";
		sentenciaSQL = sentenciaSQL + "VALUES ('" + cedulaTF.getText() + "','"+nombresTF.getText()+"','"
				+ telefonoTF.getText() + "','" + direccionTF.getText() + "','" + emailTF.getText() + "')";
		
		//System.out.println(sentenciaSQL);
		
		sentencia = cn.createStatement();
		sentencia.executeUpdate(sentenciaSQL);	
		sentencia.close();
		//Comentario add
		
	}
		
	
	@FXML
	public void consultarCliente(ActionEvent event) throws SQLException{	
		
		sentencia = cn.createStatement();
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + buscarCedulaTF.getText() +"';";		
		//Aqui esta en la fila 0 de la tabla(en los atributos)
		ResultSet rs = sentencia.executeQuery(sentenciaSQL);
		//Leo los siguientes resultados. Y los muestro en los texfield
		while(rs.next()) {			
			consCedulaTF.setText(rs.getString("cedula"));
			consNombresTF.setText(rs.getString("nombres"));
			consTelefonoTF.setText(rs.getString("telefono"));
			consEmailTF.setText(rs.getString("email"));
			consDireccionTF.setText(rs.getString("direccion"));			
		}
		
		//Se cierra el ResulSet. Porque es un cursor. Tambien se cierra el Statement.
		rs.close();
		sentencia.close();
	}
	
	@FXML
	public void actualizarCliente(ActionEvent event) throws SQLException{		
	
		sentencia = cn.createStatement();
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + actBuscarCedulaTF.getText() +"';";		
		ResultSet rs = sentencia.executeQuery(sentenciaSQL);
		
		while(rs.next()) {
			actCedulaTF.setText(rs.getString("cedula"));
			actNombresTF.setText(rs.getString("nombres"));
			actTelefonoTF.setText(rs.getString("telefono"));
			actEmailTF.setText(rs.getString("email"));
			actDireccionTF.setText(rs.getString("direccion"));			
		}
		rs.close();
		sentencia.close();
		
	}
	
	@FXML
	public void actualizarClienteGuardar(ActionEvent event) throws SQLException{
		
		sentenciaSQL = "UPDATE cliente SET cedula = '"+actCedulaTF.getText()+"', nombres = '"+actNombresTF.getText()+"',telefono='"+actTelefonoTF.getText()+"',email='"+actEmailTF.getText()+"',direccion='"+actDireccionTF.getText()+"' WHERE cedula like '"+actBuscarCedulaTF.getText()+"'";
		System.out.println(sentenciaSQL);
		sentencia = cn.createStatement();
		sentencia.executeUpdate(sentenciaSQL);
		sentencia.close();
	}

	
	
}
