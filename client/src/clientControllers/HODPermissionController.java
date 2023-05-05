package clientControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
// idk
import javafx.stage.Stage;
import javafx.scene.Node;


public class HODPermissionController extends BasicController{

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open HOD screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HOD.fxml", currentStage);
		currentStage.setTitle("CEMS System - Head Of Department");
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        // open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
    }

}
