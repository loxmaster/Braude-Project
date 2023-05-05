package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CreateQuestionController extends BasicController{
	
	@FXML
	void addAnotherPressed(ActionEvent event) {

	}

	@FXML
	void cancelPressed(ActionEvent event) {
		// Opens lecturer screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer");
	}

	@FXML
	void confirmPressed(ActionEvent event) {

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
	void backPressed(ActionEvent event) {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer");
	}
}
