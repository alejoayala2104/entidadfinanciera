package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import modelo.Garantia;

public class NuevaGarantiaControlador {

	private String clienteNuevaGarantia;	
	private Garantia nuevaGarantia = new Garantia();
	ControladorGeneral controlGeneral = new ControladorGeneral();
	
    public void setClienteNuevaGarantia(String clienteNuevaGarantia) {
		this.clienteNuevaGarantia = clienteNuevaGarantia;
	}

	@FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> cbxTipoNuevaGarantia;
    @FXML
    private TextField txfValorNuevaGarantia;
    @FXML
    private TextField txfUbiNuevaGarantia;
    

    @FXML
    public void cancelarNuevaGarantia(ActionEvent event) {
    	//Obtiene el stage donde está el botón Cancelar.
    	Window nodo = ((Node) event.getSource()).getScene().getWindow();
		Stage ventana = (Stage)(nodo);		
    	//Cierra el stage sin guardar ningún cambio.
		ventana.close();
    }

    @FXML
    public void guardarNuevaGarantia(ActionEvent event) throws IOException, SQLException {    	

    	if(cbxTipoNuevaGarantia.getValue()==null || 
    			txfValorNuevaGarantia.getText().isEmpty() || txfUbiNuevaGarantia.getText().isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Campos vacíos", "Todos los campos son obligatorios", "Por favor rellene los campos.");
    		return;
    	}
    	
    	nuevaGarantia.setClienteGarantia(clienteNuevaGarantia);
    	nuevaGarantia.setTipoGarantia(cbxTipoNuevaGarantia.getValue());
    	nuevaGarantia.setValorGarantia(txfValorNuevaGarantia.getText());
    	nuevaGarantia.setUbiGarantia(txfUbiNuevaGarantia.getText());
    	System.out.println(nuevaGarantia.toString());
    	
    	String sentenciaSQL = "INSERT INTO garantias(clientegarantia,tipogarantia,valorgarantia,ubigarantia) "
    			+ "values ('"+nuevaGarantia.getClienteGarantia()+"','"+nuevaGarantia.getTipoGarantia()+"',"
    					+ "'"+nuevaGarantia.getValorGarantia()+"','"+nuevaGarantia.getUbiGarantia()+"');";    	
    	controlGeneral.ejecutarSentenciaInsert(sentenciaSQL);
    	
    	controlGeneral.mostrarAlertaSinContent(AlertType.INFORMATION, "Garantía guardada", "Garantía guardada con éxito");    	    	
    	
    	//Cerrar la ventana.
    	cancelarNuevaGarantia(event);    	
    	
    }  
    
    @FXML
  	public void validarInputEnteroSinLimite(KeyEvent event) {
  		try {
  			TextField textfield = (TextField) event.getSource();
  			textfield.setTextFormatter(new TextFormatter<>(change ->
  	        (change.getControlNewText().matches("^[0-9]{0,20}$")) ? change : null));
  			}catch(Exception e) {
  				e.getStackTrace();
  		}
  	}

    @FXML
    public void initialize() {
    	cbxTipoNuevaGarantia.setPromptText("Seleccione un tipo");
        cbxTipoNuevaGarantia.getItems().addAll("Automóvil","Inmueble","Otro");
    }
}
