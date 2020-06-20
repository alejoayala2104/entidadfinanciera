package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.Conexion;

public class ControladorGeneral {

	//Conexion a base de datos
	Conexion conexion;
	Connection cn;
	
	//Creacion de la sentencia 
	String sentenciaSQL;
	Statement sentencia=null;
	
	//Creacion del ResultSet
	ResultSet rs = null;
	
	public ControladorGeneral() {
		 conexion= new Conexion();
		 cn = conexion.getConexion();
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
	
	public void ejecutarSentenciaInsert(String sentenciaSQL) {		
		try {
			sentencia = cn.createStatement();
			sentencia.execute(sentenciaSQL);
		} catch (SQLException e) {
			System.out.println("No se pudo ejecutar la sentencia en la base de datos.");
		}
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
	
	
	public void mostrarAlerta(AlertType tipoAlerta,String tituloVentana,String tituloMensaje,String mensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(tituloVentana);
		alerta.setHeaderText(tituloMensaje);
		alerta.setContentText(mensaje);
		alerta.showAndWait();		
	}
	
	public void mostrarAlertaSinContent(AlertType tipoAlerta,String tituloVentana,String tituloMensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(tituloVentana);
		alerta.setHeaderText(tituloMensaje);		
		alerta.showAndWait();		
	}
	
}
