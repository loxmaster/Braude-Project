package clientControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import logic.FileDownloadMessage;

public class IdAndCodeScreen extends BasicController {

	
	/////////////////////////////// Local Variables ///////////////////////////////


	private StudentExamController examController;

	public static String test_code = null;

	public static String test_id = null;

	public static boolean testRunning = false;


	/////////////////////////////// FXML variables ////////////////////////////////


	@FXML
    private Button downloadButton;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TextField testCode;

	@FXML
	private Button ClickDownload;

	@FXML
	private ImageView download;

	@FXML
	private ImageView onlineTest;
	
	@FXML
    private Label live_time;

	/////////////////////////////// FXML methods ///////////////////////////////


	@FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student - Enter Code", event);
	}

	@FXML
	void AutomaticPressed(ActionEvent event) {

		// TODO see what not works here , when works delete whats below and all guchi
		/*
		if ((test_code == null)) {
			try {
				ClientUI.chat.isStudentTakingCourse();
				Thread.sleep(3000);
				ClientUI.chat.isTestReady(test_id);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		 
		if (test_code == "none" || !(testCode.getText().equals(test_code)) || !testRunning) {
			JOptionPane.showMessageDialog(null,
					(String) "you either enetered a wrongh test code\n or you're not enlisted in this course!",
					(String) "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (testCode.getText().equals(test_code) && testRunning) {
			examController = (StudentExamController) openScreen("/clientFXMLS/StudentExam.fxml",
					"CEMS System - Student - Exam", event);
			examController.load(test_id);
		}
		*/

		String code = testCode.getText();
		examController = (StudentExamController) openScreen("/clientFXMLS/StudentExam.fxml",
					"CEMS System - Student - Exam", event);
		examController.load(code);
	}

	@FXML
	synchronized void ClickDownload(ActionEvent event) {

		test_code = new String(testCode.getText());

		ClientUI.chat.downloadFile(test_code);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (downloadMessage.getFileContent() == null) {
			JOptionPane.showMessageDialog(null, (String) "Wrong test id!", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			byte[] fileContent = downloadMessage.getFileContent();
			String filename = downloadMessage.getFilename();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save File");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
			fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setInitialFileName(filename);
			//fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));

			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					if (fileContent != null) {
						fos.write(fileContent);
						fos.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				openScreen("/clientFXMLS/StudentExamMan.fxml", "CEMS System - Lecturer - Upload A Test", event);
			} else
				JOptionPane.showMessageDialog(null, (String) "Something went wrong!", (String) "Error!",
						JOptionPane.ERROR_MESSAGE);
		}
	}


	/////////////////////////////// Instance Methods ///////////////////////////////


	public static boolean isTestRunning() {
		return testRunning;
	}

	public static void setTestRunning(boolean testRunning) {
		IdAndCodeScreen.testRunning = testRunning;
	}

	public static void setTest_code(ArrayList<String> list) {
		test_code = list.get(0);
		test_id = list.get(1);
	}

	private static FileDownloadMessage downloadMessage;

	public synchronized static FileDownloadMessage getDownloadMessage() {
		return downloadMessage;
	}

	public synchronized static void setDownloadMessage(FileDownloadMessage downloadMessage) {
		IdAndCodeScreen.downloadMessage = downloadMessage;
	}

}

