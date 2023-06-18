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

// This class is a controller for the StudentScreen view
public class StudentScreenController extends BasicController {

    // The User object representing the student who is currently logged in
    private User student;

    @FXML
    private Button Logout;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private Label screen_label;

    @FXML
    private Label live_time;

    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

    // This method is called when the StudentScreen view is loaded. It initializes
    // the student field and starts the clock.
    public void loadStudent(User student) {
        // Store the User object representing the logged-in student
        this.student = student;
        screen_label.setText("Welcome back," + student.getUsername() + " !");
    }

    // This method is called when the Log Out button is pressed. It logs the student
    // out and closes the StudentScreen view.
    @FXML
    void LogOutPressed(ActionEvent event) {
        logoutPressed(event);
    }

    // This method is called when the Open Code Prompt button is pressed. It opens
    // the EnterCodeForTestPreforming view.
    @FXML
    void OpenCodePrompt(ActionEvent event) {
        // Open the EnterCodeForTestPreforming view
        openScreen("/clientFXMLS/EnterCodeForTestPreforming.fxml", "CEMS System - Student - Enter Test Code", event);
    }

    // This method is called when the Show Grades button is pressed. It opens the
    // ViewGrades view.
    @FXML
    void showGrades(ActionEvent event) {
        // Open the ViewGrades view
        ViewGradesController vgc = (ViewGradesController) openScreen("/clientFXMLS/ViewGrades.fxml",
                "CEMS System - Student Grades", event);
    }
}
