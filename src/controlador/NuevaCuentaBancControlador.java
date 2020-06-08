package controlador;

import modelo.CuentaBancaria;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class NuevaCuentaBancControlador {
	
	private String clienteCuenta;
	CuentaBancaria nuevaCuentaBancaria = new CuentaBancaria();
	ControladorGeneral controlGeneral = new ControladorGeneral();
	
	public void setClienteNuevaCuentabanc(String clienteCuenta) {
		this.clienteCuenta = clienteCuenta;
	}

   @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> cbxTipoCuentaBanc;
    @FXML
    private TextField txfNumCuentaBanc;
    @FXML
    private TextField txfBancoCuentaBanc;

    @FXML
    public void cancelarNuevaCuenta(ActionEvent event) {
    	//Obtiene el stage donde está el botón Cancelar.
    	Window nodo = ((Node) event.getSource()).getScene().getWindow();
		Stage ventana = (Stage)(nodo);		
    	//Cierra el stage sin guardar ningún cambio.
		ventana.close();
    }

    @FXML
    public void guardarNuevaCuenta(ActionEvent event) {

    	if(txfBancoCuentaBanc.getText().isEmpty() || txfNumCuentaBanc.getText().isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Campos vacíos", "Todos los campos son obligatorios", "Por favor rellene todos los campos.");
    		return;
    	}
    	nuevaCuentaBancaria.setNumCuentaBanc(txfNumCuentaBanc.getText());
    	nuevaCuentaBancaria.setClienteCuenta(clienteCuenta);
    	nuevaCuentaBancaria.setBancoCuentaBanc(txfNumCuentaBanc.getText());
    	
    	if(cbxTipoCuentaBanc.getValue().equals("Corriente"))
    		nuevaCuentaBancaria.setTipoCuentaBanc('C');
    	else
    		nuevaCuentaBancaria.setTipoCuentaBanc('A');
    	System.out.println(nuevaCuentaBancaria.toString());
    	
    	String insertarCuenta = "INSERT INTO cuentasbancarias VALUES ('"+nuevaCuentaBancaria.getNumCuentaBanc()+"','"+
    	nuevaCuentaBancaria.getClienteCuenta()+"','"+nuevaCuentaBancaria.getBancoCuentaBanc()+"','"+nuevaCuentaBancaria.getTipoCuentaBanc()+"');";
    	controlGeneral.ejecutarSentenciaInsert(insertarCuenta);
    	
    	controlGeneral.mostrarAlertaSinContent(AlertType.INFORMATION, "Garantía guardada", "Garantía guardada con éxito");    	    	
    	
    	//Cerrar la ventana.
    	cancelarNuevaCuenta(event);  
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
    	cbxTipoCuentaBanc.getItems().addAll("Corriente","Ahorros");
    	cbxTipoCuentaBanc.getSelectionModel().selectFirst();
    }
	
	
}
