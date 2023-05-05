package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CodeForTestPreformController extends BasicController{

	@FXML
    void BackPressed(ActionEvent event) {
		// Loading student main screen from existing stage
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    	openScreen("/clientFXMLS/StudentScreen.fxml", currentStage);
    	currentStage.setTitle("CEMS System - Student");
    }

    @FXML
    void LogOutPressed(ActionEvent event) {
    	// Loading Login screen from existing stage
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    	openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
    	currentStage.setTitle("CEMS System - Student");
    }

    @FXML
    void SubmitPressed(ActionEvent event) {
		// goes to test
    }

}
