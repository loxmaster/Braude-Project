package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import clientHandlers.ClientUI;
import logic.Test;
import javafx.event.EventHandler;

public class ViewGradesController extends BasicController {

    public static ArrayList<Test> completedTestsList;
    private static ArrayList<String> SubjectCourse;
    public static boolean flag = false;
    public String value;

    // This object is used for synchronization
    final Object lock = new Object();

    @FXML
    private GridPane ExamContainer;

    @FXML
    private Text Student_ID_Text;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button exitbutton;

    @FXML
    private Button filterButton; // Button for user to initiate filter

    @FXML
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }

    @FXML
    void initialize() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientUI.chat.getcompletedTestsForStudentList();
                synchronized (lock) {
                    lock.notify();
                }
            }
        }).start();

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Student_ID_Text.setText("Student ID: " + completedTestsList.get(0).getStudentID());
        // Populate the ComboBox with course names
        Set<String> courseNames = new HashSet<>();
        for (Test test : completedTestsList) {
            ClientUI.chat.getCourseForTest(test.getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // ... rest of your code ...
            String courseName = SubjectCourse.get(2); // Assuming Test objects have a getCourseName method
            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }
        courseNames.add("Show all");
        filterComboBox.getItems().addAll(FXCollections.observableArrayList(courseNames));
        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println((String) filterComboBox.getValue());
                ExamLoad((String) filterComboBox.getValue());
            }
        });
    }

    public void ExamLoad(String value) {
        // Clear the ExamContainer
        ExamContainer.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (Test test : completedTestsList) {
                ClientUI.chat.getCourseForTest(test.getId());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (value.equals("Show all")) {
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
                } else if (SubjectCourse.get(2).equals(value)) {

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

    public static ArrayList<Test> getcompletedTestsForStudentList() {
        return completedTestsList;
    }

    public static void setcompletedTestsForStudentList(ArrayList<Test> completedTests) {
        completedTestsList = completedTests;
    }

    public static void setSubjectsCoursesList(ArrayList<String> SubjectandCourse) {
        SubjectCourse = SubjectandCourse;
    }

    public static ArrayList<Test> getCompletedTestsList() {
        return completedTestsList;
    }

    public static void setCompletedTestsList(ArrayList<Test> completedTestsList) {
        ViewGradesController.completedTestsList = completedTestsList;
    }

    public static ArrayList<String> getSubjectCourse() {
        return SubjectCourse;
    }

    public static void setSubjectCourse(ArrayList<String> subjectCourse) {
        SubjectCourse = subjectCourse;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        ViewGradesController.flag = flag;
    }

    public ComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    public void setFilterComboBox(ComboBox<String> filterComboBox) {
        this.filterComboBox = filterComboBox;
    }

}
