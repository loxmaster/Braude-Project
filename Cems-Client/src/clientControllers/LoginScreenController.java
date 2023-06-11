package clientControllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import clientHandlers.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController extends BasicController {

	private String username, password;

	@FXML
	private Button BtnInfo;

	@FXML
	private Label wrongLabel;

	@FXML
	private TextField emailTextbox;

	@FXML
	private PasswordField passTextbox;

	@FXML
	void pressedLogin(ActionEvent event) throws IOException {

		// Opens connection if closed
		ClientUI.chat.openConnection();

		// gets text from fields
		// username = emailTextbox.getText();
		// password = passTextbox.getText();
		// emailTextbox.setText("noah");
		// passTextbox.setText("123456");
		username = "noah";
		password = "123456";
		System.out.println("Entered: " + username + " " + password);

		if (username.trim().isEmpty() || password.trim().isEmpty())
			setVisibleFalse();
		else {
			// Verifies students credintials from database
			ClientUI.chat.loginVarification(username, password);

			// Waits 5 seconds for user to be found
			int cap = 20;
			while (!ClientHandler.user.getIsFound() && (cap > 0)) {
				try {
					Thread.sleep(250);
					cap--;
				} catch (InterruptedException e) {
				}
			}

			if (!ClientHandler.user.getIsFound()) {
				setVisibleFalse();
				System.out.println("user not found! (loginscreencontroller)");
			} else {
				// if found loading the corresponding screen
				switch (ClientHandler.user.getType()) {
					case "student": {
						studentLogin(event);
						break;
					}

					case "lecturer": {
						lecturerLogin(event);
						break;
					}

					case "hod": {
						hodLogin(event);
						break;
					}

					// If the type isnt ok
					default: {
						JOptionPane.showMessageDialog(null, "Something wrong with database !", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		}
	}

	private void studentLogin(ActionEvent event) {
		StudentScreenController ssc = (StudentScreenController) openScreen("/clientFXMLS/StudentScreen.fxml",
				"CEMS System - Student", event);
		ssc.loadStudent(ClientHandler.user);
		System.out.println("Opening Student screen...");

	}

	private void lecturerLogin(ActionEvent event) {
		LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer",
				event);
		lc.loadLecturer(ClientHandler.user);
		System.out.println("Opening Lecturer screen...");

	}

	private void hodLogin(ActionEvent event) {
		HODController hoc = (HODController) openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of The Department",
				event);
		hoc.loadHOD(ClientHandler.user);
		System.out.println("Opening Head Of Department screen...");

	}

	void setVisibleFalse() {
		wrongLabel.setVisible(true);
	}

	public void start(Stage stage) throws IOException {
		openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS - Login", null);
	}
}
