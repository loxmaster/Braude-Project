package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterCodeForTestController extends BasicController {

	@FXML
	private TextField code;

	@FXML
	void backPressed(ActionEvent event) {
		// Opens student screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/StudentScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student");
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
		// Opens Student Give ID screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/StudentGivesID.fxml", currentStage);
		currentStage.setTitle("CEMS - Enter ID For Exam");
	}

}
