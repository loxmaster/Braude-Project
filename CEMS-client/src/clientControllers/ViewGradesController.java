package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class ViewGradesController extends BasicController {

    @FXML
    private ToggleButton backButton;

    @FXML
    void LogOutPressed(ActionEvent event) {
        logoutPressed(event);
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen 
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }

}