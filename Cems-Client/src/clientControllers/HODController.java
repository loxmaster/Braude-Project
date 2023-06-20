package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Test;
import logic.User;

@SuppressWarnings("unused")

public class HODController extends BasicController {

	private User hod;

	public static ArrayList<Test> ongoingTests_permissions;

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
		HODPermissionController lsc = (HODPermissionController) openScreen("/clientFXMLS/HodPermission.fxml",
				"CEMS System - HOD - permissions", event);
		lsc.load();
	}

	// get the ongoing tests permissions list
	public static ArrayList<Test> getOngoingTests_permissions() {
		return ongoingTests_permissions;
	}

	// set the ongoing tests permissions list

	public static void setOngoingTests_permissions(ArrayList<Test> ongoingTests_permissions) {
		HODController.ongoingTests_permissions = ongoingTests_permissions;
	}

	public static void addTestTo_ongoingTests_permissions(Test ongoingTest_permissions) {
		if (ongoingTests_permissions == null)
			ongoingTests_permissions = new ArrayList<Test>();
		ongoingTests_permissions.add(ongoingTest_permissions);
	}

	// fetch ongoingTests_permissions_list from DB
	public static void fetch_ongoingTests_permissions_list() {
		ClientUI.chat.fetch_ongoingTests_permissions_list();
	}

	public User getHod() {
		return hod;
	}

	public void setHod(User hod) {
		this.hod = hod;
	}

}
