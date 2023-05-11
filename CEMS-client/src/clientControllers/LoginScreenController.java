package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginScreenController extends BasicController {

	@FXML
	private Button BtnInfo;

	@FXML
	private Label wrongLabel;

	@FXML
	void pressedLogin(ActionEvent event) {
		// Opens student screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/StudentScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Student");
	}

	void setVisibleFalse() {
		wrongLabel.setVisible(false);
	}
}
