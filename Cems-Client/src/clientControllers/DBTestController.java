package clientControllers;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;
import logic.Test;

public class DBTestController extends BasicController {

	private Test testToReturn;

	ObservableList<String> subjectList, courseList;

	@FXML
	private TableColumn<Test, String> TestID;

	@FXML
	private TableColumn<Test, String> Department;

	@FXML
	private TableColumn<Test, String> Author;
	
	@FXML
	private TableColumn<Test, String> CourseName;
	
	@FXML
	private TableColumn<Test, RadioButton> Choose;
	
	@FXML
	private Button exitbutton;
	
	@FXML
	private Button logo;
	
	@FXML
	private TableView<QuestionModel> table;
	
	@FXML
	private TextField TableAuthorField;
	
	@FXML
	private ComboBox<String> subjectComboBox;
	
	@FXML
	private ComboBox<String> courseComboBox;
	
	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
	
	// load the table - table has filter, filter updatePredicate handles the filter
	public void load(Test test) {
		testToReturn = test;
		// noah- added load lect assigned courses\subjects
		loadFilterComboboxes();

		Choose.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
		Author.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
		TestID.setCellValueFactory(new PropertyValueFactory<>("TestId"));

		if (LecturerController.questions.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
			FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
			table.setItems(filteredList);

			// listener - this will update the table to the filtered COMBOBOX SUBJECT
			subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});

			// listener - this will update the table to the filtered COMBOBOX SUBJECT
			courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});

			// listener - this will update the table to the filtered TEXTFIELD AUTHOR
			TableAuthorField.textProperty().addListener((observable, oldValue, newValue) -> {
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
		String selectedCourse = courseComboBox.getValue();
		String authorFilterField = TableAuthorField.getText().trim();

		// add new filters here as needed, dont forget to add a new listener
		filteredList.setPredicate(questionModel -> {
			boolean matchesSubject = selectedSubject == null || selectedSubject.isEmpty()
					|| questionModel.getSubject().contains(selectedSubject)
					|| questionModel.getSubject().contains(selectedSubject);

			boolean matchesCourse = selectedCourse == null || selectedCourse.isEmpty()
					|| questionModel.getCoursename().contains(selectedCourse)
					|| questionModel.getCoursename().contains(selectedCourse);

			boolean matchesLecturer = authorFilterField.isEmpty() 
					|| questionModel.getLecturer().contains(authorFilterField)
					|| questionModel.getLecturer().contains(authorFilterField);

			return matchesSubject && matchesLecturer && matchesCourse;
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
		//ObservableList<QuestionModel> tempQuestionList = table.getItems();
		//ArrayList<QuestionModel> questionsToAdd = new ArrayList<>();

		// for (int i = 0; i < tempQuestionList.size(); i++)
		// 	if (Choose.getCellObservableValue(tempQuestionList.get(i)).getValue().isSelected())
		// 		if (!testToReturn.getQuesitonsInTest().contains(tempQuestionList.get(i)))
		// 			questionsToAdd.add(tempQuestionList.get(i));

		// Adds the questions to the current test to return.
		//testToReturn.addToQuestions(questionsToAdd);

		// open Create Tests back with already updated test
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.initialize();
		ctc.setTest(testToReturn);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.initialize();
		ctc.setTest(testToReturn);
		ctc.loadFilterComboboxes();
	}


	@FXML
	void createQuestionPressed(ActionEvent event) {
		if (LecturerController.subjectsList.isEmpty())
            JOptionPane.showMessageDialog(null, "Lecturer has no subjects!", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            // Open the CreateQuestionController and pass the subjects list
            CreateQuestionController ctc = (CreateQuestionController) openScreen("/clientFXMLS/LecturerCreateQFromDB", "CEMS System - Lecturer - Create Question", event);
            ctc.loadFilterComboboxes();
        }

	}
}
