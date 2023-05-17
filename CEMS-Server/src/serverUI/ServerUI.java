package serverUI;

import java.io.IOException; 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serverControllers.ServerController;
 
public class ServerUI extends Application{
	
	public static ServerController sController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = null;
		Stage currentStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		try {
			root = loader.load(getClass().getResource("/serverFXMLS/ServerUI.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/serverFXMLS/background.css").toExternalForm());
		sController = loader.getController();
		sController.load();
		currentStage.setScene(scene);
		currentStage.setTitle("CEMS System - Server");
		currentStage.show();
	}
}