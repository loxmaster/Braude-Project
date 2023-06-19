package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import logic.Test;
import logic.User;

@SuppressWarnings("unused")

public class HODController extends BasicController {
	private User  hod;
	public static ArrayList<Test> ongoingTests_permissions;

	
	public User getHod() {
		return hod;
	}

	public void setHod(User hod) {
		this.hod = hod;
	}
	
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
		HODPermissionController controller = new HODPermissionController();
		HODPermissionController lsc = (HODPermissionController) openScreen2("/clientFXMLS/HodPermission.fxml", "CEMS System - HOD - permissions", event,controller);
		lsc.load();
	}
	
	//get the ongoing tests permissions list
	public static ArrayList<Test> getOngoingTests_permissions() {
		return ongoingTests_permissions;
	}

	//set the ongoing tests permissions list

	public static void setOngoingTests_permissions(ArrayList<Test> ongoingTests_permissions) {
		HODController.ongoingTests_permissions = ongoingTests_permissions;
	}
	
	public static void addTestTo_ongoingTests_permissions(Test ongoingTest_permissions) {
		if(ongoingTests_permissions==null)
		 ongoingTests_permissions = new ArrayList<Test>();
		ongoingTests_permissions.add(ongoingTest_permissions);
	}

	//fetch ongoingTests_permissions_list from DB
	public static void fetch_ongoingTests_permissions_list() {
		ClientUI.chat.fetch_ongoingTests_permissions_list();		
	}

}
