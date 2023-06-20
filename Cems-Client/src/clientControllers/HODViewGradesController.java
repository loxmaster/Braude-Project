package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import logic.Statistics;
import logic.Test;

/**
 * 
 * Controller class for the HODViewGrades view.
 * Extends the BasicController class.
 */
public class HODViewGradesController extends BasicController {

    // The list of completed tests
    public static ArrayList<Test> completedTestsList;

    // The list of subjects and courses
    private static ArrayList<String> SubjectCourse;

    // A flag variable
    public static boolean flag = false;

    // The value selected in the filterComboBox
    public String value;

    @FXML
    private Text Avg_text;

    @FXML
    private GridPane ExamContainer;

    @FXML
    private Text ID_Text;

    @FXML
    private Text Name_text;

    @FXML
    private AnchorPane View_Grades_root;

    @FXML
    private Button backButton;

    @FXML
    private Button exitbutton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Label live_time;

    @FXML
    private Button logo;

    /**
     * 
     * Event handler for the Back button press.
     * Opens the HODStatisticOnStudent view and loads the data.
     * 
     * @param event The action event triggered by the user.
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
        HODStatisticOnStudentController Hssc = (HODStatisticOnStudentController) openScreen(
                "/clientFXMLS/HODStatisticOnStudent.fxml",
                "CEMS System - Head Of Department - Statistics On Student", event);
        Hssc.load();
    }

    /**
     * 
     * Initializes the controller.
     * Starts the clock.
     */
    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

    /**
     * 
     * Displays the student information and their completed tests.
     * 
     * @param user                  The statistics of the student.
     * @param AllcompletedTestsList The list of completed tests by the student.
     */
    @FXML
    void StudendInfo(Statistics user, ArrayList<Test> AllcompletedTestsList) {

        // Start the clock
        //Timenow(live_time);

        Avg_text.setText("Average: " + Integer.toString(user.getAverage()));
        Name_text.setText("Student's Name: " + user.getStudentName());
        ID_Text.setText("Student ID: " + user.getStudentID());
        completedTestsList = new ArrayList<>();
        // Populate the ComboBox with course names
        Set<String> courseNames = new HashSet<>();
        for (Test test : AllcompletedTestsList) {
            if (user.getStudentID().equals(test.getStudentID())) {
                ClientUI.chat.getHodCourseForTestSpecificStudent(test.getId());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String courseName = SubjectCourse.get(2);
                if (!courseNames.contains(courseName)) {
                    courseNames.add(courseName);
                }
                completedTestsList.add(test);
            }
        }
        // Add the "Show all" option to the ComboBox
        courseNames.add("Show all");
        Load("Show all");

        filterComboBox.getItems().clear();
        filterComboBox.getItems().addAll(FXCollections.observableArrayList(courseNames));

        // Set the action to be performed when an option is selected in the ComboBox
        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println((String) filterComboBox.getValue());
                Load((String) filterComboBox.getValue());
            }
        });
    }

    /**
     * 
     * Loads the data based on the specified value.
     * 
     * @param value The value to load.
     */
    public void Load(String value) {
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
                ClientUI.chat.getHodCourseForTestSpecificStudent(test.getId());
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

    /**
     * Retrieves the list of completed tests for a student.
     *
     * @return The list of completed tests for a student.
     */
    public static ArrayList<Test> getcompletedTestsForStudentList() {
        return completedTestsList;
    }

    /**
     * Sets the list of completed tests for a student.
     *
     * @param completedTests The list of completed tests for a student to set.
     */
    public static void setcompletedTestsForStudentList(ArrayList<Test> completedTests) {
        completedTestsList = completedTests;
    }

    /**
     * Retrieves the list of subjects and courses.
     *
     * @return The list of subjects and courses.
     */
    public static ArrayList<String> getSubjectCourse() {
        return SubjectCourse;
    }

    /**
     * Sets the list of subjects and courses.
     *
     * @param subjectCourse The list of subjects and courses to set.
     */
    public static void setSubjectCourse(ArrayList<String> subjectCourse) {
        SubjectCourse = subjectCourse;
    }

    /**
     * Retrieves the flag variable.
     *
     * @return The value of the flag variable.
     */
    public static boolean isFlag() {
        return flag;
    }

    /**
     * Sets the flag variable.
     *
     * @param flag The value to set for the flag variable.
     */
    public static void setFlag(boolean flag) {
        ViewGradesController.flag = flag;
    }

    /**
     * Retrieves the filter combo box.
     *
     * @return The filter combo box.
     */
    public ComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    /**
     * Sets the filter combo box.
     *
     * @param filterComboBox The filter combo box to set.
     */
    public void setFilterComboBox(ComboBox<String> filterComboBox) {
        this.filterComboBox = filterComboBox;
    }

    /**
     * Sets the list of subjects and courses for a test specific student.
     *
     * @param listToAdd The list of subjects and courses to set.
     */
    public static void setHodSubjectsCourseForTestSpecificStudent(ArrayList<String> listToAdd) {
        SubjectCourse = listToAdd;
    }
}
