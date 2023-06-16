package clientControllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	String testidAssociatedWithCode = "123";

	@FXML
	void ClickDownload(ActionEvent event) {

		File receivedFile = null;
		try {
			/*
			 * check test id and validate from db
			 * it exists as an uploaded file
			 * if so, concat the 'Testid' with the id
			 */
			File newFile = new File("Cems-Server/TestFiles/Testid" + "123");
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileOutputStream fis = new FileOutputStream(newFile);
			fis.write(mybytearray);

			// bufferedIn.write(fis);

			// bufferedIn.read(mybytearray,0,mybytearray.length);

			System.out.println("File received from server.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return receivedFile;

	}
}
