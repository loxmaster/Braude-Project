package clientControllers;

import java.util.ArrayList;
import java.util.HashSet;
import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import logic.QuestionModel;
import logic.Test;

/**
 * The EditTestController class handles the logic for editing a test.
 * It extends from the CreateTestController class.
 */
public class EditTestController extends CreateTestController {

    private Test test;
    private ArrayList<QuestionModel>[] questions;
    private ObservableList<String> subjectList;
    private ObservableList<String> courseList;

    private Button deleteBtn;

    /**
     * Initializes the controller.
     */
    @Override
    void initialize() {

    }

    /**
     * Loads the test with the given test ID.
     *
     * @param testId The ID of the test to load.
     */
    void load(String testId) {
        loadSubject();
        loadCourse();
        // loadQuestions();

    }

    /**
     * Loads the subject into the subjectComboBox.
     */
    private void loadSubject() {
        HashSet<String> subjectUpdated = new HashSet<>();
        subjectUpdated.addAll(LecturerController.subjectsList);
        subjectUpdated.add(test.getSubject());
        subjectList = FXCollections.observableArrayList(subjectUpdated);
        // s // subjectComboBox.setValue(test.getSubject());ubjectComboBox.setItems(subjectList);
       
    }
    /**
     * Loads the course into the courseComboBox.
     */
    private void loadCourse() {
        HashSet<String> courseUpdated = new HashSet<>();
        courseUpdated.addAll(LecturerController.coursesList);
        courseUpdated.add(test.getCourse());
        courseList = FXCollections.observableArrayList(courseUpdated);
        // courseComboBox.setItems(courseList);
        // courseComboBox.setValue(test.getCourse());
    }
    /**
     * Loads the questions for the test.
     */
    private void loadQuestions() {

    }

    /**
     * Handles the event when the "Back" button is pressed.
     * It opens the LecturerTestTable screen.
     *
     * @param event The action event triggered by the button press.
     */
    @Override
    void backPressed(ActionEvent event) {
        openScreen("/clientFXMLS/LecturerTestTable.fxml", "CEMS System - Lecturer", event);
    }

  /**
     * Deletes the current test.
     *
     * @param event The action event triggered by the button press.
     */
    void deleteTest(ActionEvent event) {
    }

    /**
     * Handles the event when the "Add Question" button is pressed.
     * It sets the necessary information for the test and opens the Question
     * DataBase screen.
     *
     * @param event The action event triggered by the button press.
     */
    @FXML
    // unchecked(((************************************)))
    void addQuestionPressed(ActionEvent event) {
        // test is current test
        // set all information so when we come back we

        // Remembers all the data from the screen and sends it to DataBase controller
        test.setAuthor(ClientHandler.user.getpName());
       // test.setSubject(subjectComboBox.getValue());

        // noah - changed here - get course - talk to me
       // test.setCourse(courseComboBox.getValue());

        //test.setTestCode(code.getText());
        //test.setTime(startTime.getText());
        //test.setDate(date);
        //test.setDuration(duration.getText());
        //test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
        
        // TODO test.setId(null);

        // Gets all the questions from DataBase
        LecturerController.setQuestions(new ArrayList<QuestionModel>());

        // FIXME fix the query in
        // ClientHandler/GetLeturersQuestions_Handler so it returns only the
        // courses the username's courses
        // for example: lecturer noah does MATH and TOHNA, it should only have access to
        // the questions written by these subject\departments
        // and not show stuff from mech engineer

        // @kookmao - changed to it returns every question in the database,
        ClientUI.chat.GetLecturersQuestions("*");
        // ClientUI.chat.getSubjectsForLecturer(ClientHandler.user.getUsername());

        // Opens the Question DataBase screen , remembers the controller and loads the
        // test to controller
        DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
                "CEMS System - Lecturer - Create Test - Question Data Base", event);
        dbq.load(test);
    }

}
