package clientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.Test;

public class ViewGradesController extends BasicController {

    public static ArrayList<Test> completedTestsList;
    private static ArrayList<String> SubjectCourse;

    @FXML
    private GridPane ExamContainer;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private Button backButton;

    @FXML
    private List<Test> Exams;

    @FXML
    private Text Student_ID_Text;

    @FXML
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }


    // comments here
    @FXML
    void ExamLoad() {
        ClientUI.chat.getcompletedTestsList();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Student_ID_Text.setText("Student ID: "+completedTestsList.get(0).getStudentID());
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

    public static ArrayList<Test> getCompletedTestsList() {
        return completedTestsList;
    }

    public static void setCompletedTestsList(ArrayList<Test> completedTests) {
        completedTestsList = completedTests;
    }

    public static void setSubjectsCoursesList(ArrayList<String> SubjectandCourse) {
        SubjectCourse = SubjectandCourse;
    }



}