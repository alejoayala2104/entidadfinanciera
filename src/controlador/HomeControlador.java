package controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
public class HomeControlador {

	@FXML
	private void entrarClientes(ActionEvent event) throws IOException {
		
		Parent interfazClientes = FXMLLoader.load(getClass().getResource("/vista/clientes.fxml"));
		Scene escenaClientes = new Scene(interfazClientes);
		Window nodo = ((Node) event.getSource()).getScene().getWindow();
		Stage ventana = (Stage)(nodo);
		ventana.setScene(escenaClientes);
		ventana.show();
	}
	
}
