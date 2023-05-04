package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CodeForTestPreformController {

	@FXML
    void BackPressed(ActionEvent event) {
		// Loading student main screen from existing stage
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
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

    @FXML
    void LogOutPressed(ActionEvent event) {
    	
    }

    @FXML
    void SubmitPressed(ActionEvent event) {

    }

}
