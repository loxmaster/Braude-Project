package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class EnterIDForTestController extends BasicController {

	@FXML
	private TextField ID;

	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS System - Student - Enter Code", event);
	}

	@FXML
	void LogoutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		logoutPressed(event);
	}

	@FXML
	void submitPressed(ActionEvent event) {
		// Opens Exam screen from existing stage
		openScreen("/clientFXMLS/StudentTestQ.fxml", "CEMS System - Student - Exam", event);

	}

}
