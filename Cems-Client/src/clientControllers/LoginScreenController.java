package clientControllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import clientHandlers.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenController extends BasicController {

	private String username, password;

	@FXML
	private AnchorPane AncPane;

	@FXML
	private ComboBox<String> combo_Role;

	@FXML
	private TextField emailTextbox;

	@FXML
	private Button exitbutton;

	@FXML
	private Label live_time;

	@FXML
	private Button loginbutton;

	@FXML
	private Button logo;

	@FXML
	private PasswordField passTextbox;

	@FXML
	private Label wrongLabel;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
		// Add values to combo_Role
		combo_Role.getItems().addAll("student", "lecturer", "head_of_department");
	}

	@FXML
	void pressedLogin(ActionEvent event) throws IOException {

		// Opens connection if closed
		ClientUI.chat.openConnection();

		// gest text from fields
		username = emailTextbox.getText();
		// // password = passTextbox.getText();
		//username = "noah";
		password = "123456";
		String selectedRole = combo_Role.getValue();

		System.out.println("Entered: " + username + " " + password + " " + selectedRole);

		if ((username.trim().isEmpty() || password.trim().isEmpty() && !selectedRole.equals("role")))
			setVisibleFalse();
		else {
			// Verifies students credintials from database
			ClientUI.chat.loginVarification(username, password,selectedRole);

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
						StudentScreenController ssc = (StudentScreenController) openScreen(
								"/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
						ssc.loadStudent(ClientHandler.user);
						System.out.println("Opening Student screen...");
						break;
					}

					case "lecturer": {
						LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml",
								"CEMS System - Lecturer", event);
						lc.loadLecturer(ClientHandler.user);
						System.out.println("Opening Lecturer screen...");
						break;
					}

					case "head_of_department":
					case "hod": {
						HODController hoc = (HODController) openScreen("/clientFXMLS/HOD.fxml",
								"CEMS System - Head Of The Department", event);
						hoc.loadHOD(ClientHandler.user);
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

	void setVisibleFalse() {
		wrongLabel.setVisible(true);
	}

	public void start(Stage stage) throws IOException {
		openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS - Login", null);
	}
}
