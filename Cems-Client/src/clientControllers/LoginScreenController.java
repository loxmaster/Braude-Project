package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenController extends BasicController {

	// private String username, password, selectedRole;

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
	public String pressedLogin(ActionEvent event) throws IOException {
		ArrayList<String> credentials = new ArrayList<>();

		// Opens connection if closed
		ClientUI.chat.openConnection();

		// gest text from fields
		credentials = getLoginCredentials();
		
		if (credentials == null)
			return "Credentials are empty!";

		else if (credentials.get(0) == null) {
			emailTextbox.setStyle("-fx-background-color: rgb(255, 74, 74);");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Username is Empty!");
			alert.showAndWait();
			passTextbox.setStyle("");
			combo_Role.setStyle("");
			return "Username is Empty!";
		} 
		else if (credentials.get(1) == null) {
			passTextbox.setStyle("-fx-background-color: rgb(255, 74, 74);;");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Password is Empty!");
			alert.showAndWait();
			emailTextbox.setStyle("");
			combo_Role.setStyle("");
			return "Password is Empty!";
		} 
		else if (credentials.get(2) == null) {
			combo_Role.setStyle("-fx-background-color: rgb(255, 74, 74);;");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Role not selected!");
			alert.showAndWait();
			passTextbox.setStyle("");
			emailTextbox.setStyle("");
			return "Role not selected!";
		}

		// Verifies students credintials from database
		if( varifyCredentials(credentials.get(0), credentials.get(1), credentials.get(2)) ) {
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
			return "Success User Found!";
		}
		else {
			return "user not found!";
		}
	}

	/**
	 * Method for getting the data that the user entered in login screen.
	 */
	public ArrayList<String> getLoginCredentials() {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add(emailTextbox.getText());
		credentials.add(passTextbox.getText());
		credentials.add(combo_Role.getValue());
		return credentials;
	}

	/**
	 * Method to extract the dependency of the database to check the credentials of
	 * the user.
	 * 
	 * @param username
	 * @param password
	 * @param selectedRole
	 */
	public boolean varifyCredentials(String username, String password, String selectedRole) {
		
		ClientUI.chat.loginVarification(username, password, selectedRole);
		// Waits 5 seconds for user to be found
		int cap = 20;
		while (!ClientHandler.user.getIsFound() && (cap > 0)) {
			try {
				Thread.sleep(250);
				cap--;
			} catch (InterruptedException e) { }
		}

		if (!ClientHandler.user.getIsFound()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("user not found!");
			alert.showAndWait();
			return false;
		}
		return true;
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
