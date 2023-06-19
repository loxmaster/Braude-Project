package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HODStatisticalController extends BasicController {

	@FXML
	private Button exitbutton;

	@FXML
	private Label live_time;

	@FXML
	private Button logo;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open HOD screen from existing stage
		openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
	}

	@FXML
	void infoOnLecturerPressed(ActionEvent event) {
		// open Student Statistic screen
		HODStatisticOnLecturerController Hlsc = (HODStatisticOnLecturerController)openScreen("/clientFXMLS/HODStatisticOnLecturer.fxml",
				"CEMS System - Head Of Department - Statistics On Lecturer", event);
				Hlsc.load();
	}

	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		// open Student Statistic screen
		HODStatisticOnStudentController Hssc = (HODStatisticOnStudentController)openScreen("/clientFXMLS/HODStatisticOnStudent.fxml",
				"CEMS System - Head Of Department - Statistics On Student", event);
		Hssc.load();
	}

	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		// open Subject Statistic screen
		HODStatisticOnCourseController Hcsc = (HODStatisticOnCourseController) openScreen("/clientFXMLS/HODStatisticOnCourses.fxml",
				"CEMS System - Head Of Department - Statistics On Subject", event);
		Hcsc.load();
	}

	@FXML
	void specialReportPressed(ActionEvent event) {

	}

}
