package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HODStatisticalController extends BasicController {

	@FXML
	void backPressed(ActionEvent event) {
		// open HOD screen from existing stage
		openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void infoOnLecturerPressed(ActionEvent event) {
		// open Student Statistic screen 
		openScreen("/clientFXMLS/HODStatisticOnLecturer.fxml", "CEMS System - Head Of Department - Statistics On Lecturer", event);
	}

	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		// open Student Statistic screen 
		openScreen("/clientFXMLS/HODStatisticOnStudent.fxml", "CEMS System - Head Of Department - Statistics On Student", event);
	}

	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		// open Subject Statistic screen 
		openScreen("/clientFXMLS/HODStatisticOnSubject.fxml", "CEMS System - Head Of Department - Statistics On Subject", event);
		
	}

	@FXML
	void specialReportPressed(ActionEvent event) {

	}

}
