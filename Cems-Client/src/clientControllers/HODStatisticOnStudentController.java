package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class HODStatisticOnStudentController extends BasicController {

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open Statistical screen screen 
        openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
    }

    public void load() {
        
    }



}
