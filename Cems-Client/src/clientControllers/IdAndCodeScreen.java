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

/**
 * 
 * Controller class for the IdAndCodeScreen view.
 * 
 * Extends the BasicController class.
 */
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

	/**
	 * 
	 * Initializes the controller.
	 * Starts the clock.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * 
	 * Event handler for the Back button press.
	 * Opens the StudentScreen view for code entering.
	 * 
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student - Enter Code", event);
	}

	/**
	 * 
	 * Event handler for the Automatic button press.
	 * 
	 * @param event The action event triggered by the user.
	 */
	@FXML
	void AutomaticPressed(ActionEvent event) {

		String code = testCode.getText();
		examController = (StudentExamController) openScreen("/clientFXMLS/StudentExam.fxml",
				"CEMS System - Student - Exam", event);
		examController.load(code);
	}

	/**
	 * 
	 * Event handler for the Download button press.
	 * 
	 * @param event The action event triggered by the user.
	 */
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
			// fileChooser.setInitialDirectory(new File(System.getProperty("user.home") +
			// File.separator + "Desktop"));

			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					if (fileContent != null) {
						fos.write(fileContent);
						fos.flush();
						StudentUploadController suc = (StudentUploadController) openScreen("/clientFXMLS/StudentUploadTest.fxml", "CEMS System - Student - Test", event);
        				suc.load(test_code);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(null, (String) "Something went wrong!", (String) "Error!",
						JOptionPane.ERROR_MESSAGE);
		}
	}

	/////////////////////////////// Instance Methods ///////////////////////////////

	/**
	 * Retrieves the flag indicating if a test is currently running.
	 *
	 * @return {@code true} if a test is running, {@code false} otherwise.
	 */
	public static boolean isTestRunning() {
		return testRunning;
	}

	/**
	 * Sets the flag indicating if a test is currently running.
	 *
	 * @param testRunning {@code true} if a test is running, {@code false}
	 *                    otherwise.
	 */
	public static void setTestRunning(boolean testRunning) {
		IdAndCodeScreen.testRunning = testRunning;
	}

	/**
	 * Sets the test code and test ID.
	 *
	 * @param list The list containing the test code and test ID.
	 */
	public static void setTest_code(ArrayList<String> list) {
		test_code = list.get(0);
		test_id = list.get(1);
	}

	private static FileDownloadMessage downloadMessage;

	/**
	 * Retrieves the file download message.
	 *
	 * @return The file download message.
	 */
	public synchronized static FileDownloadMessage getDownloadMessage() {
		return downloadMessage;
	}

	/**
	 * Sets the file download message.
	 *
	 * @param downloadMessage The file download message to set.
	 */
	public synchronized static void setDownloadMessage(FileDownloadMessage downloadMessage) {
		IdAndCodeScreen.downloadMessage = downloadMessage;
	}

}
