package clientControllers;

import java.util.ArrayList;
import clientHandlers.ClientUI;
import javafx.collections.ObservableList;
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
import logic.Test;

/**
 * This class is a controller for managing database tests.
 * It extends the BasicController class.
 */
public class DBTestController extends BasicController {

	/**
	 * The test to be returned.
	 */
	private Test testToReturn;

	/**
	 * The list of tests.
	 */
	private static ArrayList<Test> TestList;

	/**
	 * The column for comments in the table.
	 */
	@FXML
	private TableColumn<Test, String> comment;

	/**
	 * The column for the course in the table.
	 */
	@FXML
	private TableColumn<Test, String> course;

	/**
	 * The column for the date in the table.
	 */
	@FXML
	private TableColumn<Test, String> date;

	/**
	 * The column for the department in the table.
	 */
	@FXML
	private TableColumn<Test, String> department;

	/**
	 * The column for the duration in the table.
	 */
	@FXML
	private TableColumn<Test, String> duration;

	/**
	 * The list of subjects.
	 */
	ObservableList<String> subjectList, courseList;

	/**
	 * The button for editing a test.
	 */
	@FXML
	private Button EditTestPressed;

	/**
	 * The column for the test ID in the table.
	 */
	@FXML
	private TableColumn<Test, String> testID;

	/**
	 * The column for the time in the table.
	 */
	@FXML
	private TableColumn<Test, String> time;

	/**
	 * The column for the author in the table.
	 */
	@FXML
	private TableColumn<Test, String> Author;

	/**
	 * The column for choosing a test in the table.
	 */
	@FXML
	private TableColumn<Test, RadioButton> choose;

	/**
	 * The button for exiting.
	 */
	@FXML
	private Button exitbutton;

	/**
	 * The button for the logo.
	 */
	@FXML
	private Button logo;

	/**
	 * The table for displaying tests.
	 */
	@FXML
	private TableView<Test> table;

	/**
	 * The text field for the author in the table.
	 */
	@FXML
	private TextField TableAuthorField;

	/**
	 * The combo box for selecting subjects.
	 */
	@FXML
	private ComboBox<String> subjectComboBox;

	/**
	 * The combo box for selecting courses.
	 */
	@FXML
	private ComboBox<String> courseComboBox;

	/**
	 * The label for displaying the live time.
	 */
	@FXML
	private Label live_time;

	/**
	 * This method is called to initialize a controller after its root element has
	 * been completely processed.
	 * It starts the clock.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	// load the table - table has filter, filter updatePredicate handles the filter
	// public void load(Test test) {
	// testToReturn = test;
	// // noah- added load lect assigned courses\subjects
	// loadFilterComboboxes();

	// Choose.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
	// Author.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
	// TestID.setCellValueFactory(new PropertyValueFactory<>("TestId"));

	// if (LecturerController.questions.isEmpty()) {
	// JOptionPane.showMessageDialog(null, "Error getting the question!", "Error",
	// JOptionPane.ERROR_MESSAGE);
	// } else {
	// ObservableList<QuestionModel> questionList = FXCollections
	// .observableArrayList(LecturerController.questions);
	// FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
	// table.setItems(filteredList);

	// // listener - this will update the table to the filtered COMBOBOX SUBJECT
	// subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable,
	// oldValue, newValue) -> {
	// updatePredicate(filteredList);
	// });

	// // listener - this will update the table to the filtered COMBOBOX SUBJECT
	// courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable,
	// oldValue, newValue) -> {
	// updatePredicate(filteredList);
	// });

	// // listener - this will update the table to the filtered TEXTFIELD AUTHOR
	// TableAuthorField.textProperty().addListener((observable, oldValue, newValue)
	// -> {
	// updatePredicate(filteredList);
	// });
	// }
	// }

	//// **********************\\\\
	// maybe implement to filter ?
	//// **********************\\\\
	// private void loadSubject() {
	// HashSet<String> subjectUpdated = new HashSet<>();
	// subjectUpdated.addAll(LecturerController.subjectsList);
	// subjectUpdated.add(test.getSubject());
	// subjectList = FXCollections.observableArrayList(subjectUpdated);
	// subjectComboBox.setItems(subjectList);
	// subjectComboBox.setValue(test.getSubject());
	// }

	// private void loadCourse() {
	// HashSet<String> courseUpdated = new HashSet<>();
	// courseUpdated.addAll(LecturerController.coursesList);
	// courseUpdated.add(test.getCourse());
	// courseList = FXCollections.observableArrayList(courseUpdated);
	// courseComboBox.setItems(courseList);
	// courseComboBox.setValue(test.getCourse());
	// }

	/**
	 * This method is called to load data.
	 * It invokes the methods to load subjects, courses, and tests.
	 */
	void load() {
		// loadSubject();
		// loadCourse();
		loadTests();

	}

	/**
	 * This method is used to load tests.
	 * It retrieves and populates the tests data.
	 */
	public void loadTests() {
		ClientUI.chat.getAllTestsOfLecturer();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}

		// Checked.setCellValueFactory(new PropertyValueFactory<>("checked"));
		testID.setCellValueFactory(new PropertyValueFactory<>("id"));
		date.setCellValueFactory(new PropertyValueFactory<>("dateString"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		comment.setCellValueFactory(new PropertyValueFactory<>("testcomments"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		// course.setCellValueFactory(new PropertyValueFactory<>("course"));
		// department.setCellValueFactory(new PropertyValueFactory<>("department"));

		int cap = 20;
		// wait until we have a questions pulled from database
		while (TestList.isEmpty() && (cap > 0)) {
			try {
				Thread.sleep(250);
				cap--;
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * This method is triggered when the back button is pressed.
	 * It is responsible for navigating back to the Create Tests page.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests
		openScreen("/clientFXMLS/LecturerManageTest.fxml",
				"CEMS System - Lecturer - Create Tests", event);
	}

	/**
	 * This method is used to set the list of tests.
	 *
	 * @param TestList The list of tests to be set.
	 */
	public static void setTestList(ArrayList<Test> TestList) {
		DBTestController.TestList = TestList;
	}

	@FXML
	void EditTestsPressed(ActionEvent event) {

	}

	/**
	 * This method is triggered when the add question button is pressed.
	 * It is responsible for handling the operation of adding a question.
	 *
	 * @param event The action event that triggers this method.
	 */
	// @FXML
	// void addQuestionPressed(ActionEvent event) {
	// }

}
