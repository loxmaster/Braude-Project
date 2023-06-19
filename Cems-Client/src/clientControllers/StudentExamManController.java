package clientControllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.QuestionModel;
import logic.Test;

public class StudentExamManController extends BasicController {

	private String filePath = "none";
	File selectedFile;
	String filename;

	@FXML
	private TextField TextPath;

	@FXML
	private Button exitbutton,submitButton;

	@FXML
	private Button logo;

	@FXML
	private Button chooseFile;

	@FXML
	private Button Upload;



	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	@FXML
	void OpenFileMenu(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
		Stage stage = (Stage) chooseFile.getScene().getWindow();
		selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			TextPath.setText(filePath);
			filename = selectedFile.getName();
		}

	}

	@FXML
	synchronized void UploadPressed(ActionEvent event) {
		// Open file chooser dialog

		if (selectedFile != null) {
			try (FileInputStream fileInputStream = new FileInputStream(selectedFile);
					ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {

				byte[] buffer = new byte[8192];
				int bytesRead;
				while ((bytesRead = fileInputStream.read(buffer)) != -1) {
					byteOutputStream.write(buffer, 0, bytesRead);
				}

				byte[] fileContent = byteOutputStream.toByteArray();


				//REVIEW 
				//ClientUI.chat.uploadFileToTestComplited(ClientHandler.user.getUser_id(), fileContent, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(null,
					(String) "Something went wrong!\n File not found! try picking the file again.", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void exitExam(ActionEvent event) {
		backToStudent(event);
	}

		@FXML
	void submitExam(ActionEvent event) {
		

		JOptionPane.showMessageDialog(null, "Good Luck !", "Success !", JOptionPane.WARNING_MESSAGE);
		backToStudent(event);
	}

}