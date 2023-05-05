package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LecturerController extends BasicController{
	@FXML
	private TextArea textBox;

	@FXML
	void CheckTheTestsPressed(ActionEvent event) {
		// open Test Check screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml", currentStage);
		currentStage.setTitle("CEMS System - Check Tests");
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (textBox.isVisible())
			textBox.setVisible(false);
		else
			textBox.setVisible(true);
	}

	@FXML
	void LogOutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void CreateQuestionPressed(ActionEvent event) {
		// Open Create Question screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateQ.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Questions");
	}

	@FXML
	void CreateTestsPressed(ActionEvent event) {
		// open Create Tests from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Tests");
	}

	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerOngoingTest.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Menage Tests");
	}

	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerStatistical.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Statistical Information");
	}

}
