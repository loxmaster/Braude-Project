package clientControllers;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
// idk
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;


public class HODPermissionController {

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open HOD screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HOD.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Head Of Department");
		currentStage.setScene(scene);
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        // open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/LoginScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Login");
		currentStage.setScene(scene);
    }

}
