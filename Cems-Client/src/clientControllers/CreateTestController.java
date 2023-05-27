package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import logic.QuestionModel;


public class CreateTestController extends BasicController {

	@FXML
	private ScrollPane table;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Open question DB screen from existing stage
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());

		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Create Test - Question Data Base", event);
		System.out.println(LecturerController.getQuestions());
		//dbq.load();
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void savePressed(ActionEvent event) {

	}

}
