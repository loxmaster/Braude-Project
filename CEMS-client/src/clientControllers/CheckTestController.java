package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CheckTestController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	void LogoutPressed(ActionEvent event) {
		logoutPressed(event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen from existing stage
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}
}
