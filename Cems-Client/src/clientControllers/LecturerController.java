package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import logic.QuestionModel;
import logic.User;

/**
 * 
 * Controller class for the Lecturer view.
 * 
 * Extends the BasicController class.
 */
public class LecturerController extends BasicController {

	public static ArrayList<String> subjectsList;
	public static ArrayList<String> coursesList;
	public static ArrayList<QuestionModel> questions;

	@FXML
	private Button BtnInfo;

	@FXML
	private Label welcomeLabel;

	@FXML
	private TextArea textBox;

	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * Method for setting the name of the user.
	 */
	public void setWelcomeLabel() {
		welcomeLabel.setText(welcomeLabel.getText() + " " + ClientHandler.user.getUsername() + "!");
	}

	/**
	 * Loads the lecturer data.
	 *
	 * @param user The User object representing the logged-in lecturer.
	 */
	public void loadLecturer(User user) {
		// Get all the subjects for the lecturer
		setSubjectsList(new ArrayList<String>());
		ClientUI.chat.getSubjectsForLecturer((Object) ClientHandler.user.getUsername());

		setCoursesList(new ArrayList<String>());
		ClientUI.chat.getCoursesForLecturer((Object) ClientHandler.user.getUsername());

		// Sets the welcome label
		setWelcomeLabel();
	}

	/**
	 * Event handler for the Question button press.
	 * Opens the LecturerOptions view.
	 *
	 * @param event The action event triggered by the user.
	 * @throws IOException If an error occurs while loading the LecturerOptions
	 *                     view.
	 */
	@FXML
	void QuestionPressed(ActionEvent event) throws IOException {
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

	/**
	 * Event handler for the Check The Tests button press.
	 * Opens the LecturerCheckAutomatingTest view and loads the table.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void CheckTheTestsPressed(ActionEvent event) {
		CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml",
				"CEMS System - Lecturer - Check Tests", event);
		ctc.loadTable();
	}

	/**
	 * Event handler for the Help button press.
	 * Toggles the visibility of the text box.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void HelpPressed(ActionEvent event) {
		if (textBox.isVisible())
			textBox.setVisible(false);
		else
			textBox.setVisible(true);
	}

	/**
	 * Event handler for the Upload Test button press.
	 * Opens the LecturerTestUpload view.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void UploadTestPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerTestUpload.fxml", "CEMS System - Lecturer - Upload A Test", event);
	}

	/**
	 * Event handler for the Edit Tests button press.
	 * Opens the LecturerTestTable view, loads the table, and initializes it.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void EditTestsPressed(ActionEvent event) {
		DBTestController dbt = (DBTestController) openScreen("/clientFXMLS/LecturerTestTable.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		dbt.load();
		dbt.initialize();
	}

	/**
	 * Event handler for the Create Tests button press.
	 * Opens the LecturerCreateTest view, loads the filter combo boxes, and
	 * initializes it.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void CreateTestsPressed(ActionEvent event) {
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.loadFilterComboboxes();
		ctc.initialize();
	}

	/**
	 * Event handler for the Manage Tests button press.
	 * Opens the LecturerManageTest view.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void ManageTestsPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer - Create Tests", event);
		// ctc.loadFilterComboboxes();
	}

	/**
	 * Event handler for the Manage Ongoing Tests button press.
	 * Opens the LecturerOngoingTest view and loads the table.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen
		CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerOngoingTest.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.loadTable();
	}

	/**
	 * Event handler for the Statistical Info button press.
	 * Opens the LecturerStatistical view and loads the data.
	 *
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information
		LecturerStatisticalController lsc = (LecturerStatisticalController) openScreen(
				"/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical Information", event);
		lsc.load();
	}

	/**
	 * Retrieves the list of subjects.
	 *
	 * @return The list of subjects.
	 */
	public static ArrayList<String> getSubjectsList() {
		return subjectsList;
	}

	/**
	 * Retrieves the list of courses.
	 *
	 * @return The list of courses.
	 */
	public static ArrayList<String> getCoursesList() {
		return coursesList;
	}

	/**
	 * Sets the list of subjects.
	 *
	 * @param subjectsList The list of subjects to set.
	 */
	public static void setSubjectsList(ArrayList<String> subjectsList) {
		LecturerController.subjectsList = subjectsList;
	}

	/**
	 * Sets the list of courses.
	 *
	 * @param coursesList The list of courses to set.
	 */
	public static void setCoursesList(ArrayList<String> coursesList) {
		LecturerController.coursesList = coursesList;
	}

	/**
	 * Retrieves the list of questions.
	 *
	 * @return The list of questions.
	 */
	public static ArrayList<QuestionModel> getQuestions() {
		return questions;
	}

	/**
	 * Sets the list of questions.
	 *
	 * @param questions The list of questions to set.
	 */
	public static void setQuestions(ArrayList<QuestionModel> questions) {
		LecturerController.questions = questions;
	}

}
