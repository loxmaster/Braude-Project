package clientControllers;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
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
	private static ArrayList<Test> TestList;

	@FXML
	private TableColumn<Test, String> comment;

	@FXML
	private TableColumn<Test, String> course;

	@FXML
	private TableColumn<Test, String> date;

	@FXML
	private TableColumn<Test, String> department;

	@FXML
	private TableColumn<Test, String> duration;

	ObservableList<String> subjectList, courseList;

	@FXML
	private Button EditTestPressed;

	@FXML
	private TableColumn<Test, String> testID;

	@FXML
	private TableColumn<Test, String> time;

	@FXML
	private TableColumn<Test, String> Author;

	@FXML
	private TableColumn<Test, RadioButton> choose;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TableView<Test> table;

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

	void load() {
		// loadSubject();
		// loadCourse();
		loadTests();

	}

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

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests
		openScreen("/clientFXMLS/LecturerManageTest.fxml",
				"CEMS System - Lecturer - Create Tests", event);
	}

	public static void setTestList(ArrayList<Test> TestList) {
		DBTestController.TestList = TestList;
	}

	// this shouldnt work , and honastly it doesnt work, but hey , now the page
	// opens ?
	@FXML
	void addQuestionPressed(ActionEvent event) {
	}

}
