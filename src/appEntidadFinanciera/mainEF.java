package appEntidadFinanciera;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainEF extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {	
			Parent root = null;
			root = FXMLLoader.load(getClass().getResource("/vista/home.fxml"));	
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);	
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {	
		launch(args);
	}

}
