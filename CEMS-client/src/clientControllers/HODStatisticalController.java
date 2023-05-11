package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HODStatisticalController extends BasicController {

	@FXML
	void backPressed(ActionEvent event) {
		// open HOD screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HOD.fxml", currentStage);
		currentStage.setTitle("CEMS System - Head Of Department");
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void infoOnLecturerPressed(ActionEvent event) {
		// open Student Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HODStatisticOnLecturer.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		// open Student Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HODStatisticOnStudent.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		// open Subject Statistic screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HODStatisticOnSubject.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void specialReportPressed(ActionEvent event) {

	}

}
