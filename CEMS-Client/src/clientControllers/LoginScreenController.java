package clientControllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import clientHandlers.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	void pressedLogin(ActionEvent event) throws IOException {
		email = emailTextbox.getText();
		password = passTextbox.getText();
		System.out.println(email + " " + password);
		if (email.trim().isEmpty() || password.trim().isEmpty())
			setVisibleFalse();
		else {
			// Creates new thread to verify students credintials from db
			Thread t = new Thread(new Runnable() {
				public void run() {
					ClientUI.chat.loginVarification(email, password);
				}
			});
			
			t.start();
			try {
				Thread.sleep(1000);
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (!ClientHandler.user.getIsFound()) {
				setVisibleFalse();
				System.out.println("user not found! (loginscreencontroller)");
			} else {
				// if found loading the student screen
				switch (ClientHandler.user.getType()) {
					case "student": {
						StudentScreenController ssc;
            			ssc = (StudentScreenController) openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
						ssc.loadStudent(ClientHandler.user);
						System.out.println("Opening Student screen...");
						break;
					}

					case "lecturer": {
						LecturerController lc;
            			lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
						lc.loadLecturer(ClientHandler.user);
						System.out.println("Opening Lecturer screen...");
						break;
					}

					case "hod": {
						HODController hoc;
            			hoc = (HODController) openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of The Department", event);
						hoc.loadHOD(ClientHandler.user);
						break;
					}

					default: {
						JOptionPane.showMessageDialog(null, "Something wrong with database !", "Error", JOptionPane.ERROR_MESSAGE);
					}

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
