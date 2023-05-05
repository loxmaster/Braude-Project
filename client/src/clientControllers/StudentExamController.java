package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

public class StudentExamController extends BasicController{

    @FXML
    void submitPressed(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openScreen("StudentScreen.fxml", currentStage);
        currentStage.setTitle("CEMS System - Student");
    }

}
