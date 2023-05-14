package logic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class QuestionModel {
    private int id;
    private String question, answer;
    private Button edit;

    public QuestionModel(String question) {
        id = 0;
        this.question = question;
        answer = null;
        edit = new Button();
        edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// remember all the shit of the question 
                // send to EditQuestionScreen
			}
        });
        edit.setText("Edit");
        edit.setId("BtnInfo");
        edit.setPrefWidth(60);
        edit.setPrefHeight(20);
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
