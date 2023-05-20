package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;


public class CreateTestController extends BasicController {

	@FXML
	private ScrollPane table;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Open question DB screen from existing stage
		openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Create Test - Question Data Base", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void savePressed(ActionEvent event) {

	}

}
