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
import logic.Test;

public class ViewGradesController extends BasicController {

    public static ArrayList<Test> completedTestsList;

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
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }
    
//comments here 
    @FXML
    void ExamLoad() {
        ClientUI.chat.getcompletedTestsList();       
        int column = 0;
        int row = 1;
        try {
            for (Test test : Exams) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource("/clientFXMLS/StudentTestCard.fxml");
                if (url == null) {
                    System.out.println("Resource not found. Exiting...");
                    return;
                }
                AnchorPane testcard = fxmlLoader.load(url.openStream());
                TestCardController cardController = fxmlLoader.getController();
                cardController.setCard(test);
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


    private List<Test> studentsExamsLoad() {

        List<Test> ls = new ArrayList<>();
        //Test test = ls.add(test);
        test = new Test("020301", "algebra1", "misha2", "0211", "6");
        ls.add(test);
        test = new Test("020301", "algebra1", "misha3", "056411", "100");
        ls.add(test);
        return ls;
    }

}