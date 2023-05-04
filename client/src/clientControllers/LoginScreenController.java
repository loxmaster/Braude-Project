package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenController {

	@FXML
	private Button loginButton;

	@FXML
	private Label wrongLabel;

	@FXML
	void pressedLogin(ActionEvent event) {
		// Opens student screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane newScene = null;
		try {
			newScene = FXMLLoader.load(getClass().getResource("/clientFXMLS/StudentScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(newScene);
		currentStage.setTitle("CEMS System - Student");
		currentStage.setScene(scene);
	}

	void setVisibleFalse() {
		wrongLabel.setVisible(false);
	}
}
