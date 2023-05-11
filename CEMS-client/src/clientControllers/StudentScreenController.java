package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class StudentScreenController extends BasicController {

	@FXML
	private TextArea helpLabel;

	@FXML
	void LogOutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (helpLabel.isVisible())
			helpLabel.setVisible(false);
		else
			helpLabel.setVisible(true);
	}

	@FXML
	void OpenCodePrompt(ActionEvent event) {
		// Opening window for code entering
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student - Enter Test Code");
	}

	@FXML
	void showGrades(ActionEvent event) {
		// Opening Show Grades screen
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/ViewGrades.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student Grades");
	}
}
