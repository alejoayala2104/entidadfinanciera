package controlador;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ConsTransControlador {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private StackPane consTrans;
    @FXML
    private AnchorPane acpConsTransCedula;
    @FXML    
    private TextField txfConsResCedula;

    
    public StackPane getconsTrans() {
    	return this.consTrans;
    }
    
    public TextField gettxfConsResCedula() {
    	return this.txfConsResCedula;
    }    

    @FXML
    public void continuarABusqueda(ActionEvent event) {
    	this.txfConsResCedula.setText("Hola mundo");
    	System.out.println("aaa");
    }
    
    @FXML
    public void initialize() {
        

    }
}
