package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HODController extends BasicController {

	@FXML
	void HodStatisticalPressed(ActionEvent event) {
		// open Statistical screen screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HodStatisticScrene.fxml", currentStage);
		currentStage.setTitle("CEMS System - Head Of Department - Statistics");
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void logoutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void permissionsPressed(ActionEvent event) {
		// open Permissions screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HodPermission.fxml", currentStage);
		currentStage.setTitle("CEMS System - Head Of Department - Permissions");
	}

}
