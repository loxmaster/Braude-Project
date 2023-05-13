package logic;

import javafx.scene.control.Button;

public class QuestionModel {
    private int id;
    private String question, answer;
    private Button edit;

    public QuestionModel(String question) {
        id = 0;
        this.question = question;
        answer = null;
        edit = null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

}
