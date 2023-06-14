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
	ObservableList<String> subjectList,courseList;

	@FXML
	private TableColumn<QuestionModel, CheckBox> Check;

	@FXML
	private TableColumn<QuestionModel, String> LecturerName;

	@FXML
	private TableColumn<QuestionModel, String> SubjectName;

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

	@FXML
	private ComboBox<String> courseComboBox;

	// load the table - table has filter, filter updatePredicate handles the filter
	public void load(Test test) {
		testToReturn = test;
		// noah- added load lect assigned courses\subjects
		loadFilterComboboxes();

		Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
		LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));

		if (LecturerController.questions.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			ObservableList<QuestionModel> questionList = FXCollections
					.observableArrayList(LecturerController.questions);
			FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
			table.setItems(filteredList);

			// listener - this will update the table to the filtered COMBOBOX SUBJECT
			subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});

			// listener - this will update the table to the filtered TEXTFIELD AUTHOR
			QuestionAuthorField.textProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});
		}
	}

	// load subject contents into the combobox
	public void loadFilterComboboxes() {
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());
		
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);
		courseComboBox.getItems().removeAll();
		courseComboBox.setItems(courseList);
	}

	private void updatePredicate(FilteredList<QuestionModel> filteredList) {
		String selectedSubject = subjectComboBox.getValue();
		String authorFilterField = QuestionAuthorField.getText().trim().toLowerCase();

		// add new filters here as needed, dont forget to add a new listener
		filteredList.setPredicate(questionModel -> {
			boolean matchesSubject = selectedSubject == null || selectedSubject.isEmpty()
					|| questionModel.getSubject().toUpperCase().contains(selectedSubject)
					|| questionModel.getSubject().toLowerCase().contains(selectedSubject);

			boolean matchesLecturer = authorFilterField.isEmpty()
					|| questionModel.getLecturer().toLowerCase().contains(authorFilterField)
					|| questionModel.getLecturer().toUpperCase().contains(authorFilterField);

			return matchesSubject && matchesLecturer;
		});
	}

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
