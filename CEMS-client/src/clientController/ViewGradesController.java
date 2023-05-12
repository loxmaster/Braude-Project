package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class ViewGradesController extends BasicController {

    @FXML
    private ToggleButton backButton;

    @FXML
    void LogOutPressed(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
        currentStage.setTitle("CEMS System - Login");
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen from existing stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openScreen("/clientFXMLS/StudentScreen.fxml", currentStage);
        currentStage.setTitle("CEMS System - Student");
    }

}