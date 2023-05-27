package logic;

import java.io.Serializable;
import clientControllers.EditQuestionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

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
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public QuestionModel getQuestion() {
        return this;
    }

}
