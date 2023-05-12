package clientControllers;

import java.io.IOException;

import clientHandlers.StudentUI;
import clientHandlers.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController extends BasicController {

	private String email, password;

	@FXML
	private Button BtnInfo;

	@FXML
	private Label wrongLabel;

	@FXML
	private TextField emailTextbox;

	@FXML
	private PasswordField passTextbox;

	@FXML
	void pressedLogin(ActionEvent event) {
		email = emailTextbox.getText();
		password = passTextbox.getText();
		System.out.println(email + " " + password);
		if (email.trim().isEmpty() || password.trim().isEmpty())
			setVisibleFalse();
		else {
			// verify students credintials
			StudentUI.chat.acceptLogin(email, password);
			//
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if (!ClientHandler.user.getIsFound()) {
				setVisibleFalse();
				System.out.println("user not found! (loginscreencontroller)");
			} else {
				// if found loading the student screen
				switch (ClientHandler.user.getType()) {
					case "student": {
						Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						StudentScreenController ssc = new StudentScreenController();
						ssc.loadStudent(ClientHandler.user);
						System.out.println("Opening Student screen...");
						openScreen("/clientFXMLS/StudentScreen.fxml", currentStage);
						currentStage.setTitle("CEMS System - Student");
						break;
					}

					case "lecturer": {
						Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						LecturerController lsc = new LecturerController();
						lsc.loadLecturer(ClientHandler.user);
						System.out.println("Opening Lecturer screen...");
						openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
						currentStage.setTitle("CEMS System - Lecturer");
						break;
					}

					case "hod":
						Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						HODController ssc = new HODController();
						// ssc.loadHod(HodHandler.student);
						System.out.println("Opening HOD screen...");
						openScreen("/clientFXMLS/HOD.fxml", currentStage);
						currentStage.setTitle("CEMS System - Head Of The Department");
						break;
				}
			}

		}
	}

	void setVisibleFalse() {
		wrongLabel.setVisible(true);
	}

	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
		stage.setTitle("CEMS - Login");
		stage.setScene(scene);
		stage.show();
	}
}
