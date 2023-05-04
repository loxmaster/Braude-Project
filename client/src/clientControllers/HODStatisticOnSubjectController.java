package clientControllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class HODStatisticOnSubjectController {

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open Statistical screen screen from existing stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/clientFXMLS/HodStatisticScrene.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		currentStage.setTitle("CEMS System - Head Of Department - Statistics");
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
