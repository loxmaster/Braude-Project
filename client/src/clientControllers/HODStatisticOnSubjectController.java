package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class HODStatisticOnSubjectController extends BasicController{

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open Statistical screen screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/HodStatisticScrene.fxml", currentStage);
		currentStage.setTitle("CEMS System - Head Of Department - Statistics");
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        // open Login screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
		currentStage.setTitle("CEMS System - Login");
    }

}
