package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;

public class OngoingTestController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	void LogOutPressed(ActionEvent event) {
		logoutPressed(event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen from existing stage
		((Node) event.getSource()).getScene().getWindow().hide();
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}

}
