package logic;

import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Question implements Serializable {

    private String id, subject, coursename;
    private String questiontext, questionnumber, lecturer;
    private String OptionA, OptionB, OptionC, OptionD, answer;
    private Button edit;

    public Question(String id, String lecturer, String subject, String coursename, String questiontext, String questionnumber) {
        this.id = id;
        this.subject = subject;
        this.coursename = coursename;
        this.questiontext = questiontext;
        this.questionnumber = questionnumber;
        this.lecturer = lecturer;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public Question getQuestion() {
        return this;
    }

    public CheckBox getCheckBox() {
        return new CheckBox();
    }

}
