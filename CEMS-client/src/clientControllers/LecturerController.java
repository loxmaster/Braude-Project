package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import logic.QuestionModel;
import logic.User;

public class LecturerController extends BasicController {
	// idk if lecturer is needed , can be accessed through ClientHandler
	public User lecturer;
	public static ArrayList<String> subjectsList = new ArrayList<String>();
	public static ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

	@FXML
	private Button BtnInfo;

	@FXML
	private Label welcomeLabel;

	public User getLecturer() {
		return lecturer;
	}

	public void setLecturer(User lecturer) {
		this.lecturer = lecturer;
	}

	@FXML 
	private TextArea textBox;

	public void loadLecturer(User user) {
		// get all the data for subjects
		// thread here
		lecturer = user;
		ClientUI.chat.getSubjectsForLecturer((Object)lecturer.getUsername());
		welcomeLabel.setText(welcomeLabel.getText() + " " + lecturer.getUsername().toUpperCase() + " !"); // lasim lev leshanot le name ve lo username
	}

	@FXML
	void QuestionPressed(ActionEvent event) throws IOException {
		// go to options screen
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer - Questions", event);
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
	void LogOutPressed(ActionEvent event) {
		// open Login screen
		logoutPressed(event);
	}

	@FXML
	void CreateTestsPressed(ActionEvent event) {
		// open Create Tests
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
	}

	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen
		openScreen("/clientFXMLS/LecturerOngoingTest.fxml", "CEMS System - Lecturer - Menage Tests", event);
	}

	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information
		openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical Information", event);
	}

}
