package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HODStatisticalController extends BasicController {

    // Define UI elements
    @FXML
    private Button exitbutton;

    @FXML
    private Label live_time;

    @FXML
    private Button logo;

    /**
     * Initializes the controller.
     * Starts the clock on the UI.
     */
    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

    /**
     * Event handler for the Back button press.
     * Navigates the user back to the HOD screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void backPressed(ActionEvent event) {
        // open HOD screen from existing stage
        openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Head Of Department", event);
    }

    /**
     * Event handler for the "Info On Lecturer" button press.
     * Navigates the user to the statistics on lecturer screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void infoOnLecturerPressed(ActionEvent event) {
        // open Statistics On Lecturer screen
        HODStatisticOnLecturerController Hlsc = (HODStatisticOnLecturerController) openScreen("/clientFXMLS/HODStatisticOnLecturer.fxml",
                "CEMS System - Head Of Department - Statistics On Lecturer", event);
        Hlsc.load();
    }

    /**
     * Event handler for the "Info On Student" button press.
     * Navigates the user to the statistics on student screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void infoOnStudentPressed(ActionEvent event) {
        // open Statistics On Student screen
        HODStatisticOnStudentController Hssc = (HODStatisticOnStudentController) openScreen("/clientFXMLS/HODStatisticOnStudent.fxml",
                "CEMS System - Head Of Department - Statistics On Student", event);
        Hssc.load();
    }

    /**
     * Event handler for the "Info On Subject" button press.
     * Navigates the user to the statistics on subject screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void infoOnSubjectPressed(ActionEvent event) {
        // open Statistics On Subject screen
        HODStatisticOnCourseController Hcsc = (HODStatisticOnCourseController) openScreen("/clientFXMLS/HODStatisticOnCourses.fxml",
                "CEMS System - Head Of Department - Statistics On Subject", event);
        Hcsc.load();
    }

    /**
     * Event handler for the "Special Report" button press.
     * Currently empty. Needs to be implemented.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void specialReportPressed(ActionEvent event) {
        // Add your code here for handling the Special Report button press
    }
}


