package clientControllers;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.QuestionModel;

public class EditQuestionController extends BasicController {

	// Observable lists to hold the subjects and courses
	private ObservableList<String> subjectList;
	private ObservableList<String> courseList;

	// Original ID of the question
	private String originalId;

	@FXML
	private ToggleGroup editGroupEditQuestion; // Toggle group for the radio buttons

	@FXML
	private TextField qA, qB, qC, qD; // Text fields for the answer options

	@FXML
	private Button BtnInfo; // Info button

	@FXML
	private TextField body; // Text field for the question body

	@FXML
	private RadioButton A, B, C, D; // Radio buttons for the correct answer

	@FXML
	private TextField qNumber; // Text field for the question number

	@FXML
	private ComboBox<String> subjectCombobox; // Combo box for selecting the subject

	@FXML
	private ComboBox<String> courseComboBox; // Combo box for selecting the course

	@FXML
	private Label live_time; // Label for displaying the live time

	/**
	 * This function initializes the controller.
	 * It starts the clock on the UI.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * This function loads the subjects and courses into the combo boxes.
	 */
	public void loadEditQuestionScreen() {
		// Get the subjects and courses from the LecturerController
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

		// Clear and set the items in the subject combo box
		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);

		// Clear and set the items in the course combo box
		courseComboBox.getItems().removeAll();
		courseComboBox.setItems(courseList);
	}

	/**
	 * This function loads the details of the question to be edited into the UI.
	 * 
	 * @param question The question model containing the details of the question.
	 * @param id       The ID of the question.
	 */
	public void loadQuestion(QuestionModel question, String id) {
		// Set the question body text
		body.setText(question.getQuestiontext());

		// Create hash sets to hold the updated subjects and courses
		HashSet<String> subjectUpdated = new HashSet<>();
		HashSet<String> courseUpdated = new HashSet<>();

		// Add the current subjects and courses to the updated lists
		subjectUpdated.addAll(LecturerController.subjectsList);
		courseUpdated.addAll(LecturerController.coursesList);

		// Add the subject and course of the question to the updated lists
		subjectUpdated.add(question.getSubject());
		courseUpdated.add(question.getCoursename());

		// Convert the updated lists to observable lists
		subjectList = FXCollections.observableArrayList(subjectUpdated);
		courseList = FXCollections.observableArrayList(courseUpdated);

		// Set the items in the course combo box and select the course of the question
		courseComboBox.setItems(courseList);
		courseComboBox.setValue(question.getCoursename());

		// Set the items in the subject combo box and select the subject of the question
		subjectCombobox.setItems(subjectList);
		subjectCombobox.setValue(question.getSubject());

		// Set the text of the answer options
		qA.setText(question.getOptionA());
		qB.setText(question.getOptionB());
		qC.setText(question.getOptionC());
		qD.setText(question.getOptionD());

		// Select the correct answer radio button
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
		}

		// Set the question number text
		qNumber.setText(question.getQuestionnumber());

		// Store the original ID of the question
		originalId = id;
	}

	/**
	 * This function is called when the Save button is pressed. It validates the
	 * input, updates the question in the database,
	 * and navigates back to the LecturerQuestionsTable screen.
	 * 
	 * @param event The event that triggered this function (pressing the Save
	 *              button).
	 */
	@FXML
	void SavePressed(ActionEvent event) {

		String correctAnswer = null;

		// Check if a subject has been selected
		if (subjectCombobox.getValue() == "" || subjectCombobox.getValue() == ""
				|| subjectCombobox.getValue() == null) {
			subjectCombobox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Subject Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			subjectCombobox.setStyle("-fx-background-color: white;");
		}

		// Check if a course has been selected
		if (courseComboBox.getValue() == "" || courseComboBox.getValue() == " " || courseComboBox.getValue() == null) {
			courseComboBox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Course Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			courseComboBox.setStyle("-fx-background-color: transparent;");
		}

		// Check if the question body is empty
		if (body.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions text !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Check if all answer options are filled
		if (qA.getText().isEmpty() || qB.getText().isEmpty() || qC.getText().isEmpty() || qD.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions all answer !", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		}

	// Check if the question number is empty
	if(qNumber.getText().isEmpty())

	{
		JOptionPane.showMessageDialog(null, "Please add question number !", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}}

	// Check if the question number is a valid number between 1 and 999
	try{
	int number = Integer.parseInt(qNumber.getText());if(number<1||number>999)
	{
				JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error",
						JOptionPane.ERROR_MESSAGE);
			int number = Integer.parseInt(qNumber.getText());
			if (number < 1 || number > 999) {
				JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				int length = qNumber.getText().length();
				if (length == 1)
					qNumber.setText("00" + qNumber.getText());
				if (length == 2)
					qNumber.setText("0" + qNumber.getText());
				if (length > 3) {
					JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}catch(
	NumberFormatException e)
	{
		JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error",
				JOptionPane.ERROR_MESSAGE);
		return;
		JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error",
				JOptionPane.ERROR_MESSAGE);
		return;
	}

	// Check if a correct answer has been selected
	if(!A.isSelected()&&!B.isSelected()&&!C.isSelected()&&!D.isSelected())
	{
		JOptionPane.showMessageDialog(null, "Please select correct answer !", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}

	// Determine which answer option is the correct one
	if(A.isSelected())correctAnswer="a";else if(B.isSelected())correctAnswer="b";else if(C.isSelected())correctAnswer="c";else if(D.isSelected())correctAnswer="d";

	// Set the update status to 1
	ClientUI.updatestatus=1;

	// Send the updated question and answer to the SQL database
	sendQandANStoSQL(subjectCombobox.getValue(), courseComboBox.getValue(), body.getText(), qNumber.getText(),
				qA.getText(), qB.getText(), qC.getText(), qD.getText(), correctAnswer);

		// Wait for the update to complete
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		// Check if the update was successful
		if (ClientUI.updatestatus == 0) {
			// If the update was not successful, show a warning message and return
			JOptionPane.showMessageDialog(null, "Changes NOT Saved! Question Number Exist In DB", "Fail!",
					JOptionPane.WARNING_MESSAGE);
			ClientUI.updatestatus = 1;
			return;
		}
		// If the update was successful, show a success message
		JOptionPane.showMessageDialog(null, "Changes Saved!", "Success!", JOptionPane.WARNING_MESSAGE);

		// Clear the list of questions
		LecturerController.setQuestions(new ArrayList<QuestionModel>());

		// Request the updated list of questions from the server
		ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());

		// Open the LecturerQuestionsTableController and load the questions into the
		// table
		LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen(
				"/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		lqtc.loadTable();

	}

	/**
	 * This function sends the updated question and answer options to the SQL
	 * database.
	 * 
	 * @param subject       The subject of the question.
	 * @param course        The course of the question.
	 * @param qBody         The body of the question.
	 * @param qnumber       The number of the question.
	 * @param optionA       The text of option A.
	 * @param optionB       The text of option B.
	 * @param optionC       The text of option C.
	 * @param optionD       The text of option D.
	 * @param correctAnswer The correct answer option (a, b, c, or d).
	 */
	public void sendQandANStoSQL(String subject, String course, String qBody, String qnumber, String optionA,
			String optionB,
			String optionC, String optionD, String correctAnswer) {
		// Get the subject ID from the data
		String subjectid;
		ClientUI.chat.GetSubjectIDfromSubjectCourses(subject);

		// Wait for the response
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Store the subject ID
		subjectid = CreateQuestionController.getSubjectID();
		System.out.println("CreateQuestion: " + subjectid);

		// Append the question number to the subject ID
		subjectid += qnumber;

		// Send the updated question to the SQL database
		ClientUI.chat.EditQuestion(subjectid, subject, course, qBody, qnumber, originalId);

		// Wait for the response
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Send the updated answer options to the SQL database
		ClientUI.chat.EditAnswers(subjectid, optionA, optionB, optionC, optionD, correctAnswer);
	}

	/**
	 * This function is called when the 'Back' button is pressed. It navigates the user back to the 'Questions Table' screen.
	 * 
	 * @param event The event triggered by pressing the 'Back' button.
	 */
	@FXML
	void backPressed(ActionEvent event) {
		// Open the LecturerQuestionsTableController and load the questions into the table
		LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen(
				"/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		lqtc.loadTable();
	}

	/**
	 * This function is called when the 'Cancel' button is pressed. It performs the same action as the 'Back' button, navigating the user back to the 'Questions Table' screen.
	 * 
	 * @param event The event triggered by pressing the 'Cancel' button.
	 */
	@FXML
	void cancelPressed(ActionEvent event) {
		// Call the backPressed function to navigate back to the 'Questions Table' screen
		backPressed(event);
	}

	/**
	 * This function is called when the 'Delete' button is pressed. It deletes the question from the database and navigates the user back to the 'Questions Table' screen.
	 * 
	 * @param event The event triggered by pressing the 'Delete' button.
	 */
	@FXML
	void DeletePressed(ActionEvent event) {
		// Set the update status to 1
		ClientUI.updatestatus = 1;

		// Send a request to delete the question from the database
		ClientUI.chat.DeleteQuestion(originalId);

		// If the question was not deleted successfully, display an error message
		if (ClientUI.updatestatus == 0) {
			JOptionPane.showMessageDialog(null, "Question NOT Deleted! Qustion Number Exist In DB", "Fail!",
					JOptionPane.WARNING_MESSAGE);
			ClientUI.updatestatus = 1;
			return;
		}
		// If the question was deleted successfully, display a success message
		JOptionPane.showMessageDialog(null, "Question Deleted!", "Success!", JOptionPane.WARNING_MESSAGE);

		// Clear the list of questions
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		// Request the updated list of questions from the server
		ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());

		// Open the LecturerQuestionsTableController and load the questions into the table
		LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen(
				"/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		lqtc.loadTable();
	}

}
