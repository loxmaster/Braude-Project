package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import logic.QuestionModel;
import logic.Test;

public class EditTestController extends CreateTestController {
    private Test test;
    private ArrayList<QuestionModel>[] questions;
    private ObservableList<String> subjectList;
    private ObservableList<String> courseList;

    private Button deleteBtn;

    @Override
    void initialize() {

    }

    //
    void load(String testId) {
        loadSubject();
        loadCourse();
        // loadQuestions();

    }

    private void loadSubject() {
        HashSet<String> subjectUpdated = new HashSet<>();
        subjectUpdated.addAll(LecturerController.subjectsList);
        subjectUpdated.add(test.getSubject());
        subjectList = FXCollections.observableArrayList(subjectUpdated);
        subjectComboBox.setItems(subjectList);
        subjectComboBox.setValue(test.getSubject());
    }

    private void loadCourse() {
        HashSet<String> courseUpdated = new HashSet<>();
        courseUpdated.addAll(LecturerController.coursesList);
        courseUpdated.add(test.getCourse());
        courseList = FXCollections.observableArrayList(courseUpdated);
        courseComboBox.setItems(courseList);
        courseComboBox.setValue(test.getCourse());
    }

    private void loadQuestions(String testId) throws IOException {

    }

    // one step back !
    @Override
    void backPressed(ActionEvent event) {
        openScreen("/clientFXMLS/LecturerTestTable.fxml", "CEMS System - Lecturer", event);
    }

    // delete the current test
    void deleteTest(ActionEvent event) {
    }

    @FXML
    // unchecked(((************************************)))
    void addQuestionPressed(ActionEvent event) {
        // test is current test
        // set all information so when we come back we

        // Remembers all the data from the screen and sends it to DataBase controller
        test.setAuthor(ClientHandler.user.getpName());
        test.setSubject(subjectComboBox.getValue());

        // noah - changed here - get course - talk to me
        test.setCourse(courseComboBox.getValue());

        test.setTestCode(code.getText());
        test.setTime(startTime.getText());
        test.setDate(date);
        test.setDuration(duration.getText());
        test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
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
