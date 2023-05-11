package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterIDForTestController extends BasicController {

	@FXML
	private TextField ID;

	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student - Enter Code");
	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void submitPressed(ActionEvent event) {
		// Opens Exam screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("StudentTestQ.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student - Exam");
	}

}
