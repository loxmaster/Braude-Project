package clientControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import logic.FileDownloadMessage;

public class EnterIDForTestController extends BasicController {

	public static String test_code;

	public static void setTest_code(String code) {
		test_code = code;
	}


	private static FileDownloadMessage downloadMessage;

	public synchronized static FileDownloadMessage getDownloadMessage() {
		return downloadMessage;
	}

	public synchronized static void setDownloadMessage(FileDownloadMessage downloadMessage) {
		EnterIDForTestController.downloadMessage = downloadMessage;
	}

	
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
	void backPressed(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student - Enter Code", event);
	}

	@FXML
    void AutomaticPressed(ActionEvent event) {

		try {
			ClientUI.chat.isStudentTakingCourse();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));

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
		} else
			JOptionPane.showMessageDialog(null, (String) "Something went wrong!", (String) "Error!",
					JOptionPane.ERROR_MESSAGE);
	}
	// }

}
}
