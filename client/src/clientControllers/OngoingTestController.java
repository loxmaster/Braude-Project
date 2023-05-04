package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OngoingTestController {

	@FXML
	private TableView<?> table;

	@FXML
	void LogOutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		VBox vbox = null;
		try {
			vbox = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(vbox);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane newScene = null;
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/Lecturer1.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Lecturer");
		currentStage.setScene(scene);
	}

}
