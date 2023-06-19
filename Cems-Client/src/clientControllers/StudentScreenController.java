package clientControllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import logic.User;

// Suppressing warnings about unused imports or fields
@SuppressWarnings("unused")

/**
 * 
 * Controller class for the StudentScreen view.
 * 
 * Extends the BasicController class.
 */
public class StudentScreenController extends BasicController {

    private User student; // The User object representing the student who is currently logged in

    @FXML
    private Button Logout; // Button for logging out

    @FXML
    private Button exitbutton; // Button for exiting

    @FXML
    private Button logo; // Button for displaying logo

    @FXML
    private Label screen_label; // Label for displaying the screen message

    @FXML
    private Label live_time; // Label for displaying live time

    /**
     * 
     * Initializes the StudentScreenController.
     * Starts the clock by calling the Timenow() method with the live_time label.
     */
    @FXML
    void initialize() {
        Timenow(live_time);
    }

    /**
     * 
     * Loads the student data.
     * This method is called when the StudentScreen view is loaded.
     * 
     * @param student The User object representing the logged-in student.
     */
    public void loadStudent(User student) {
        this.student = student;
        screen_label.setText("Welcome back, " + student.getUsername() + "!");
    }

    /**
     * 
     * Event handler for the Log Out button press.
     * Logs out the student and closes the StudentScreen view.
     * 
     * @param event The action event triggered by the user.
     */
    @FXML
    void LogOutPressed(ActionEvent event) {
        logoutPressed(event);
    }

    /**
     * 
     * Event handler for the Open Code Prompt button press.
     * Opens the EnterCodeForTestPreforming view.
     * 
     * @param event The action event triggered by the user.
     */
    @FXML
    void OpenCodePrompt(ActionEvent event) {
        openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS System - Student - Enter Test Code", event);
    }

    /**
     * 
     * Event handler for the Show Grades button press.
     * Opens the ViewGrades view.
     * 
     * @param event The action event triggered by the user.
     */
    @FXML
    void showGrades(ActionEvent event) {
        ViewGradesController vgc = (ViewGradesController) openScreen("/clientFXMLS/ViewGrades.fxml",
                "CEMS System - Student Grades", event);
        vgc.initialize();
    }
}
