package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateQuestionFromDBController extends CreateQuestionController {

	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
	
	@Override
	@FXML
	void cancelPressed(ActionEvent event) {
		// Opens lecturer question database screen from test
		openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Question Data Base", event);
	}

	@Override
	@FXML
	void backPressed(ActionEvent event) {
		// Opens lecturer question database screen from test
		openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Question Data Base", event);
	}

}
