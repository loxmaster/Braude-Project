package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This controller handles the display of statistical information for the Head of Department (HOD).
 * It extends from the BasicController class.
 */
public class HODStatisticalController extends BasicController {

    // Define UI elements
	@FXML
	private Button exitbutton;

	@FXML
	private Label live_time;

	@FXML
	private Button logo;

    /**
     * This function initializes the controller.
     * It starts the clock on the UI.
     */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    /**
     * This function handles the action of clicking the "Back" button.
     * It navigates the user back to the HOD screen.
     */
	@FXML
	void backPressed(ActionEvent event) {
		// open HOD screen from existing stage
		openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
	}

    /**
     * This function handles the action of clicking the "Info On Lecturer" button.
     * It navigates the user to the statistics on lecturer screen.
     */
	@FXML
	void infoOnLecturerPressed(ActionEvent event) {
		// open Student Statistic screen
		HODStatisticOnLecturerController Hlsc = (HODStatisticOnLecturerController)openScreen("/clientFXMLS/HODStatisticOnLecturer.fxml",
				"CEMS System - Head Of Department - Statistics On Lecturer", event);
				Hlsc.load();
	}

    /**
     * This function handles the action of clicking the "Info On Student" button.
     * It navigates the user to the statistics on student screen.
     */
	@FXML
	void infoOnStudentPressed(ActionEvent event) {
		// open Student Statistic screen
		HODStatisticOnStudentController Hssc = (HODStatisticOnStudentController)openScreen("/clientFXMLS/HODStatisticOnStudent.fxml",
				"CEMS System - Head Of Department - Statistics On Student", event);
		Hssc.load();
	}

    /**
     * This function handles the action of clicking the "Info On Subject" button.
     * It navigates the user to the statistics on subject screen.
     */
	@FXML
	void infoOnSubjectPressed(ActionEvent event) {
		// open Subject Statistic screen
		HODStatisticOnCourseController Hcsc = (HODStatisticOnCourseController) openScreen("/clientFXMLS/HODStatisticOnCourses.fxml",
				"CEMS System - Head Of Department - Statistics On Subject", event);
		Hcsc.load();
	}

    /**
     * This function handles the action of clicking the "Special Report" button.
     * Currently, the function body is empty and needs to be implemented.
     */
	@FXML
	void specialReportPressed(ActionEvent event) {

	}

}
