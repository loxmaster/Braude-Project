package clientControllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadTestController extends BasicController {

	private String filePath = "none";

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
		Stage stage = (Stage) chooseFile.getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			TextPath.setText(filePath);
		}

	}

	@FXML
	void UploadPressed(ActionEvent event) {
		if (!filePath.isEmpty() && !(filePath == "none") && (!testID.getText().isEmpty())) {
			try {
				File selectedFile = new File(filePath);

				// Create the output stream and object output stream
				FileOutputStream fileOut = new FileOutputStream(selectedFile);
				// ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				BufferedOutputStream objectOut = new BufferedOutputStream(fileOut);

				byte[] mybytearray = new byte[(int) selectedFile.length()];

				// Write the selected file object to the output stream
				objectOut.write(mybytearray, 0, mybytearray.length);
				// Close the streams
				objectOut.close();

				//sendToServer((Object)mybytearray);

				System.out.println("File uploaded successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No File Chosen", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	@FXML
	void backToLecturer(ActionEvent event) {

	}

}
