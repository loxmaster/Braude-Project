package clientControllers;

import java.io.IOException;

import clientHandlers.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class BasicController {
	

	/**
	 * Method for opening a screen from given fxml path and title
	 * @param fxml fxml path
	 * @param title title of the screen
	 * @param event the triger event
	 */ 
	public BasicController openScreen(String fxml, String title ,ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		AnchorPane root = null;
		Stage currentStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		try {
			root = loader.load(getClass().getResource(fxml).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
		currentStage.setScene(scene);
		currentStage.setTitle(title);
		currentStage.show();
		return loader.getController();
	}

	// logout method is same for everyone, goes back to the main login screen.
	@FXML
	void logoutPressed(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
        openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS System - Login", event);
        ClientHandler.resetClientData();
		//ClientHandler.quit();
	}

	@FXML
	void backToLecturer(ActionEvent event) {
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}
}
