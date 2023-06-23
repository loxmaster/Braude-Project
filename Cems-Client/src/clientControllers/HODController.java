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

/**
 * 
 * This controller handles the functionality for the Head of Department (HOD).
 * 
 * It extends from the BasicController class.
 */
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

	/**
	 * 
	 * This function initializes the controller.
	 * It starts the clock on the UI.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * 
	 * This function loads the HOD user.
	 * 
	 * @param user The HOD user.
	 */
	public void loadHOD(User user) {
		hod = user;
		screen_label.setText("Welcome back, " + hod.getUsername() + " !");
	}

	/**
	 * 
	 * This function handles the action of clicking the "Statistical" button.
	 * It navigates the user to the statistical information screen.
	 * 
	 * @param event The ActionEvent triggered by the button press.
	 */
	@FXML
	void HodStatisticalPressed(ActionEvent event) {
		// open Statistical screen screen from existing stage
		openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
	}

	/**
	 * 
	 * /**
	 * 
	 * This function handles the action of clicking the "Permissions" button.
	 * It navigates the user to the permissions screen.
	 * 
	 * @param event The ActionEvent triggered by the button press.
	 */
	@FXML
	void permissionsPressed(ActionEvent event) {
		// open Permissions screen from existing stage
		HODPermissionController lsc = (HODPermissionController) openScreen("/clientFXMLS/HodPermission.fxml",
				"CEMS System - HOD - permissions", event);
		lsc.load();
	}

	/**
	 * 
	 * This function gets the ongoing tests permissions list.
	 * 
	 * @return The ongoing tests permissions list.
	 */
	public static ArrayList<Test> getOngoingTests_permissions() {
		return ongoingTests_permissions;
	}

	/**
	 * 
	 * This function sets the ongoing tests permissions list.
	 * 
	 * @param ongoingTestsPermissions The ongoing tests permissions list to set.
	 */
	public static void setOngoingTests_permissions(ArrayList<Test> ongoingTests_permissions) {
		HODController.ongoingTests_permissions = ongoingTests_permissions;
	}

	/**
	 * 
	 * This function adds a test to the ongoing tests permissions list.
	 * 
	 * @param ongoingTestPermissions The test to add.
	 */
	public static void addTestTo_ongoingTests_permissions(Test ongoingTest_permissions) {
		if (ongoingTests_permissions == null)
			ongoingTests_permissions = new ArrayList<Test>();
		ongoingTests_permissions.add(ongoingTest_permissions);
	}

	/**
	 * 
	 * This function fetches the ongoing tests permissions list from the database.
	 */
	public static void fetch_ongoingTests_permissions_list() {
		ClientUI.chat.fetch_ongoingTests_permissions_list();
	}

	/**
	 * 
	 * This function gets the HOD user.
	 * 
	 * @return The HOD user.
	 */
	public User getHod() {
		return hod;
	}

	/**
	 * 
	 * This function sets the HOD user.
	 * 
	 * @param hod The HOD user to set.
	 */
	public void setHod(User hod) {
		this.hod = hod;
	}

}
