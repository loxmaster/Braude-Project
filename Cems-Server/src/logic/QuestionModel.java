package logic;

import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class QuestionModel implements Serializable {

    private String id, subject, coursename;
    private String questiontext, questionnumber, lecturer;
    private Button edit;
    private CheckBox checkBox;

    public QuestionModel(String id, String subject, String coursename, String questiontext, String questionnumber,
            String lecturer) {
        this.id = id;
        this.subject = subject;
        this.coursename = coursename;
        this.questiontext = questiontext;
        this.questionnumber = questionnumber;
        this.lecturer = lecturer;
        checkBox = new CheckBox();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void createButton() {
        edit = new Button("Edit");

        edit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            }
        });

        edit.setId("editbutton");
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
