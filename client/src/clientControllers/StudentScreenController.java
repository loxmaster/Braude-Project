package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentScreenController {

	@FXML
	private TextArea helpLabel;

	@FXML
	void LogOutPressed(ActionEvent event) {
		// Opens login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    	AnchorPane vbox = null;
    	try {
    		vbox = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Scene scene = new Scene(vbox);
    	currentStage.setScene(scene);
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
		AnchorPane newScene = new AnchorPane();
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/EnterCodeForTestPreforming.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Student - Enter Code");
		currentStage.setScene(scene);
	}

	@FXML
	void showGrades(ActionEvent event) {
		// Opening Show Grades screen
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Pane newScene = new Pane();
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/ViewGrades.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Student Grades");
		currentStage.setScene(scene);
	}
}
