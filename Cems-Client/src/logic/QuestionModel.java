package logic;

import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class QuestionModel implements Serializable {

    private String id, subject, coursename;
    private String questiontext, questionnumber, lecturer;
    private String optionA, optionB, optionC, optionD;
    private String answer;
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

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
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

    public QuestionModel getQuestion() {
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

}
