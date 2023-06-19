package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.User;

@SuppressWarnings("unused")

public class HODController extends BasicController {
	private User hod;

	@FXML
	private Button Logout;

	@FXML
	private Button exitbutton;

	@FXML
	private Label live_time;

	@FXML
	private Button logo;

	@FXML
	private Label screen_label;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	public void loadHOD(User user) {
		hod = user;
		screen_label.setText("Welcome back, " + hod.getUsername() + " !");
	}

	@FXML
	void HodStatisticalPressed(ActionEvent event) {
		// open Statistical screen screen from existing stage
		openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
	}

	@FXML
	void permissionsPressed(ActionEvent event) {
		// open Permissions screen from existing stage
		openScreen("/clientFXMLS/HodPermission.fxml", "CEMS System - Head Of Department - Permissions", event);
	}

}
