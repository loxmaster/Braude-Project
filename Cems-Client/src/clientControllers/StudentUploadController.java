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
import logic.Test;

public class StudentUploadController extends BasicController{

    private String testId;
    private String filePath = "none";
	private File selectedFile;
	private String filename;

    @FXML
    private TextField TextPath;

    @FXML
    private Button Upload;

    @FXML
    private Button chooseFile;

    @FXML
    private Button exitbutton;

    @FXML
    private Label live_time;

    @FXML
    private Button logo;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    public void load(String testId) {
        this.testId = testId;
    }

    @FXML
	void OpenFileMenu(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Choose a file");
		// fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
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
				ClientUI.chat.uploadStudentFile(testId, fileContent, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(null,
					(String) "Something went wrong!\n File not found! try picking the file again.", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer - Manage Test", event);
	}

}
