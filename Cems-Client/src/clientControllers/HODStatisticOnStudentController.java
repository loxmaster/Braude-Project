package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
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
import logic.User;

/**
 * This controller handles the Head of Department's view of student statistics.
 * It extends from the BasicController class.
 */
public class HODStatisticOnStudentController extends BasicController {

    @FXML
    private TableColumn<Statistics, String> Average; // Column for average score

    @FXML
    private TableColumn<Statistics, Void> Distribution; // Column for score distribution

    @FXML
    private TableColumn<Statistics, String> Email; // Column for student email

    @FXML
    private TableColumn<Statistics, String> Number_Of_Courses; // Column for number of courses

    @FXML
    private TableColumn<Statistics, String> StudentsID; // Column for student ID

    @FXML
    private TableColumn<Statistics, String> Students_Name; // Column for student name

    @FXML
    private ComboBox<String> Name_combo; // Combo box for student names

    @FXML
    private ComboBox<String> Combo_ID; // Combo box for student IDs

    @FXML
    private Button exitbutton; // Button for exiting

    @FXML
    private Button logo; // Logo button

    @FXML
    private TableView<Statistics> table; // Table for displaying statistics

    @FXML
    private Label live_time; // Label for displaying live time

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
     * This function is called when the logout button is pressed.
     * It can be used to handle the logout action.
     *
     * @param event The ActionEvent object
     */
    @FXML
    void logoutPressed(ActionEvent event) {
        // Handle the logout action if needed
    }

    /**
     * This function is called when the back button is pressed.
     * It opens the Statistical screen.
     *
     * @param event The ActionEvent object
     */
    @FXML
    void backPressed(ActionEvent event) {
        // open Statistical screen screen
        openScreen("/clientFXMLS/HodStatisticScrene.fxml", "CEMS System - Head Of Department - Statistics", event);
    }

    // Store the list of completed tests and exam statistics
    private static ArrayList<String> StudentsList;
    private static ArrayList<Test> completedTestsList;
    private static ArrayList<Statistics> AllStudentList;
    private static ArrayList<Test> AllcompletedTestsList;

    /**
     * 
     * Loads the data for the HODStatisticOnStudentController.
     * This method should be called to populate the necessary data for the view.
     */
    public void load() {

        // Request completed tests for the lecturer
        ClientUI.chat.geStudentListUnderSameDepartment();
        // Wait for the response
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 0;
        ArrayList<User> StudentUserList = new ArrayList<>();
        while (i < StudentsList.size()) {
            User student = new User(StudentsList.get(i), StudentsList.get(i + 1),
                    StudentsList.get(i + 2),
                    StudentsList.get(i + 3), StudentsList.get(i + 4), StudentsList.get(i + 5));
            i += 6;
            StudentUserList.add(student);
        }

        // Set the cell value factories for each column
        // This tells JavaFX how to populate each cell in the column
        // ... Cell value factories ...

        // Initialize some variables for calculations
        Students_Name.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        StudentsID.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Average.setCellValueFactory(new PropertyValueFactory<>("Average"));
        Number_Of_Courses.setCellValueFactory(new PropertyValueFactory<>("Number_Of_Courses"));

        AllStudentList = new ArrayList<>();
        AllcompletedTestsList = new ArrayList<>();
        for (User Student_user : StudentUserList) {
            // Request completed tests for the lecturer
            ClientUI.chat.HodGETcompletedTestsForSpecificStudentList(Student_user.getUser_id());
            // Wait for the response
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int Total_Number_of_Exams = 0, Grade_Total_Sum = 0, Total_Number_of_Courses = 0;
            String diffCourses = "";
            if (completedTestsList.size() > 0) {
                Total_Number_of_Exams = completedTestsList.size();
                for (Test test : completedTestsList) {
                    Grade_Total_Sum += Integer.parseInt(test.getGrade());
                    if (!diffCourses.contains(test.getId().substring(0, 4))) {
                        diffCourses = diffCourses + test.getId().substring(0, 4) + ",";
                        Total_Number_of_Courses++;
                    }

                }

                AllStudentList.add(new Statistics(Student_user.getUsername(), Student_user.getUser_id(),
                        Student_user.getEmail(), Grade_Total_Sum / Total_Number_of_Exams,
                        Integer.toString(Total_Number_of_Courses)));
                AllcompletedTestsList.addAll(completedTestsList);
            }
        }

        Set<String> studIds = new HashSet<>();
        for (User use : StudentUserList) {
            studIds.add(use.getUser_id());
        }
        Combo_ID.getItems().addAll(studIds);

        Set<String> studName = new HashSet<>();
        for (User use : StudentUserList) {
            studName.add(use.getUsername());
        }
        Name_combo.getItems().addAll(studName);

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
                                        getClass().getResource("/clientFXMLS/HODViewGrades.fxml"));
                                Parent root = loader.load();

                                HODViewGradesController hodviewGradesController = loader.getController();
                                hodviewGradesController.StudendInfo(data, AllcompletedTestsList);

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

        ObservableList<Statistics> listToAdd = FXCollections.observableArrayList(AllStudentList);
        table.setItems(listToAdd);
        if (!table.getColumns().contains(Distribution)) {
            table.getColumns().add(Distribution);

        }

        // Create a FilteredList wrapping the ObservableList
        FilteredList<Statistics> filteredData = new FilteredList<>(listToAdd, p -> true);

        Combo_ID.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stat -> {
                // If filter text is empty, display all statistics.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare StudentID of every statistics with filter.
                return stat.getStudentID().equals(newValue);
            });
        });

        Name_combo.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stat -> {
                // If filter text is empty, display all statistics.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare StudentName of every statistics with filter.
                return stat.getStudentName().equals(newValue);
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
     * Sets the list of students under the same department.
     *
     * @param listToAdd The ArrayList of student names.
     */
    public static void setStudentListUnderSameDepartment(ArrayList<String> listToAdd) {
        StudentsList = listToAdd;
    }

    /**
     * Sets the list of completed tests for a specific student.
     *
     * @param listToAdd The ArrayList of completed Test objects.
     */
    public static void setcompletedTestsForSpecificStudent(ArrayList<Test> listToAdd) {
        completedTestsList = listToAdd;
    }
}
