package clientControllers;

import java.util.ArrayList;
import clientHandlers.ClientUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Test;

public class HODPermissionController extends BasicController {

	@FXML
	private TableView<Test> table;

	@FXML
	private TableColumn<Test, String> Subject;

	@FXML
	private TableColumn<Test, String> TimeToAdd;

	@FXML
	private TableColumn<Test, String> Reason;

	@FXML
	private TableColumn<Test, Button> Approve;

	@FXML
	private TableColumn<Test, Button> Deny;
	@FXML
	private Button backBTN;
	
public void load() {

	System.out.println("load function of hodPermission Controller");
    //fetch ongoingTestsPermissions from DB
    HODController.fetch_ongoingTests_permissions_list();
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    //get the ongoingTestsPermissions that returned from the DB
    ArrayList<Test> tests = HODController.getOngoingTests_permissions();
    //clear the table before set new values
    table.getItems().clear();
	table.refresh();
	// Set cell value factories for the cols of the table
    Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    TimeToAdd.setCellValueFactory(new PropertyValueFactory<>("timeToAdd"));
    Reason.setCellValueFactory(new PropertyValueFactory<>("ReasonForTimeExtension"));
    // Set cell value factories for the Approve and Deny columns to create the buttons
    Approve.setCellValueFactory(cellData -> new SimpleObjectProperty<Button>(createButtons(cellData.getValue(), "Approve")));
    Deny.setCellValueFactory(cellData -> new SimpleObjectProperty<Button>(createButtons(cellData.getValue(), "Deny")));


    if (tests != null) {
        ObservableList<Test> testList = FXCollections.observableArrayList(tests);

        table.setItems(testList);
    }
}


public Button createButtons(Test test, String btnName) {
    Button btn = new Button(btnName);
    btn.setUserData(test);  // associate the button with the corresponding Test object

    btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	System.out.println("Approve button clicked");
            // Retrieve the Test object associated with the clicked button
            Test clickedTest = (Test) ((Button) event.getSource()).getUserData();
            //delete the clicked test from the permissionsTable in db
        	ClientUI.chat.Update_HOD_permissionsTable_inDB(clickedTest);
        	System.out.println("test deleted from the db");
            // Remove the Test object from the TableView's items
            table.getItems().remove(clickedTest);
        	System.out.println("items removed from the table");
        	HODController.ongoingTests_permissions.clear();
        	System.out.println("items cleared");
            // Refresh the TableView
            table.refresh();
        	System.out.println("table refreshed");
        }
    });

    return btn;
}

	@FXML
	void backPressed(ActionEvent event) {
		// ClientUI.chat.GetOngoingTests();
		
		
		HODController hd = (HODController) openScreen("/clientFXMLS/HOD.fxml", "CEMS System - HOD -test time permissions ",event);
	}
}
