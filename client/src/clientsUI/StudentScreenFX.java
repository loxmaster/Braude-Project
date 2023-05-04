package clientsUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentScreenFX extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader();
		stage.setTitle("CEMS System - Student");
		loader.setLocation(getClass().getResource("/clientFXMLS/StudentScreen.fxml"));
		AnchorPane newScene = null;
		try {
			newScene = loader.load();
		}catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(newScene);
		stage.setScene(scene);
		stage.show();
	}
}
