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

/**
 * This controller handles the login screen of the application.
 * It extends from the BasicController class.
 */
public class LoginScreenController extends BasicController {

	private String username, password;

	@FXML
	private AnchorPane AncPane; // AnchorPane for the login screen

	@FXML
	private ComboBox<String> combo_Role; // ComboBox for selecting the role

	@FXML
	private TextField emailTextbox; // TextField for entering the email

	@FXML
	private Button exitbutton; // Button for exiting the application

	@FXML
	private Label live_time; // Label for displaying the live time

	@FXML
	private Button loginbutton; // Button for logging in

	@FXML
	private Button logo; // Button for the logo

	@FXML
	private PasswordField passTextbox; // PasswordField for entering the password

	@FXML
	private Label wrongLabel; // Label for displaying wrong login information

	/**
	 * This function initializes the controller.
	 * It starts the clock on the UI and adds values to the role ComboBox.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
		// Add values to combo_Role
		combo_Role.getItems().addAll("student", "lecturer", "head_of_department");
	}

	/**
	 * This function handles the login button press event.
	 * It verifies the user credentials from the database and opens the corresponding screen based on the user type.
	 *
	 * @param event The ActionEvent object representing the login button press event
	 * @throws IOException If an input or output exception occurred
	 */
	@FXML
	void pressedLogin(ActionEvent event) throws IOException {

		// Opens connection if closed
		ClientUI.chat.openConnection();

		// gest text from fields
		username = emailTextbox.getText();
		//password = passTextbox.getText();
		String selectedRole = combo_Role.getValue();

		System.out.println("Entered: " + username + " " + password + " " + selectedRole);

		if ((username.trim().isEmpty() || password.trim().isEmpty() || selectedRole == null )) {
			// setVisibleFalse();
			JOptionPane.showMessageDialog(null, "your username or password are incorrect!",
					"incorrect username or password",
					JOptionPane.ERROR_MESSAGE);
			if (username.isEmpty()) {
				emailTextbox.setStyle("-fx-background-color: rgb(255, 74, 74);");
			}
			if (password.isEmpty()) {
				passTextbox.setStyle("-fx-background-color: rgb(255, 74, 74);;");
			}

			if (selectedRole == null) {
				combo_Role.setStyle("-fx-background-color: rgb(255, 74, 74);;");
			}
			return;
		}

		else {
			// Verifies students credintials from database
			ClientUI.chat.loginVarification(username, password, selectedRole);

			// Waits for user to be found
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}

			if (!ClientHandler.user.getIsFound()) {
						JOptionPane.showMessageDialog(null, "your username or password or type are incorrect!",
					"Fail LogIn",
					JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "your username or password are incorrect!",
								"incorrect username or password",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		}
	}

/**
 * Starts the application by opening the LoginScreen view.
 *
 * @param stage The primary stage of the application.
 * @throws IOException If an error occurs while loading the LoginScreen view.
 */
public void start(Stage stage) throws IOException {
    openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS - Login", null);
}
}
