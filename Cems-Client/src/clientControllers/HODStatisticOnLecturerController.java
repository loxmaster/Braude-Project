package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class HODStatisticOnLecturerController extends BasicController {

    @FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private TableView<?> table;

    @FXML
    void backPressed(ActionEvent event) {
        // open Statistical screen 
        openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
    }

}
