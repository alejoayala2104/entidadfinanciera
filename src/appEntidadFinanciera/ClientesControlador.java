package appEntidadFinanciera;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientesControlador {	 
	
	//Conexion a base de datos
	Conexion conexion = new Conexion();
	Connection cn = conexion.getConexion();	
	
	//Creacion de la sentencia 
	String sentenciaSQL;
	Statement sentencia=null;
	//Creacion del ResultSet
	ResultSet rs = null;
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
	
	public boolean validarCliente(TextField tf) throws SQLException {		

		sentencia = cn.createStatement();
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + tf.getText() +"';";
		rs = sentencia.executeQuery(sentenciaSQL);
		if(rs.next()) {//Si se ejecuta la consulta y hay resultados, significa que el usuario existe.
			return true;
		}
		//rs.close();
		//sentencia.;
		return false;//Si no se encuentran resultados, significa que el usuario no existe.
	}
	
	public void limpiarTextFields() {		
		cedulaTF.clear();
		nombresTF.clear();
		telefonoTF.clear();
		direccionTF.clear();
		emailTF.clear();

		buscarCedulaTF.clear();
		consCedulaTF.clear();
		consNombresTF.clear();
		consTelefonoTF.clear();
		consDireccionTF.clear();
		consEmailTF.clear();
		
		actBuscarCedulaTF.clear();
		actCedulaTF.clear();
		actNombresTF.clear();
		actTelefonoTF.clear();
		actEmailTF.clear();
		actDireccionTF.clear();		
	}
	
	@FXML
	public void validarInputNumerico(KeyEvent event) {
		try {
			TextField textfield = (TextField) event.getSource();
			textfield.setTextFormatter(new TextFormatter<>(change ->
	        (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
			}catch(Exception e) {
				e.getStackTrace();
		}
	}

	@FXML
	public void registrarCliente(ActionEvent event)throws SQLException{		

		if(cedulaTF.getText().isEmpty() || nombresTF.getText().isEmpty() || telefonoTF.getText().isEmpty() ||
				direccionTF.getText().isEmpty() || emailTF.getText().isEmpty()) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al ingresar");
			alerta.setHeaderText(null);
			alerta.setContentText("Todos los datos son obligatorios. Por favor rellenar los campos vacíos.");
			alerta.showAndWait();
			return;
		}
		
		if(validarCliente(cedulaTF)) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Cliente existente");
			alerta.setHeaderText(null);
			alerta.setContentText("El cliente ya está registrado en el sistema.");
			alerta.showAndWait();
			return;
		}
		
		sentenciaSQL = "INSERT INTO cliente(cedula, nombres, telefono, email, direccion)";
		sentenciaSQL = sentenciaSQL + "VALUES ('" + cedulaTF.getText() + "','"+nombresTF.getText()+"','"
				+ telefonoTF.getText() + "','" + direccionTF.getText() + "','" + emailTF.getText() + "')";
		
		
		sentencia = cn.createStatement();
		sentencia.executeUpdate(sentenciaSQL);	
		sentencia.close();
		
		//Alerta de registro exitoso
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Registro creado");
		alerta.setHeaderText("Cliente registrado con éxito");
		alerta.setContentText(null);
		alerta.showAndWait();
		
	}
		
	
	@FXML
	public void consultarCliente(ActionEvent event) throws SQLException{	

		if(buscarCedulaTF.getText().isEmpty()) {	
			limpiarTextFields();
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR");
			alerta.setHeaderText("Campo vacío");
			alerta.setContentText("Por favor ingrese un valor en la casilla de búsqueda.");
			alerta.showAndWait();
			return;
		}
		sentencia = cn.createStatement();
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + buscarCedulaTF.getText() +"';";		
		//Aqui esta en la fila 0 de la tabla(en los atributos)
		ResultSet rs = sentencia.executeQuery(sentenciaSQL);
		
		if(validarCliente(buscarCedulaTF)==false) {
			limpiarTextFields();
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Cliente no encontrado");
			alerta.setHeaderText(null);
			alerta.setContentText("No se encontraron registros con la cédula ingresada.");
			alerta.showAndWait();
			return;
		}
		//Leo los siguientes resultados. Y los muestro en los texfield.
		while(rs.next()) {
			consCedulaTF.setText(rs.getString("cedula"));
			consNombresTF.setText(rs.getString("nombres"));
			consTelefonoTF.setText(rs.getString("telefono"));
			consEmailTF.setText(rs.getString("email"));
			consDireccionTF.setText(rs.getString("direccion"));
			buscarCedulaTF.clear();
		}
		
		//Se cierra el ResulSet. Porque es un cursor. Tambien se cierra el Statement.
		rs.close();
		sentencia.close();
	}
	
	@FXML
	public void actualizarCliente(ActionEvent event) throws SQLException{		
	
		if(actBuscarCedulaTF.getText().isEmpty()) {	
			limpiarTextFields();
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR");
			alerta.setHeaderText("Campo vacío");
			alerta.setContentText("Por favor ingrese un valor en la casilla de búsqueda.");
			alerta.showAndWait();
			return;
		}
		sentencia = cn.createStatement();
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + actBuscarCedulaTF.getText() +"';";		
		ResultSet rs = sentencia.executeQuery(sentenciaSQL);
		//Validación.
		if(validarCliente(actBuscarCedulaTF)==false) {
			limpiarTextFields();
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Cliente no encontrado");
			alerta.setHeaderText(null);
			alerta.setContentText("No se encontraron registros con la cédula ingresada.");
			alerta.showAndWait();
			return;
		}
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
		
		if(actCedulaTF.getText().isEmpty() || actNombresTF.getText().isEmpty() || actTelefonoTF.getText().isEmpty() ||
				actEmailTF.getText().isEmpty() || actDireccionTF.getText().isEmpty()) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error al ingresar");
			alerta.setHeaderText(null);
			alerta.setContentText("Todos los datos son obligatorios. Por favor rellenar los campos vacíos.");
			alerta.showAndWait();
			return;
		}
		
		sentenciaSQL = "UPDATE cliente SET cedula = '"+actCedulaTF.getText()+"', nombres = '"+actNombresTF.getText()+"',telefono='"+actTelefonoTF.getText()+"',email='"+actEmailTF.getText()+"',direccion='"+actDireccionTF.getText()+"' WHERE cedula like '"+actBuscarCedulaTF.getText()+"'";
		System.out.println(sentenciaSQL);
		sentencia = cn.createStatement();
		sentencia.executeUpdate(sentenciaSQL);
		sentencia.close();
		
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Registro actualizado");
		alerta.setHeaderText("Cambios guardados con éxito");
		alerta.setContentText(null);
		alerta.showAndWait();
	}

	
}
