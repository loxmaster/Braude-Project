package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CreateQuestionFromDBController extends CreateQuestionController{

	@Override
	@FXML
	void cancelPressed(ActionEvent event) {
		// Opens lecturer question database screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerDBQ.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Question Data Base");
	}
	
	@Override
	@FXML
	void backPressed(ActionEvent event) {
		// Opens lecturer question database screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerDBQ.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Question Data Base");
	}

}
