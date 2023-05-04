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

public class EnterCodeForTestController {

	@FXML
	private TextField code;

	@FXML
	void backPressed(ActionEvent event) {
		// Opens student screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane newScene = null;
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/StudentScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Student");
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
		// Opens Student Give ID screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane vbox = null;
		try {
			vbox = FXMLLoader.load(getClass().getResource("/clientFXMLS/StudentGivesID.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentStage.setTitle("CEMS - Enter ID For Exam");
		Scene scene = new Scene(vbox);
		currentStage.setScene(scene);
	}

}
