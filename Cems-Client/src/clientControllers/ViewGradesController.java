package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import clientHandlers.ClientUI;
import logic.Test;
import javafx.event.EventHandler;

// This class is a controller for the ViewGrades view
public class ViewGradesController extends BasicController {

    // The list of completed tests
    public static ArrayList<Test> completedTestsList;

    // The list of subjects and courses
    private static ArrayList<String> SubjectCourse;

    // A flag variable
    public static boolean flag = false;

    // The value selected in the filterComboBox
    public String value;

    // The container for the exams
    @FXML
    private GridPane ExamContainer;

    // The text field for the student ID
    @FXML
    private Text Student_ID_Text;

    // The back button
    @FXML
    private Button backButton;

    // The combo box for filtering
    @FXML
    private ComboBox<String> filterComboBox;

    // The exit button
    @FXML
    private Button exitbutton;

    // The filter button
    @FXML
    private Button filterButton;

    @FXML
    private Label live_time;

    // This method is called when the back button is pressed. It loads the student
    // main screen.
    @FXML
    void backButtonPressed(ActionEvent event) {
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }

    // This method is called when the ViewGrades view is loaded. It initializes the
    // view.
    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
        // Get the list of completed tests for the student
        ClientUI.chat.getcompletedTestsForStudentList();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set the student ID text
        Student_ID_Text.setText("Student ID: " + completedTestsList.get(0).getStudentID());

        // Populate the ComboBox with course names
        Set<String> courseNames = new HashSet<>();
        for (Test test : completedTestsList) {
            ClientUI.chat.getCourseForTest(test.getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String courseName = SubjectCourse.get(2);
            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }

        // Add the "Show all" option to the ComboBox
        courseNames.add("Show all");
        ExamLoad("Show all");
        filterComboBox.getItems().addAll(FXCollections.observableArrayList(courseNames));

        // Set the action to be performed when an option is selected in the ComboBox
        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println((String) filterComboBox.getValue());
                ExamLoad((String) filterComboBox.getValue());
            }
        });
    }

    public void ExamLoad(String value) {
        // Clear the ExamContainer. This is necessary to ensure that the previous exams
        // are not shown.
        ExamContainer.getChildren().clear();

        // These variables are used to determine the position of the exam card in the
        // grid.
        int column = 0;
        int row = 1;
        try {
            // Loop through each test in the completed tests list.
            for (Test test : completedTestsList) {
                // Get the course for the test.
                ClientUI.chat.getCourseForTest(test.getId());
                try {
                    // Sleep for a short period to allow the course to be fetched.
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // If the value is "Show all", then all exams should be shown.
                if (value.equals("Show all")) {
                    // Create a new FXMLLoader.
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    // Get the URL for the StudentTestCard.fxml file.
                    URL url = getClass().getResource("/clientFXMLS/StudentTestCard.fxml");
                    if (url == null) {
                        System.out.println("Resource not found. Exiting...");
                        return;
                    }
                    // Load the StudentTestCard.fxml file.
                    AnchorPane testcard = fxmlLoader.load(url.openStream());
                    // Get the controller for the test card.
                    TestCardController cardController = fxmlLoader.getController();
                    // Set the test and subject course for the test card.
                    cardController.setCard(test, SubjectCourse);
                    // If the column is 4, then reset the column to 0 and increment the row.
                    if (column == 4) {
                        column = 0;
                        ++row;
                    }
                    // Add the test card to the ExamContainer.
                    ExamContainer.add(testcard, column++, row);
                    // Set the margin for the test card.
                    GridPane.setMargin(testcard, new Insets(10));
                } else if (SubjectCourse.get(2).equals(value)) {
                    // If the value is not "Show all", then only the exams for the selected course
                    // should be shown.
                    // The code in this block is the same as the code in the "Show all" block, but
                    // it only executes if the course for the test matches the selected course.
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource("/clientFXMLS/StudentTestCard.fxml");
                    if (url == null) {
                        System.out.println("Resource not found. Exiting...");
                        return;
                    }
                    AnchorPane testcard = fxmlLoader.load(url.openStream());
                    TestCardController cardController = fxmlLoader.getController();
                    cardController.setCard(test, SubjectCourse);
                    if (column == 4) {
                        column = 0;
                        ++row;
                    }
                    ExamContainer.add(testcard, column++, row);
                    GridPane.setMargin(testcard, new Insets(10));
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }

    // Getter for the list of completed tests for a student
    public static ArrayList<Test> getcompletedTestsForStudentList() {
        return completedTestsList;
    }

    // Setter for the list of completed tests for a student
    public static void setcompletedTestsForStudentList(ArrayList<Test> completedTests) {
        completedTestsList = completedTests;
    }

    // Setter for the list of subjects and courses
    public static void setSubjectsCoursesList(ArrayList<String> SubjectandCourse) {
        SubjectCourse = SubjectandCourse;
    }

    // Getter for the list of completed tests
    public static ArrayList<Test> getCompletedTestsList() {
        return completedTestsList;
    }

    // Setter for the list of completed tests
    public static void setCompletedTestsList(ArrayList<Test> completedTestsList) {
        ViewGradesController.completedTestsList = completedTestsList;
    }

    // Getter for the list of subjects and courses
    public static ArrayList<String> getSubjectCourse() {
        return SubjectCourse;
    }

    // Setter for the list of subjects and courses
    public static void setSubjectCourse(ArrayList<String> subjectCourse) {
        SubjectCourse = subjectCourse;
    }

    // Getter for the flag variable
    public static boolean isFlag() {
        return flag;
    }

    // Setter for the flag variable
    public static void setFlag(boolean flag) {
        ViewGradesController.flag = flag;
    }

    // Getter for the filter combo box
    public ComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    // Setter for the filter combo box
    public void setFilterComboBox(ComboBox<String> filterComboBox) {
        this.filterComboBox = filterComboBox;
    }

}
