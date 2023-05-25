package logic;

import java.io.IOException;
import java.io.Serializable;

//import clientControllers.EditQuestionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionModel implements Serializable {
    private String id, subject, coursename;
    private String questiontext, questionnumber, lecturer;
    private Button edit;

    public QuestionModel(String id, String subject, String coursename, String questiontext, String questionnumber,
            String lecturer) {
        this.id = id;
        this.subject = subject;
        this.coursename = coursename;
        this.questiontext = questiontext;
        this.questionnumber = questionnumber;
        this.lecturer = lecturer;
    }

    public void createButton() {
        edit = new Button("Edit");

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
                //EditQuestionController eqc = loader.getController();
                //eqc.loadQuestion(getQuestion());
                System.out.println("opening edit question");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
                currentStage.setScene(scene);
                currentStage.setTitle("CEMS System - Lecturer - Edit Question");
                currentStage.show();
            }
        });

        edit.setId("BtnInfo");
        edit.setPrefWidth(60);
        edit.setPrefHeight(20);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCoursename() {
        return this.coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getQuestiontext() {
        return this.questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public String getQuestionnumber() {
        return this.questionnumber;
    }

    public void setQuestionnumber(String questionnumber) {
        this.questionnumber = questionnumber;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    //how is this diffrent from create button ? just by creating edit ?
    public Button getEdit() {
        createButton();
        return this.edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public QuestionModel getQuestion() {
        return this;
    }

}
