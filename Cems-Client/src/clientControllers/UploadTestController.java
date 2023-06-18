package clientControllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadTestController extends BasicController {

	private String filePath = "none";
	File selectedFile;
	String filename;

	@FXML
	private TextField TextPath;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private Button chooseFile;

	@FXML
	private Button Upload;

	@FXML
	private TextField testID;

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
				// Send file and ID to the server
				ClientUI.chat.uploadFile(testID.getText(), fileContent, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(null,
					(String) "Something went wrong!\n File not found! try picking the file again.", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void backToLecturer(ActionEvent event) {
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer - Main Screen ", event);
	}
	@FXML
	void backPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer - Manage Test", event);
	}


}
