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

	public void loadLecturer(User user) {
		// get all the subjects for lecturer
		setSubjectsList(new ArrayList<String>());
		ClientUI.chat.getSubjectsForLecturer((Object) ClientHandler.user.getUsername());

		setCoursesList(new ArrayList<String>());
		ClientUI.chat.getCoursesForLecturer((Object) ClientHandler.user.getUsername());

		// Sets the welcome label
		setWelcomeLabel();
	}

	// go to questions screen
	@FXML
	void QuestionPressed(ActionEvent event) throws IOException {
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

	@FXML
	void CheckTheTestsPressed(ActionEvent event) {
		CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml",
				"CEMS System - Lecturer - Check Tests", event);
		ctc.loadTable();
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (textBox.isVisible())
			textBox.setVisible(false);
		else
			textBox.setVisible(true);
	}

	@FXML
	void UploadTestPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerTestUpload.fxml", "CEMS System - Lecturer - Upload A Test", event);
	}

	@FXML
	void EditTestsPressed(ActionEvent event) {
		DBTestController dbt = (DBTestController) openScreen("/clientFXMLS/LecturerTestTableNoAddQuestionPlease.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		dbt.load();
		dbt.initialize();
	}

	@FXML
	void CreateTestsPressed(ActionEvent event) {
		// open Create Tests
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.loadFilterComboboxes();
		ctc.initialize();
	}

	@FXML
	void ManageTestsPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer - Create Tests", event);
		// ctc.loadFilterComboboxes();

	}

	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen
		CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerOngoingTest.fxml",
				"CEMS System - Lecturer - Create Tests", event);
		ctc.loadTable();
	}

	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information
		LecturerStatisticalController lsc = (LecturerStatisticalController) openScreen(
				"/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical Information", event);
		lsc.load();
	}

	public static ArrayList<String> getSubjectsList() {
		return subjectsList;
	}

	public static ArrayList<String> getCoursesList() {
		return coursesList;
	}

	public static void setSubjectsList(ArrayList<String> subjectsList) {
		LecturerController.subjectsList = subjectsList;
	}

	public static void setCoursesList(ArrayList<String> coursesList) {
		LecturerController.coursesList = coursesList;
	}

	public static ArrayList<QuestionModel> getQuestions() {
		return questions;
	}

	public static void setQuestions(ArrayList<QuestionModel> questions) {
		LecturerController.questions = questions;
	}

}
