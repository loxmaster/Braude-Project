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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;
import logic.Test;

/**
 * This class is the controller for the DBQ (Database Questions) screen. It
 * allows the user to view and select questions from the database.
 */
public class DBQController extends BasicController {
	// The test to return after selecting questions
	private Test testToReturn;

	// Observable lists to hold the subjects and courses
	ObservableList<String> subjectList, courseList;

	// FXML annotations for the UI elements
	@FXML
	private TableColumn<QuestionModel, CheckBox> Check; // Column for checkboxes to select questions
	@FXML
	private TableColumn<QuestionModel, String> LecturerName; // Column for lecturer names
	@FXML
	private TableColumn<QuestionModel, String> SubjectName; // Column for subject names
	@FXML
	private TableColumn<QuestionModel, String> Question; // Column for question texts
	@FXML
	private TableColumn<QuestionModel, String> QuestionID; // Column for question IDs
	@FXML
	private Button exitbutton; // Button to exit the screen
	@FXML
	private Button logo; // Logo button
	@FXML
	private TableView<QuestionModel> table; // Table to display the questions
	@FXML
	private TextField QuestionAuthorField; // Field to filter questions by author
	@FXML
	private ComboBox<String> subjectComboBox; // ComboBox to select a subject
	@FXML
	private ComboBox<String> courseComboBox; // ComboBox to select a course
	@FXML
	private Label live_time; // Label to display the current time

	/**
	 * This function is called when the screen is initialized. It starts the clock.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * This function loads the questions into the table and sets up the filters.
	 * 
	 * @param test The test for which to select questions.
	 */
	public void load(Test test) {
		// Store the test to return after selecting questions
		testToReturn = test;

		// Load the lecturer's assigned courses and subjects into the filter comboboxes
		loadFilterComboboxes();

		// Set the cell value factories for each column
		// This tells JavaFX how to populate each cell in the column
		Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
		LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));

		// Check if there are any questions to display
		if (LecturerController.questions.isEmpty()) {
			// If not, display an error message
			JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			// If there are questions, add them to the table
			ObservableList<QuestionModel> questionList = FXCollections
					.observableArrayList(LecturerController.questions);
			FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
			table.setItems(filteredList);

			// Add listeners to the filter fields
			// These will update the table whenever the selected subject, course, or author
			// changes
			subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});
			courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});
			QuestionAuthorField.textProperty().addListener((observable, oldValue, newValue) -> {
				updatePredicate(filteredList);
			});
		}
	}

	/**
	 * This function loads the subjects and courses into the respective comboboxes.
	 */
	public void loadFilterComboboxes() {
		// Get the list of subjects and courses from the LecturerController
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

		// Clear the comboboxes and set their items to the lists of subjects and courses
		subjectComboBox.getItems().clear();
		subjectComboBox.setItems(subjectList);

		courseComboBox.getItems().clear();
		courseComboBox.setItems(courseList);
	}

	/**
	 * This function updates the predicate of the filtered list based on the
	 * selected subject, course, and author.
	 * 
	 * @param filteredList The filtered list to update.
	 */
	private void updatePredicate(FilteredList<QuestionModel> filteredList) {
		// Get the selected subject, course, and author
		String selectedSubject = subjectComboBox.getValue();
		String selectedCourse = courseComboBox.getValue();
		String authorFilterField = QuestionAuthorField.getText().trim();

		// Set the predicate of the filtered list
		// The predicate checks whether a question matches the selected subject, course,
		// and author
		filteredList.setPredicate(questionModel -> {
			// A question matches the selected subject if no subject is selected or if the
			// question's subject contains the selected subject
			boolean matchesSubject = selectedSubject == null || selectedSubject.isEmpty()
					|| questionModel.getSubject().contains(selectedSubject);

			// A question matches the selected course if no course is selected or if the
			// question's course contains the selected course
			boolean matchesCourse = selectedCourse == null || selectedCourse.isEmpty()
					|| questionModel.getCoursename().contains(selectedCourse);

			// A question matches the selected author if no author is entered or if the
			// question's lecturer contains the entered author
			boolean matchesLecturer = authorFilterField.isEmpty()
					|| questionModel.getLecturer().contains(authorFilterField);

			// A question matches the filters if it matches the selected subject, course,
			// and author
			return matchesSubject && matchesCourse && matchesLecturer;
		});
	}

	/**
	 * This method is called when the 'Add Question' button is pressed. It adds the
	 * selected questions to the test.
	 * 
	 * @param event The event that triggered this method (i.e., pressing the 'Add
	 *              Question' button).
	 */
	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Get the list of questions currently displayed in the table
		ObservableList<QuestionModel> tempQuestionList = table.getItems();

		// Create a list to store the questions that need to be added to the test
		ArrayList<QuestionModel> questionsToAdd = new ArrayList<>();

		// Iterate over the list of displayed questions
		for (int i = 0; i < tempQuestionList.size(); i++) {
			// If the checkbox for the current question is selected and the question is not
			// already in the test
			if (Check.getCellObservableValue(tempQuestionList.get(i)).getValue().isSelected()
					&& !testToReturn.getQuesitonsInTest().contains(tempQuestionList.get(i))) {
				// Add the question to the list of questions to add
				questionsToAdd.add(tempQuestionList.get(i));
			}
		}

		// Add the questions to the test
		testToReturn.addToQuestions(questionsToAdd);

		// Open the 'Create Test' screen with the updated test
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.setTest(testToReturn);
		ctc.initialize();
	}

	/**
	 * This method is called when the 'Back' button is pressed. It opens the 'Create
	 * Test' screen.
	 * 
	 * @param event The event that triggered this method (i.e., pressing the 'Back'
	 *              button).
	 */
	@FXML
	void backPressed(ActionEvent event) {
		// Open the 'Create Test' screen with the current test
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.setTest(testToReturn);
		ctc.initialize();
	}

	/**
	 * This method is called when the 'Create Question' button is pressed. It opens
	 * the 'Create Question' screen.
	 * 
	 * @param event The event that triggered this method (i.e., pressing the 'Create
	 *              Question' button).
	 */
	@FXML
	void createQuestionPressed(ActionEvent event) {
		// Open the 'Create Question' screen
		CreateQuestionFromDBController ctcec = (CreateQuestionFromDBController) openScreen(
				"/clientFXMLS/LecturerCreateQFromDB.fxml", "CEMS System - Lecturer - Create Tests - Create Questions",
				event);

		// Set the current test in the 'Create Question' screen and load the filter
		// comboboxes
		ctcec.setTest(testToReturn);
		ctcec.loadFilterComboboxes();
	}

}
