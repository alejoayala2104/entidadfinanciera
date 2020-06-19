package controlador;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.Conexion;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	
	//Imagen empresa icono
	@FXML
	Image empresa_logo = new Image (getClass().getResourceAsStream("/vista/icon/empresa_logo.png"));
	ImageView empresa_logo_view = new ImageView (empresa_logo);
	
	
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
	
	//Este método ejecuta una sentencia SQL. Devuelve el ResultSet con el resultado de dicha consulta.
	public ResultSet ejecutarSentencia(String sentenciaSQL) {
		ResultSet resultSet=null;
		try {
			sentencia = cn.createStatement();
			resultSet = sentencia.executeQuery(sentenciaSQL);
		} catch (SQLException e) {
			System.out.println("No se pudo ejecutar la sentencia en la base de datos.");
		}
		return resultSet;
	}
	
	//Verifica si un cliente existe o no.
	public boolean validarCliente(TextField tf) throws SQLException {

		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + tf.getText() +"';";
		rs = ejecutarSentencia(sentenciaSQL);
		
		//Si se ejecuta la consulta y hay resultados, significa que el usuario existe.
		if(rs.next()) {
			return true;
		}
		//Si no se encuentran resultados, significa que el usuario no existe.
		return false;
	}
	
	public void mostrarAlerta(AlertType tipoAlerta,String tituloVentana,String tituloMensaje,String mensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(tituloVentana);
		alerta.setHeaderText(tituloMensaje);
		alerta.setContentText(mensaje);
		alerta.showAndWait();		
	}
	
	//Método que fuerza a los campos de cédula y teléfono a solo recibir entradas numéricas.
	//También hace que no se pueda digitar del número 0 de primero.		
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
			mostrarAlerta(AlertType.ERROR, "Error al ingresar", null, "Todos los datos son obligatorios. Por favor rellenar los campos vacíos.");			
			return;
		}
		
		//En caso de que el cliente exista, muestra la alerta y sale del metodo registrarCliente().
		if(validarCliente(cedulaTF)) {			
			mostrarAlerta(AlertType.ERROR, "Cliente existente", null, "El cliente ya está registrado en el sistema.");			
			return;
		}
		
		//Creación y ejecución de la sentencia.
		sentenciaSQL = "INSERT INTO cliente(cedula, nombres, telefono, email, direccion)";
		sentenciaSQL = sentenciaSQL + "VALUES ('" + cedulaTF.getText() + "','"+nombresTF.getText()+"','"
				+ telefonoTF.getText() + "','" + direccionTF.getText() + "','" + emailTF.getText() + "')";		
		sentencia = cn.createStatement();
		sentencia.execute(sentenciaSQL); //Es diferente a executeQuery porque en este metodo no se retorna nada.
		sentencia.close();
		
		//Alerta de registro exitoso.
		mostrarAlerta(AlertType.INFORMATION, "Registro creado", "Cliente registrado con éxito", null);
		limpiarTextFields();
		//Se cierra el ResultSet y el Statement
		rs.close();
		sentencia.close();
	}
		
	
	@FXML
	public void consultarCliente(ActionEvent event) throws SQLException{	

		//Valida que el campo de cédula no esté vacío.
		if(buscarCedulaTF.getText().isEmpty()) {	
			limpiarTextFields();
			mostrarAlerta(AlertType.ERROR, "Error", "Campo vacío", "Por favor ingrese un valor en la casilla de búsqueda.");			
			return;
		}
	
		//Ejecuta la consulta 
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + buscarCedulaTF.getText() +"';";
		rs = ejecutarSentencia(sentenciaSQL);	
	
		//Si hay un resultado, mostrar sus datos en los textfield.
		if(rs.next()) {
			consCedulaTF.setText(rs.getString("cedula"));
			consNombresTF.setText(rs.getString("nombres"));
			consTelefonoTF.setText(rs.getString("telefono"));
			consEmailTF.setText(rs.getString("email"));
			consDireccionTF.setText(rs.getString("direccion"));
			buscarCedulaTF.clear();
		}
		//Si no, dar aviso de que no se encontró el cliente, limpiar los textfield y salir del controlador consultarCliente().
		else {
			limpiarTextFields();
			mostrarAlerta(AlertType.ERROR, "Cliente no encontrado", null, "No se encontraron registros con la cédula ingresada.");
			return;
		}
		
		//Se cierra el ResulSet. Porque es un cursor. Tambien se cierra el Statement.
		rs.close();
		sentencia.close();
	}
	
	@FXML
	public void actualizarCliente(ActionEvent event) throws SQLException{
	
		//Verificar que el campo de cedula no esté vacío.
		if(actBuscarCedulaTF.getText().isEmpty()) {	
			limpiarTextFields();
			mostrarAlerta(AlertType.ERROR, "ERROR", "Campo vacío", "Por favor ingrese un valor en la casilla de búsqueda.");		
			return;
		}

		//Ejecuta la consulta.
		sentenciaSQL = "SELECT * from cliente WHERE cedula like '" + actBuscarCedulaTF.getText() +"';";	
		rs = ejecutarSentencia(sentenciaSQL);
		
		//Consultar cliente.
		if(rs.next()) {
			actCedulaTF.setText(rs.getString("cedula"));
			actNombresTF.setText(rs.getString("nombres"));
			actTelefonoTF.setText(rs.getString("telefono"));
			actEmailTF.setText(rs.getString("email"));
			actDireccionTF.setText(rs.getString("direccion"));			
		}else {
			limpiarTextFields();
			mostrarAlerta(AlertType.ERROR, "Cliente no encontrado", null, "No se encontraron registros con la cédula ingresada.");
			return;
		}
		
		rs.close();
		sentencia.close();
	}
	
	@FXML
	public void actualizarClienteGuardar(ActionEvent event) throws SQLException{
		
		//Valida que no haya campos vacíos.
		if(actCedulaTF.getText().isEmpty() || actNombresTF.getText().isEmpty() || actTelefonoTF.getText().isEmpty() ||
				actEmailTF.getText().isEmpty() || actDireccionTF.getText().isEmpty()) {
			mostrarAlerta(AlertType.ERROR, "Error al ingresar", null, "Todos los datos son obligatorios. Por favor rellenar los campos vacíos.");			
			return;
		}
		String cedulaBuscada = actBuscarCedulaTF.getText();
		//Verifica que la cédula que está ingresando no es de algún otro cliente ya existente.
		if(actCedulaTF.getText().equals(cedulaBuscada)==false && validarCliente(actCedulaTF)) {
			limpiarTextFields();
			mostrarAlerta(AlertType.ERROR, "Cliente existente", "No se puede actualizar la cédula", "El cliente con dicha cédula ya existe.");
			return;
		}
		
		//Se ejecuta el update.
		sentenciaSQL = "UPDATE cliente SET cedula = '"+actCedulaTF.getText()+"', nombres = '"+actNombresTF.getText()+"',telefono='"+actTelefonoTF.getText()+"',email='"+actEmailTF.getText()+"',direccion='"+actDireccionTF.getText()+"' WHERE cedula like '"+actBuscarCedulaTF.getText()+"'";
		sentencia = cn.createStatement();
		sentencia.executeUpdate(sentenciaSQL);
		
		//Mostrar mensaje de éxito.
		mostrarAlerta(AlertType.INFORMATION, "Registro actualizado", "Cambios guardados con éxito", null);
		limpiarTextFields();
		//Se cierra la sentencia.
		sentencia.close();
	}
	
	@FXML    
    public void entrarHome(ActionEvent event) throws IOException {
    	Parent home = FXMLLoader.load(getClass().getResource("/vista/home.fxml"));
		Scene homeScene = new Scene(home);
		Window nodo = ((Node) event.getSource()).getScene().getWindow();
		Stage ventana = (Stage)(nodo);
		ventana.setScene(homeScene);
		ventana.show();
    }
}
