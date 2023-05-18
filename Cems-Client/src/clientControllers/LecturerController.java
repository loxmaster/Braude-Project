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

	public static ArrayList<String> subjectsList = new ArrayList<String>();
	public static ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();

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
