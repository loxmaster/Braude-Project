package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DBQController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// open Create Tests from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Tests");
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Tests");
	}

	@FXML
	void createQuestionPressed(ActionEvent event) {
		// Open Create Question screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateQFromDB.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Questions");
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

}
