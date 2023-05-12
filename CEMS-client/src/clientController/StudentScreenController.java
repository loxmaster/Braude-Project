package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logic.User;

public class StudentScreenController extends BasicController {
	private User student;

	@FXML
	private TextArea helpLabel;

	public void loadStudent(User student) {
		this.student = student;
	}

	@FXML
	void LogOutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (helpLabel.isVisible())
			helpLabel.setVisible(false);
		else
			helpLabel.setVisible(true);
	}

	@FXML
	void OpenCodePrompt(ActionEvent event) {
		// Opening window for code entering
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student - Enter Test Code");
	}

	@FXML
	void showGrades(ActionEvent event) {
		// Opening Show Grades screen
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/ViewGrades.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student Grades");
	}

	// sorry
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
		stage.setTitle("CEMS - Login");
		stage.setScene(scene);
		stage.show();
		stage.setTitle("CEMS System - Student");
	}

}
