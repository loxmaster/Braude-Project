package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentExamController extends BasicController {

    @FXML
    void submitPressed(ActionEvent event) {
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }
}
