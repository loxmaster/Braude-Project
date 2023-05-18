package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;

public class LecturerQuestionsTableController extends BasicController {

    @FXML
    private Button BtnInfo;

    @FXML
    private TableView<QuestionModel> table;

    @FXML
    private TableColumn<QuestionModel, String> id;

    @FXML
    private TableColumn<QuestionModel, String> subject;

    @FXML
    private TableColumn<QuestionModel, String> coursename;

    @FXML
    private TableColumn<QuestionModel, String> questiontext;

    @FXML
    private TableColumn<QuestionModel, String> questionnumber;

    @FXML
    private TableColumn<QuestionModel, String> lecturer;

    @FXML
    private TableColumn<QuestionModel, Button> edit;

    public void loadTable(ArrayList<QuestionModel> question) {

        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        coursename.setCellValueFactory(new PropertyValueFactory<>("Coursename"));
        questiontext.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
        questionnumber.setCellValueFactory(new PropertyValueFactory<>("Questionnumber"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));

        // Waits 5 seconds for data to be found
        int cap = 20;
        while (LecturerController.questions.isEmpty() && (cap > 0)) {
            try {
                Thread.sleep(250);
                cap--;
            } catch (InterruptedException e) {
            }
        }
        if (LecturerController.questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
            table.setItems(questionList);
        }
    }

    @FXML
    void backPressed(ActionEvent event) {
        LecturerController.questions = new ArrayList<QuestionModel>();
        openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
    }

    @FXML
    void helpPressed(ActionEvent event) {

    }

}
