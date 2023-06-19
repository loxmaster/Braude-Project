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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * Controller class for handling upload test functionality.
 * 
 * Extends BasicController.
 */
public class UploadTestController extends BasicController {

	private String filePath = "none"; // Path to the selected file
	private File selectedFile; // The selected file
	private String filename; // Name of the selected file

	@FXML
	private TextField TextPath; // Text field for displaying the file path

	@FXML
	private Button exitbutton; // Button for exiting

	@FXML
	private Button logo; // Button for displaying logo

	@FXML
	private Button chooseFile; // Button for choosing a file

	@FXML
	private Button Upload; // Button for uploading

	@FXML
	private TextField testID; // Text field for entering the test ID

	@FXML
	private Label live_time; // Label for displaying live time

	/**
	 * 
	 * Initializes the controller.
	 * Starts the clock by calling the Timenow() method with the live_time label.
	 */
	@FXML
	void initialize() {
		Timenow(live_time);
	}

	/**
	 * Event handler for opening the file menu.
	 *
	 * @param event The action event triggered by the user.
	 */
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

	/**
	 * Event handler for the Upload button press.
	 *
	 * @param event The action event triggered by the user.
	 */
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

				// REVIEW
				ClientUI.chat.uploadFile(testID.getText(), fileContent, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(null,
					(String) "Something went wrong!\n File not found! try picking the file again.", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * 
	 * Event handler for navigating back to the Lecturer screen.
	 * 
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void backToLecturer(ActionEvent event) {
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer - Main Screen ", event);
	}

	/**
	 * 
	 * Event handler for navigating back to the Manage Test screen.
	 * 
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void backPressed(ActionEvent event) {
		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer - Manage Test", event);
	}

}
