package clientControllers;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class CreateTestController extends BasicController {

	
	@FXML
	private CheckBox A, B, C, D;

	@FXML
	private TextField optionA, optionqB, optionC, optionD;

	@FXML
	private TextArea qBpdy;

	@FXML
	private VBox questionTracker;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private Button savebutton;
	
	//@FXML
	//private ComboBox<String> subjectComboBox;

	//@FXML
	//private ScrollPane table;
	
	@FXML
    private ChoiceBox<?> choiceBox;

	

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Open question DB screen from existing stage
		DBQController dqc = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml","CEMS System - Lecturer - Create Test - Question Data Base",event);
		dqc.loadTable();

		//ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
		//Waits 5 seconds for data to be found
		/*int cap = 20;
		while ((LecturerController.getQuestions().isEmpty()) && (cap > 0)) {
			try {
				Thread.sleep(250);
				cap--;
			} catch (InterruptedException e) { }
		}*/
		System.out.println("Answer: " + LecturerController.getQuestions());
	}

	/*@FXML
	void helpPressed(ActionEvent event) {

	}*/

	@FXML
	void savePressed(ActionEvent event) {

	}

}
