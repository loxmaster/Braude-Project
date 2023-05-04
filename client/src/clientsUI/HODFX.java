package clientsUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HODFX extends Application {


	public static void main(String[] args) {
		launch(args);
	}
//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
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		stage.setTitle("CEMS System - Head Of Department");
		loader.setLocation(getClass().getResource("/clientFXMLS/HOD.fxml"));
		AnchorPane newScene = null;
		try {
			newScene = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(newScene);
		stage.setScene(scene);
		stage.show();
	}
}
