package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class CheckTestController extends BasicController {

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private TableView<?> table;

    @FXML
	  private Label live_time;

	  @FXML
	  void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    @FXML
    void backPressed(ActionEvent event) {
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
    }

    @FXML
    void backToLecturer(ActionEvent event) {
		openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
    }


}
