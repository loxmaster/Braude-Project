package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    void ExamLoad( String filterComboBox) {
        // Clear the ExamContainer
        ExamContainer.getChildren().clear();

        ClientUI.chat.getcompletedTestsForStudentList();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Student_ID_Text.setText("Student ID: " + completedTestsList.get(0).getStudentID());

        int column = 0;
        int row = 1;
        try {
            for (Test test : completedTestsList) {
                ClientUI.chat.getCourseForTest(test.getId());
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource("/clientFXMLS/StudentTestCard.fxml");
                if (url == null) {
                    System.out.println("Resource not found. Exiting...");
                    return;
                }
                AnchorPane testcard = fxmlLoader.load(url.openStream());
                TestCardController cardController = fxmlLoader.getController();
                cardController.setCard(test, SubjectCourse);

                // Add a check here to see if the course name matches the selected ComboBox item
                String selectedCourse = (String) filterComboBox.getValue();
                if (selectedCourse != null && !selectedCourse.equals("Show all")
                        && !test.getCourse().equals(selectedCourse)) {
                    continue;
                }

                if (column == 4) {
                    column = 0;
                    ++row;
                }

                ExamContainer.add(testcard, column++, row);
                GridPane.setMargin(testcard, new Insets(10));
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

    @FXML
    void initialize() {
        ClientUI.chat.getcompletedTestsForStudentList();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Student_ID_Text.setText("Student ID: " + completedTestsList.get(0).getStudentID());
        // Populate the ComboBox with course names
        List<String> courseNames = new ArrayList<>();
        for (Test test : completedTestsList) {
            ClientUI.chat.getCourseForTest(test.getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String courseName = SubjectCourse.get(2); // Assuming Test objects have a getCourseName method
            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }
        courseNames.add("Show all");
        filterComboBox.setItems(FXCollections.observableArrayList(courseNames));

        filterComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExamLoad((String) comboBox.getValue());
            }
        });
    }

}
