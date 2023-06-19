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
import logic.Test;
import logic.User;

public class LecturerController extends BasicController {

	public static ArrayList<String> subjectsList;
	public static ArrayList<QuestionModel> questions;
	public static ArrayList<Test> ongoingTests;

	@FXML
	private Button BtnInfo;

	@FXML
	private Label welcomeLabel;

	@FXML
	private TextArea textBox;

	/**
	 * Method for setting the name of the user.
	 * TODO change to name and not username.
	 */
	public void setWelcomeLabel() {
		welcomeLabel.setText(welcomeLabel.getText() + " " + ClientHandler.user.getUsername().toUpperCase() + " !");
	}

	public void loadLecturer(User user) {
		// get all the subjects for lecturer
		setSubjectsList(new ArrayList<String>());
		ClientUI.chat.getSubjectsForLecturer((Object) ClientHandler.user.getUsername());
		// Sets the welcome label
		setWelcomeLabel();
	}

	@FXML
	void QuestionPressed(ActionEvent event) throws IOException {
		// go to options screen
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

	@FXML
	void CheckTheTestsPressed(ActionEvent event) {
		// open Test Check screen
		openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml", "CEMS System - Lecturer - Check Tests", event);
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (textBox.isVisible())
			textBox.setVisible(false);
		else
			textBox.setVisible(true);
	}

	@FXML
	void CreateTestsPressed(ActionEvent event) {
		// open Create Tests
		CreateTestController ctc = (CreateTestController) openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
		ctc.load();
	}

	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen 
		OngoingTestController controller = new OngoingTestController();
		OngoingTestController lsc = (OngoingTestController) openScreen2("/clientFXMLS/LecturerOngoingTest.fxml", "CEMS System - HOD - permissions", event,controller);
		lsc.load();
	}

	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information
		LecturerStatisticalController lsc = (LecturerStatisticalController) openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical Information", event);
		lsc.load();
	}
	
	

	public static ArrayList<String> getSubjectsList() {
		return subjectsList;
	}

	public static void setSubjectsList(ArrayList<String> subjectsList) {
		LecturerController.subjectsList = subjectsList;
	}

	public static ArrayList<QuestionModel> getQuestions() {
		return questions;
	}

	public static void setQuestions(ArrayList<QuestionModel> questions) {
		LecturerController.questions = questions;
	}
	public static ArrayList<Test> getOngoingTests() {
		return ongoingTests;
	}

	public static void setOngoingTests(ArrayList<Test> ongoingTests) {
		LecturerController.ongoingTests = ongoingTests;
	}
}
