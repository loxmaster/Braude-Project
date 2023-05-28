package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import logic.QuestionModel;
import logic.Test;


public class CreateTestController extends BasicController {

	private Test test = new Test();

	@FXML
	private CheckBox A, B, C, D;

	@FXML
	private TextField optionA, optionB, optionC, optionD;

	@FXML
	private TextArea qBody;

	@FXML
    private TextField code, duration, startTime, totalPoints;

    @FXML
    private DatePicker date;
	
	@FXML
	private VBox questionTracker;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private Button savebutton;

    @FXML
    private ComboBox<String> subjectComboBox;

	@FXML
	void addQuestionPressed(ActionEvent event) {
		
		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date.toString());
		test.setDuration(duration.getText());
		//test.setId(null);

		// Gets all the questions from DataBase
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());

		// Opens the Question DataBase
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
	}

	public void setTest(Test test) {
		this.test = test;
		ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();

		int index=1;
		for (QuestionModel question : tempQuestionList) {
			questionTracker.getChildren().add(createQuestionInTestButton(question, index));
			index++;
		}
	}

	private Button createQuestionInTestButton(QuestionModel question, int index) {
        Button questionInTestButton = new Button("Q: " + index);

        questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
				qBody.setText(question.getQuestiontext());
                //optionA.setText(null);
				//optionB.setText(null);
				//optionC.setText(null);
				//optionD.setText(null);
                
            }
        });

        //questionInTestButton.setId("questionbutton");
        questionInTestButton.setPrefWidth(60);
        questionInTestButton.setPrefHeight(20);
		question.setEdit(questionInTestButton);
		return questionInTestButton;
    }

	@FXML
	void helpPressed(ActionEvent event) {

	}

	// INSERT INTO `projecton`.`tests` (`id`, `duration`, `testcomments`, `authorsname`, `code`, `date`, `time`, `questions`) VALUES ('sd', 'sd', 'sd', 'sd', 'sd', 'sd', 'sd', 'sd');
	@FXML
	void savePressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date.toString());
		test.setDuration(duration.getText());
		//test.setId(null);

		//ClientUI.chat.sendTestToDatabase(test);
	}

}
