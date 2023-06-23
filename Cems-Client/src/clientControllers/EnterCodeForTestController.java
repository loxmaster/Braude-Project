package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The EnterCodeForTestController class handles the logic for entering the code for a test by a student.
 * It extends from the BasicController class.
 */
public class EnterCodeForTestController extends BasicController {

	@FXML
	private Label live_time;

	@FXML
	private TextField code;

	/**
	 * Initializes the controller.
	 * It starts the clock on the UI.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	/**
	 * Handles the event when the "Back" button is pressed.
	 * It opens the student screen.
	 *
	 * @param event The action event triggered by the button press.
	 */
	@FXML
	void BackPressed(ActionEvent event) {
		// Opens student screen
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
	}

	/**
	 * Handles the event when the "Submit" button is pressed.
	 * It opens the "EnterCodeForTestPreforming" screen.
	 *
	 * @param event The action event triggered by the button press.
	 */
	@FXML
	void SubmitPressed(ActionEvent event) {
		// Opens Student Give ID screen
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS - Enter ID For Exam", event);
	}

}
