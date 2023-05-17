package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import logic.User;

@SuppressWarnings("unused")

public class StudentScreenController extends BasicController {
	
	private User student;

	@FXML
	private TextArea helpLabel;

	public void loadStudent(User student) {
		this.student = student;
	}

	@FXML
	void LogOutPressed(ActionEvent event) {
		logoutPressed(event);
	}

	@FXML
	void HelpPressed(ActionEvent event) {
		if (helpLabel.isVisible())
			helpLabel.setVisible(false);
		else
			helpLabel.setVisible(true);
	}

	@FXML
	void OpenCodePrompt(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS System - Student - Enter Test Code", event);
	}

	@FXML
	void showGrades(ActionEvent event) {
		// Opening Show Grades screen
		openScreen("/clientFXMLS/ViewGrades.fxml", "CEMS System - Student Grades", event);
	}
}
