package clientControllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import logic.User;

@SuppressWarnings("unused")

public class StudentScreenController extends BasicController {

	private User student;

	@FXML
	private TextArea helpLabel;

	@FXML
	private Label live_time;

	public void loadStudent(User student) {
		this.student = student;
		Timenow(live_time);
	}

	@FXML
	void LogOutPressed(ActionEvent event) {
		logoutPressed(event);
	}

	@FXML
	void OpenCodePrompt(ActionEvent event) {
		// Opening window for code entering
		openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS System - Student - Enter Test Code", event);
	}

	@FXML
	void showGrades(ActionEvent event) {
		// Opening Show Grades screen
		ViewGradesController vgc = (ViewGradesController) openScreen("/clientFXMLS/ViewGrades.fxml",
				"CEMS System - Student Grades", event);
		vgc.ExamLoad();
	}

}
