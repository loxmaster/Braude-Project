package clientControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class IdAndCodeScreen extends BasicController { //TODO change name

	@FXML
    private Button downloadButton;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TextField ID;

	@FXML
    private TextField code;

	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student - Enter Code", event);
	}

	@FXML
	void submitPressed(ActionEvent event) {
		/*
		* grab id and CODE text from text field
		* validate information...
		*/
		String testCode = code.getText();

		// Opens the Choose test Type screen and sets the test code for the controller.
		StudentExamController sec = (StudentExamController) openScreen("/clientFXMLS/ChooseTestType.fxml", "CEMS System - Student - Exam Type Selection", event);
		sec.loadTestCode(testCode);
	}

}
