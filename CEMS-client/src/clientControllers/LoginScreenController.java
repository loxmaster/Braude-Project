package clientControllers;

import java.io.IOException;

import clientHandlers.ClientUI;
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
import javafx.scene.layout.AnchorPane;
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
			// verify students credintials
			Thread t = new Thread(new Runnable() {
				public void run() {
					ClientUI.chat.loginVarification(email, password);
				}
			});
			//
			t.start();
			try {
				Thread.sleep(1000);
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//

			if (!ClientHandler.user.getIsFound()) {
				setVisibleFalse();
				System.out.println("user not found! (loginscreencontroller)");
			} else {
				// if found loading the student screen
				switch (ClientHandler.user.getType()) {
					case "student": {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage currentStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						AnchorPane root = loader.load(getClass().getResource("/clientFXMLS/StudentScreen.fxml").openStream());
						StudentScreenController ssc = loader.getController();
						ssc.loadStudent(ClientHandler.user);
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
						System.out.println("Opening Student screen...");
						currentStage.setTitle("CEMS System - Student");
						currentStage.setScene(scene);
						currentStage.show();
						break;
					}

					case "lecturer": {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage currentStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						AnchorPane root = loader.load(getClass().getResource("/clientFXMLS/Lecturer1.fxml").openStream());
						LecturerController lc = loader.getController();
						lc.loadLecturer(ClientHandler.user);
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
						System.out.println("Opening Lecturer screen...");
						currentStage.setTitle("CEMS System - Lecturer");
						currentStage.setScene(scene);
						currentStage.show();
						break;
					}

					case "hod": {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage currentStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						AnchorPane root = loader.load(getClass().getResource("/clientFXMLS/HOD.fxml").openStream());
						// HODController hoc = loader.getController();
						// hoc.loadHOD(ClientHandler.user);
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
						System.out.println("Opening HOD screen...");
						currentStage.setTitle("CEMS System - Head Of The Department");
						currentStage.setScene(scene);
						currentStage.show();
						break;
					}

					default: {
						setVisibleFalse();
						System.out.println("some is wrong with database");
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
