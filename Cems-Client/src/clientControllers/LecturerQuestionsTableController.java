package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    public void loadTable() {

        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        coursename.setCellValueFactory(new PropertyValueFactory<>("Coursename"));
        questiontext.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
        questionnumber.setCellValueFactory(new PropertyValueFactory<>("Questionnumber"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));

        // Waits 5 seconds for data to be found
        int cap = 20;
        while (LecturerController.getQuestions().isEmpty() && (cap > 0)) {
            try {
                Thread.sleep(250);
                cap--;
            } catch (InterruptedException e) { }
        }
        if (LecturerController.questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
            for (QuestionModel question : questionList)
                question.setEdit(createEditButton(question));
            table.setItems(questionList);
        }
    }

    public Button createEditButton(QuestionModel question) {
        Button edit = new Button("Edit");

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // remember all the data of the question
                // send to EditQuestionScreen
                ((Node) event.getSource()).getScene().getWindow().hide();
                AnchorPane root = null;
                Stage currentStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                try {
                    root = loader.load(getClass().getResource("/clientFXMLS/EditQuestion.fxml").openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EditQuestionController eqc = loader.getController();
                eqc.loadQuestion(question.getQuestion(), question.getId());
                System.out.println("opening edit question");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
                currentStage.setScene(scene);
                currentStage.setTitle("CEMS System - Lecturer - Edit Question");
                currentStage.show();
            }
        });

        edit.setId("editbutton");
        edit.setPrefWidth(60);
        edit.setPrefHeight(20);
        return edit;
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
