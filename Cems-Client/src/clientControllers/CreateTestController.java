package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private TextField qID;

	@FXML
	private TextArea OptionA, OptionB, OptionC, OptionD;

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

	// who is this?
	public void loadsubjectsCombobox() {
		ObservableList<String> subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);

	}

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// test is current test
		// set all information so when we come back we

		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date);
		System.out.println(date.getId());
		System.out.println(date.getValue());

		test.setDuration(duration.getText());
		// test.setId(null);

		// Gets all the questions from DataBase
		LecturerController.setQuestions(new ArrayList<QuestionModel>());

		// FIXME fix the query in 
		//ClientHandler/GetLeturersQuestions_Handler so it returns only the
		// courses the username's courses
		// for example: lecturer noah does MATH and TOHNA, it should only have access to
		// the questions written by these subject\departments
		// and not show stuff from mech engineer

		// @kookmao - changed to it returns every question in the database,
		ClientUI.chat.GetLecturersQuestions("*");
		// ClientUI.chat.getSubjectsForLecturer(ClientHandler.user.getUsername());

		// Opens the Question DataBase
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
				"CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
	}

	/**
	 * Method to set the test we were working on when returning to this screen.
	 */
	public void setTest(Test test) {
		this.test = test;

		test.setAuthor(ClientHandler.user.getUsername());
		// loads the subjects in the subjects combobox
		loadsubjectsCombobox();
		subjectComboBox.setValue(test.getSubject());
		code.setText(test.getTestCode());
		startTime.setText(test.getTime());
		duration.setText(test.getDuration());
		date.setValue(test.getDate().getValue());

		ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();
		int index = 1;
		for (QuestionModel question : tempQuestionList) {
			questionTracker.getChildren().add(createQuestionInTestButton(question, index++));
			// index++;
		}
	}

	private Button createQuestionInTestButton(QuestionModel question, int index) {
		Button questionInTestButton = new Button("Q: " + index);

		questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				qBody.setText(question.getQuestiontext());
				qBody.setWrapText(true);
				OptionA.setText(question.getOptionA());
				OptionB.setText(question.getOptionB());
				OptionC.setText(question.getOptionC());
				OptionD.setText(question.getOptionD());
				qID.setText(question.getId());

				A.setSelected(false);
				B.setSelected(false);
				C.setSelected(false);
				D.setSelected(false);

				switch (question.getAnswer()) {
					case "a":
						A.setSelected(true);
						break;
					case "b":
						B.setSelected(true);
						break;
					case "c":
						C.setSelected(true);
						break;
					case "d":
						D.setSelected(true);
						break;

				}

			}
		});

		// questionInTestButton.setId("questionbutton");
		questionInTestButton.setPrefWidth(70);
		questionInTestButton.setPrefHeight(10);
		// questionInTestButton.setPadding(new Insets(20, 0, 20, 0));
		question.setEdit(questionInTestButton);
		return questionInTestButton;
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	// save changes ; write to db changes
	@FXML
	void savePressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date);
		test.setDuration(duration.getText());
		// TODO test.setId();

		ClientUI.chat.sendTestToDatabase(test);

		// Goes to lecturer screen
		backToLecturer(event);
	}

}
