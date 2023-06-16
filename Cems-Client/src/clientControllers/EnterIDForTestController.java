package clientControllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EnterIDForTestController extends BasicController {

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TextField ID;

	@FXML
	private Button AutomaticPressed;

	@FXML
	private Button ManualPressed;

	@FXML
    private Button ClickDownload;

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

		// openScreen("/clientFXMLS/StudentTestQ.fxml", "CEMS System - Student - Exam",
		// event);
		openScreen("/clientFXMLS/ChooseTestType.fxml", "CEMS System - Student - Exam", event);
		
	}
	
	@FXML
	void AutomaticPressed(ActionEvent event) {
		
	}
	
	@FXML
	void ManualPressed(ActionEvent event) {
		openScreen("/clientFXMLS/getTestFile.fxml", "CEMS System - Student - Exam", event);
	}

	

    @FXML
    void ClickDownload(ActionEvent event) {
		String testidAssociatedWithCode = "123";
		try {
			// Create the input stream and object input stream
			FileInputStream fileIn = new FileInputStream("Testid"+testidAssociatedWithCode);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			// Read the file object from the input stream
			File uploadedFile = (File) objectIn.readObject();
			
			// Process the uploaded file as needed
			// For example, display the file path
			String filePath = uploadedFile.getAbsolutePath();
			System.out.println("Accepted file: " + filePath);
			
			// Close the streams
			objectIn.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

    }

}
