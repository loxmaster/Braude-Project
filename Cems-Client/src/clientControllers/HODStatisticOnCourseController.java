package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Statistics;
import logic.Test;

/**
 * 
 * This controller handles the statistics on course view for the Head of
 * Department (HOD).
 * 
 * It extends from the BasicController class.
 */
public class HODStatisticOnCourseController extends BasicController {

    // Define the FXML components
    @FXML
    private TableColumn<Statistics, String> Average_Grade;

    @FXML
    private TableColumn<Statistics, String> Course_ID;

    @FXML
    private TableColumn<Statistics, String> Course_Name;

    @FXML
    private TableColumn<Statistics, Void> Distribution;

    @FXML
    private TableColumn<Statistics, String> Number_of_Exams;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private TableView<Statistics> table;

    @FXML
    private Label live_time;

    /**
     * 
     * This function initializes the controller.
     * It starts the clock on the UI.
     */
    @FXML
    void initialize() {
        Timenow(live_time);
    }

    /**
     * 
     * This function is called when the "Log Out" button is pressed.
     * It handles the action of logging out the user.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void logoutPressed(ActionEvent event) {
    }

    /**
     * 
     * This function is called when the "Back" button is pressed.
     * It navigates the user back to the statistics view.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void backPressed(ActionEvent event) {
        openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
    }

    // Define the lists to store the completed tests and exam statistics
    private static ArrayList<String> SubjectCourse;
    private static ArrayList<String> SubjectCourseIDs;
    private static ArrayList<Test> completedTestsList;
    private static ArrayList<Test> AllcompletedTestsList;
    private static ArrayList<Statistics> allCourseStatistics;

    /**
     * 
     * This function loads the statistics from the server and populates the table.
     */
    public void load() {

        // Request the list of courses in the same department from the server
        ClientUI.chat.getCoursesSameDepartment();

        // Wait for the response from the server
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Initialize the SubjectCourseIDs list
        SubjectCourseIDs = new ArrayList<String>();
        int subid = 1, curid = 3;
        for (int i = 0; i < (SubjectCourse.size() / 4); i++) {
            SubjectCourseIDs.add(SubjectCourse.get(subid) + SubjectCourse.get(curid));
            subid += 4;
            curid += 4;
        }

        // Initialize the table columns
        Course_Name.setCellValueFactory(new PropertyValueFactory<>("Name_of_Courses"));
        Course_ID.setCellValueFactory(new PropertyValueFactory<>("Course"));
        Average_Grade.setCellValueFactory(new PropertyValueFactory<>("Average"));
        Number_of_Exams.setCellValueFactory(new PropertyValueFactory<>("Number_of_Exams"));

        // Initialize the allCourseStatistics and AllcompletedTestsList lists
        int indexcoursename = 2, indexSubjectCourseIDs = 0;
        allCourseStatistics = new ArrayList<>();
        AllcompletedTestsList = new ArrayList<>();

        for (String id : SubjectCourseIDs) {
            // Request the list of exams for the course from the server

            ClientUI.chat.getCoursesExams(id);
            // Wait for the response
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Initialize counters for passing and failing students
            int passingStudents = 0;
            int failingStudents = 0;

            // Define the passing grade
            int passingGrade = 55;
            int total_above = 0, total_below = 0, average = 0, median = 0;
            int total_grade = 0, Number_of_Exams = 0;
            ArrayList<String> diffStudentID = new ArrayList<>();

            // If there are completed tests for the course
            if (completedTestsList.size() > 0) {
                // Add the completed tests to the AllcompletedTestsList
                AllcompletedTestsList.addAll(completedTestsList);

                // Initialize an array to store the grades of the completed tests
                int[] array = new int[completedTestsList.size()];
                Arrays.fill(array, 0);
                String CurrenttestID = completedTestsList.get(0).getId();
                Number_of_Exams++;
                int index = 0;

                // For each completed test
                for (Test test : completedTestsList) {
                    // If the student's grade is greater than or equal to the passing grade,
                    // increment the passing students counter
                    if (Integer.parseInt(test.getGrade()) >= passingGrade) {
                        passingStudents++;
                    }
                    // Otherwise, increment the failing students counter
                    else {
                        failingStudents++;
                    }
                    // Add the grade to the total grade
                    total_grade += Integer.parseInt(test.getGrade());
                    // Add the grade to the array
                    array[index++] = Integer.parseInt(test.getGrade());
                    // If the test ID is different from the current test ID,
                    // increment the number of exams and update the current test ID
                    if (!CurrenttestID.equals(test.getId())) {
                        Number_of_Exams++;
                        CurrenttestID = test.getId();
                    }
                    // If the student ID is not already in the diffStudentID list,
                    // add it to the list
                    if (!diffStudentID.contains(test.getStudentID())) {
                        diffStudentID.add(test.getStudentID());
                    }
                }
                // Calculate the average grade
                average = total_grade / completedTestsList.size();

                // Sort the array of grades
                Arrays.sort(array);
                // Calculate the median grade
                if (completedTestsList.size() % 2 != 0) {
                    median = array[completedTestsList.size() / 2];
                } else {
                    median = (array[completedTestsList.size() / 2 - 1] + array[completedTestsList.size() / 2]) / 2;
                }
                // For each grade in the array, if the grade is above the average,
                // increment the total_above counter, otherwise increment the total_below
                // counter
                for (int i = 0; i < completedTestsList.size(); i++) {
                    if (array[i] >= average) {
                        total_above++;
                    } else {
                        total_below++;
                    }
                }
            }
            // Add a new Statistics object to the allCourseStatistics list
            allCourseStatistics.add(new Statistics(SubjectCourse.get(indexcoursename),
                    SubjectCourseIDs.get(indexSubjectCourseIDs), average, Integer.toString(Number_of_Exams),
                    diffStudentID.size(), Integer.toString(median), passingStudents, failingStudents, total_above,
                    total_below));
            indexSubjectCourseIDs++;
            indexcoursename += 4;
        }
        // Increment the index```java
        indexcoursename = 2;
        indexSubjectCourseIDs = 0;

        // Create a set to store the unique course names
        Set<String> studIds = new HashSet<>();
        for (Statistics use : allCourseStatistics) {
            studIds.add(use.getName_of_Courses());
        }
        // Add the unique course names to the comboBox
        comboBox.getItems().addAll(studIds);

        // Set the cell factory for the Distribution column
        // This tells JavaFX how to create each cell in the column
        Distribution.setCellFactory(new Callback<TableColumn<Statistics, Void>, TableCell<Statistics, Void>>() {
            @Override
            public TableCell<Statistics, Void> call(final TableColumn<Statistics, Void> param) {
                final TableCell<Statistics, Void> cell = new TableCell<Statistics, Void>() {

                    private final Button btn = new Button("Show Distribution");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            // Add the data to the table
                            Statistics data = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource("/clientFXMLS/HodStatisticOnCourseGraph.fxml"));
                                Parent root = loader.load();

                                HODOnCourseGraphController hodonCourseGraphController = loader.getController();
                                hodonCourseGraphController.CoursesdInfo(data, AllcompletedTestsList);

                                Stage stage = (Stage) btn.getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("selectedData: " + data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });

        // Create an ObservableList from the allCourseStatistics list
        ObservableList<Statistics> listToAdd = FXCollections.observableArrayList(allCourseStatistics);

        // Add the ObservableList to the table
        table.setItems(listToAdd);

        // If the Distribution column is not already in the table, add it to the table
        if (!table.getColumns().contains(Distribution)) {
            table.getColumns().add(Distribution);
        }

        // Create a FilteredList wrapping the ObservableList
        FilteredList<Statistics> filteredData = new FilteredList<>(listToAdd, p -> true);

        // Add a listener to the comboBox value property
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stat -> {
                // If filter text is empty, display all statistics.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare StudentID of every statistics with filter.
                return stat.getName_of_Courses().equals(newValue);
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Statistics> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // Add sorted and filtered data to the table
        table.setItems(sortedData);

    }

    /**
     * 
     * Set the list of courses in the same department.
     * 
     * @param listToAdd The list of courses to set.
     */
    public static void setCoursesSameDepartment(ArrayList<String> listToAdd) {
        SubjectCourse = listToAdd;
    }

    /**
     * 
     * Set the list of exams for the courses.
     * 
     * @param listToAdd The list of exams to set.
     */
    public static void setCoursesExams(ArrayList<Test> listToAdd) {
        completedTestsList = listToAdd;
    }

}
