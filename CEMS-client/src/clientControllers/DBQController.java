package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class DBQController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// open Create Tests 
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests 
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
	}

	@FXML
	void createQuestionPressed(ActionEvent event) {
		// Open Create Question screen
		openScreen("/clientFXMLS/LecturerCreateQFromDB.fxml", "CEMS System - Lecturer - Create Tests - Create Questions", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void LogoutPressed(ActionEvent event) {
		logoutPressed(event);
	}

}
