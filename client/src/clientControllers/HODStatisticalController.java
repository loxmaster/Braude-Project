package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
		// open Student Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HODStatisticOnLecturer.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
	}

	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		// open Student Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HODStatisticOnStudent.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
	}

	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		// open Subject Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HODStatisticOnSubject.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
	}

	@FXML
	void specialReportPressed(ActionEvent event) {
		
	}

}
