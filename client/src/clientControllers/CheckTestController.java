package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CheckTestController extends BasicController{

    @FXML
    private TableView<?> table;

    
    @FXML
    void logoutPressed(ActionEvent event) {
		// open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
		currentStage.setTitle("CEMS System - Lecturer");
	}
}
