package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnterIDForTestController {

	@FXML
	private TextField ID;

	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane newScene = new AnchorPane();
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/EnterCodeForTestPreforming.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Student - Enter Code");
		currentStage.setScene(scene);
	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane vbox = null;
		try {
			vbox = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(vbox);
		currentStage.setScene(scene);
	}

	@FXML
	void submitPressed(ActionEvent event) {
		
	}

}
