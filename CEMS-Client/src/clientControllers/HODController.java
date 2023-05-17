package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import logic.User;

@SuppressWarnings("unused")

public class HODController extends BasicController {
	private User  hod;
	
	public void loadHOD(User user) {
		hod = user;
    }

	@FXML
	void HodStatisticalPressed(ActionEvent event) {
		// open Statistical screen screen from existing stage
		openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void permissionsPressed(ActionEvent event) {
		// open Permissions screen from existing stage
		openScreen("/clientFXMLS/HodPermission.fxml", "CEMS System - Head Of Department - Permissions", event);
	}

}
