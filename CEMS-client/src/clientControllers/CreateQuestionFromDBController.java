package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateQuestionFromDBController extends CreateQuestionController {

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
