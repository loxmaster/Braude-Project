package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewGradesController {

    @FXML
    private ToggleButton backButton;

    @FXML
    void LogOutPressed(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
    	VBox vbox = null;
    	try {
    		vbox = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Scene scene = new Scene(vbox);
    	currentStage.setTitle("CEMS System - Login");
    	currentStage.setScene(scene);
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
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

}