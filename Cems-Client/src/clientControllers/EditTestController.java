package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
        loadQuestions();

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

    @Override
    void backPressed(ActionEvent event) {
        openScreen("/clientFXMLS/LecturerTestTable.fxml", "CEMS System - Lecturer", event);
    }

}
