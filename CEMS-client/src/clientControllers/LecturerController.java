package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logic.User;

public class LecturerController extends BasicController {
	// idk if lecturer is needed , can be accessed through ClientHandler
	public User lecturer;
	public static ArrayList<String> subjectsList = new ArrayList<String>();
	public static ArrayList<String> questions = new ArrayList<String>();

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
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerOptions.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Questions");
		
	}

	@FXML
	void CheckTheTestsPressed(ActionEvent event) {
		// open Test Check screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Check Tests");
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
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
		ClientHandler.resetClientData();
	}

	@FXML
	void CreateTestsPressed(ActionEvent event) {
		// open Create Tests from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Create Tests");
	}

	@FXML
	void MenageOngoingTestsPressed(ActionEvent event) {
		// open Ongoing Test screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerOngoingTest.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Menage Tests");
	}

	@FXML
	void StatisticalInfoPressed(ActionEvent event) {
		// open Statistical information from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LecturerStatistical.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer - Statistical Information");
	}

}
