package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class HODPermissionController extends BasicController {

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open HOD screen from existing stage
        openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
    }

}
