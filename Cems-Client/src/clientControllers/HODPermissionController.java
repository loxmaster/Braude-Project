package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;


public class HODPermissionController extends BasicController {

    @FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
    @FXML
    
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open HOD screen from existing stage
        openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
    }

}
