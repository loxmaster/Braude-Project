package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class LecturerStatisticalController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	void LogOutPressed(ActionEvent event) {
		// open Login screen 
		logoutPressed(event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// Open lecturer screen 
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}

}
