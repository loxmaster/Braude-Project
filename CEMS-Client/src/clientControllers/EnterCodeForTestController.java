package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EnterCodeForTestController extends BasicController {

	@FXML
	private TextField code;

	@FXML
	void BackPressed(ActionEvent event) {
		// Opens student screen
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
	}

	@FXML
	void SubmitPressed(ActionEvent event) {
		// Opens Student Give ID screen
		openScreen("/clientFXMLS/StudentGivesID.fxml", "CEMS - Enter ID For Exam", event);
	}

}
