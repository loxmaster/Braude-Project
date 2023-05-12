package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class CreateTestController extends BasicController {

	@FXML
	private ScrollPane table;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Open question DB screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerDBQ.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Test - Question Data Base");
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer");
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void savePressed(ActionEvent event) {

	}

}
