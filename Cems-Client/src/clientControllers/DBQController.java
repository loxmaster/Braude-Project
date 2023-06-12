package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;
import logic.Test;

public class DBQController extends BasicController {

	private Test testToReturn;
	ObservableList<String> subjectList;

	@FXML
	private TableColumn<QuestionModel, CheckBox> Check;

	@FXML
	private TableColumn<QuestionModel, String> LecturerName;

	// @FXML
	// private TableColumn<QuestionModel, String> SubjectName;

	@FXML
	private TableColumn<QuestionModel, String> Question;

	@FXML
	private TableColumn<QuestionModel, String> QuestionID;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TableView<QuestionModel> table;

	@FXML
	private TextField QuestionAuthorField;

	@FXML
	private ComboBox<String> subjectComboBox;

	// who is this?
	public void loadFilterComboboxes() {
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);

		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);
	}

	public void load(Test test) {
		testToReturn = test;
		// noah- added load lect assigned courses\subjects
		loadFilterComboboxes();

		Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
		LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
		//SubjectName.setCellValueFactory(new PropertyValueFactory<>("Subject"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));

		if (LecturerController.questions.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			ObservableList<QuestionModel> questionList = FXCollections
					.observableArrayList(LecturerController.questions);
			FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
			table.setItems(filteredList);
			// || selectedSubject.isEmpty()
			// listener - this will update the table to the filtered combobox selection
			subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				filteredList.setPredicate(questionModel -> {
					String selectedSubject = subjectComboBox.getValue();
					String searchAuthor = QuestionAuthorField.getText().trim().toLowerCase();
					return (selectedSubject == null
							|| questionModel.getSubject().toUpperCase().contains(selectedSubject)
							|| questionModel.getSubject().toLowerCase().contains(selectedSubject)&& (searchAuthor.isEmpty() || questionModel.getLecturer().toLowerCase().contains(searchAuthor)));
				});
			});
		}
	}

	// public void load(Test test) {

	// testToReturn = test;
	// //noah- added load lect assigned courses\subjects
	// loadsubjectsCombobox();
	// //FilteredList<String> filteredList = new FilteredList<>(subjectList);

	// Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
	// LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
	// Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
	// QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));

	// if (LecturerController.questions.isEmpty()) {
	// JOptionPane.showMessageDialog(null, "Error getting the question!", "Error",
	// JOptionPane.ERROR_MESSAGE);
	// } else {

	// ObservableList<QuestionModel> questionList =
	// FXCollections.observableArrayList(LecturerController.questions);
	// table.setItems(questionList);
	// }
	// }

	/**
	 * Method to reurn to create test screen with the selected questions.
	 * 
	 * @param event
	 */
	@FXML
	void addQuestionPressed(ActionEvent event) {

		// Remembers the questions that needs to be added
		ObservableList<QuestionModel> tempQuestionList = table.getItems();
		ArrayList<QuestionModel> questionsToAdd = new ArrayList<>();

		for (int i = 0; i < tempQuestionList.size(); i++)
			if (Check.getCellObservableValue(tempQuestionList.get(i)).getValue().isSelected())
				if (!testToReturn.getQuesitonsInTest().contains(tempQuestionList.get(i)))
					questionsToAdd.add(tempQuestionList.get(i));

		// Adds the questions to the current test to return.
		testToReturn.addToQuestions(questionsToAdd);

		// open Create Tests back with already updated test
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.setTest(testToReturn);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.setTest(testToReturn);
	}

	@FXML
	void createQuestionPressed(ActionEvent event) {
		// Open Create Question screen
		openScreen("/clientFXMLS/LecturerCreateQFromDB.fxml",
				"CEMS System - Lecturer - Create Tests - Create Questions", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}
}
