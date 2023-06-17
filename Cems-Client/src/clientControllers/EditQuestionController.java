package clientControllers;

import java.util.ArrayList;
import java.util.HashSet;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.QuestionModel;

public class EditQuestionController extends BasicController {

	private ObservableList<String> subjectList;
	private ObservableList<String> courseList;
	private String originalId;

	@FXML
	private ToggleGroup editGroupEditQuestion;

	@FXML
	private TextField qA, qB, qC, qD;

	@FXML
	private Button BtnInfo;

	@FXML
	private TextField body;

	@FXML
	private RadioButton A, B, C, D;

	@FXML
	private TextField qNumber;

	@FXML
	private ComboBox<String> subjectCombobox;

	@FXML
	private ComboBox<String> courseComboBox;

	public void loadEditQuestionScreen() {
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);

		courseComboBox.getItems().removeAll();
		courseComboBox.setItems(courseList);

	}

	// load question details under 'Edit Question'
	public void loadQuestion(QuestionModel question, String id) {
		// loadEditQuestionScreen();

		body.setText(question.getQuestiontext());
		HashSet<String> subjectUpdated = new HashSet<>();
		HashSet<String> courseUpdated = new HashSet<>();

		subjectUpdated.addAll(LecturerController.subjectsList);
		subjectUpdated.add(question.getSubject());

		courseUpdated.addAll(LecturerController.coursesList);
		courseUpdated.add(question.getCoursename());

		subjectList = FXCollections.observableArrayList(subjectUpdated);
		courseList = FXCollections.observableArrayList(courseUpdated);

		courseComboBox.setItems(courseList);
		courseComboBox.setValue(question.getCoursename());
	

		subjectCombobox.setItems(subjectList);
		subjectCombobox.setValue(question.getSubject());

		qA.setText(question.getOptionA());
		qB.setText(question.getOptionB());
		qC.setText(question.getOptionC());
		qD.setText(question.getOptionD());

		switch (question.getAnswer()) {
			case "a":
				A.setSelected(true);
				break;
			case "b":
				B.setSelected(true);
				break;
			case "c":
				C.setSelected(true);
				break;
			case "d":
				D.setSelected(true);
				break;

			// ComboBox<String> tempCombobox = new ComboBox<>();
			// subjectComboBox = tempCombobox;
		}

		qNumber.setText(question.getQuestionnumber());

		originalId = id;
	}

	@FXML
	void SavePressed(ActionEvent event) {

		String newBody = body.getText();
		String newQNumber = qNumber.getText();
		ClientUI.chat.EditQuestion(newBody, newQNumber, originalId);
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
		// resets the question array after the update
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

	@FXML
	void backPressed(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen(
				"/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		lqtc.loadTable();
	}

	@FXML
	void cancelPressed(ActionEvent event) {
		// TODO cancel does the same as back maybe remove the 'Back' utton?
		backPressed(event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

}
