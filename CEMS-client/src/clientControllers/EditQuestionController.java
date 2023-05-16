package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.QuestionModel;
import javafx.scene.Node;

public class EditQuestionController extends BasicController {
	
	private String originalId ;
	@FXML
	private TextField qA, qB, qC, qD;

	@FXML
	private Button BtnInfo;

	@FXML
	private TextArea body;

	@FXML
	private CheckBox A, B, C, D;

	@FXML
	private TextField qNumber;

	@FXML
	private ComboBox<?> subjectCombobox;

	//load question details under 'Edit Question'
	public void loadQuestion(QuestionModel question,String id) {
		// TODO
		body.setText(question.getQuestiontext());
		ArrayList<String> subjectArray = new ArrayList<String>();
		subjectArray.add(question.getSubject());
		ComboBox<?> tempCombobox = new ComboBox<>();
		subjectCombobox = tempCombobox;
		originalId = id;
		qNumber.setText(question.getQuestionnumber());
	}

	@FXML
	void SavePressed(ActionEvent event) {
		// TODO
		String newBody = body.getText();
		String newQNumber = qNumber.getText();
		ClientUI.chat.EditQuestion(newBody, newQNumber, originalId);
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}

	@FXML
	void LogoutPressed(ActionEvent event) {
		LogoutPressed(event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// return to table view from 'Edit Question'
		//((Node) event.getSource()).getScene().getWindow().hide();
		openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		// TODO see if the return works
	}

	@FXML
	void cancelPressed(ActionEvent event) {
		// REVIEW cancel does the same as back maybe remove the 'Back' utton?
		backPressed(event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

}
