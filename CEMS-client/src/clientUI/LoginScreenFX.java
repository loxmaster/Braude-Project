package clientUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenFX extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		AnchorPane root;
		FXMLLoader loader = new FXMLLoader();
		stage.setTitle("CEMS System - Login");
		loader.setLocation((getClass().getResource("/clientFXMLS/LoginScreen.fxml")));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		//-fx-background-color: #deb887;
		scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}