package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HODStatisticalController {

	@FXML
	void backPressed(ActionEvent event) {
		// open HOD screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HOD.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Head Of Department");
		currentStage.setScene(scene);
	}

	@FXML
	void helpPressed(ActionEvent event) {
		
	}

	@FXML
	void infoOnLecturerPressed(ActionEvent event) {
		
	}

	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		
	}

	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		
	}

	@FXML
	void logoutPressed(ActionEvent event) {
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
	void specialReportPressed(ActionEvent event) {
		
	}

}
