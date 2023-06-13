package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


	// ###################### Local Variables ##########################


	private Test test = new Test();

	private int pointsInTest = 0;

	private ChangeListener<? super String> qPointsListener;


	// ###################### FXML Variables ###########################


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
    private TextArea qPoints;

	@FXML
	private ComboBox<String> subjectComboBox;
// ######################### FXML Methods ###########################
	// who is this?
	public void loadsubjectsCombobox() {
		ObservableList<String> subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);


	/**
	 * Method handeling the pressing of 'Add Questions' button .
	 * @param event
	 */
	@FXML
	void addQuestionPressed(ActionEvent event) {
		// test is current test
		// set all information so when we come back we

		// Remembers all the data from the screen and sends it to DataBase controller
		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date);
		test.setDuration(duration.getText());
		test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
		// TODO test.setId(null);
		
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

		// Opens the Question DataBase screen , remembers the controller and loads the test to controller
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml", "CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
	}

	/**
	 * Method handeling the pressing of 'Help' button .
	 * @param event
	 */
	@FXML
	void helpPressed(ActionEvent event) {

	}

	/**
	 * Method handeling the pressing of 'Save' button .
	 * @param event
	 */
	@FXML
	void savePressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getUsername()); // TODO change to pName and not username
		test.setSubject(subjectComboBox.getValue());
		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date);
		test.setDuration(duration.getText());
		test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
		// TODO test.setId();
		
		// Sends the test to the database using the ClientController 'chat' in the ClientUI
		ClientUI.chat.sendTestToDatabase(test);

		// Goes to lecturer screen
		// TODO show some prompt of finishing or preview of what is gonna be sent
		backToLecturer(event);
	}


	//######################### Local Methods ##############################

/**
	 * Hook method , called when this screen is opened.
	 */
	public void load() {
		
		// Getting the subjects from the static subject list 
		ObservableList<String> subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		
		// Defining the ComboBox for subjects
		subjectComboBox.getItems().removeAll();
		subjectComboBox.setItems(subjectList);
	}

	/**
	 * Method to set the test we were working on when returning to this screen.
	 */
	public void setTest(Test test) {

		// loads the subjects in the subjects combobox
		loadsubjectsCombobox();

		// Updates the current test and sets the view for the client
		this.test = test;
		test.setAuthor(ClientHandler.user.getUsername());
		subjectComboBox.setValue(test.getSubject());
		code.setText(test.getTestCode());
		startTime.setText(test.getTime());
		duration.setText(test.getDuration());
		date.setValue(test.getDate().getValue());
		totalPoints.setText(String.valueOf(test.getTotalPoints()));

		// For every question added we define a specific button for it
		ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();
		int index = 1; // Index for the question number (1,2,3,..)
		for (QuestionModel question : tempQuestionList) {
			// Creates and adds the question to the VBox
			questionTracker.getChildren().add(createQuestionInTestButton(question, index));
			index++;
		}
	}

	/**
	 * Method to create a question button in a VBox at the test screen.
	 * @param question the QuestionModel where all the data
	 * @param index	number of the question in the list
	 * @return Button
	 */
	private Button createQuestionInTestButton(QuestionModel question, int index) {
		Button questionInTestButton = new Button("Q: " + index);

		// Handle what happens when you press the button
		questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// Defining the total points field listener
				// Removes the current listener
				if (qPointsListener != null)
					qPoints.textProperty().removeListener(qPointsListener); 
				// Creates new listener for this question and puts it in qPointsListener
				qPointsListener = new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						try {
							if(newValue == null)
								question.setPoints(0);
							else 
								question.setPoints(Integer.parseInt(newValue));
							updateTotalPoints();
							totalPoints.setText(String.valueOf(pointsInTest));
						} catch(Exception e) {
							question.setPoints(0);
						 }
					}
				};
				// Inserts the Listener to the qPoints field
				qPoints.textProperty().addListener(qPointsListener);
				
				// Updates the view for the user
				qBody.setText(question.getQuestiontext());
				qBody.setWrapText(true);
				OptionA.setText(question.getOptionA());
				OptionB.setText(question.getOptionB());
				OptionC.setText(question.getOptionC());
				OptionD.setText(question.getOptionD());
				qID.setText(question.getId());
				qPoints.setText("" + question.getPoints());

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

	/**
	 * Method to update the total amount of points currently in the test, by
	 * going over all the questions in the test and adding up all the questions points.
	 * Remembers the result in local variable pointsInTest.
	 */
	private void updateTotalPoints() {
		int totalPoints = 0;
		ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();
		for (QuestionModel question : tempQuestionList)
			totalPoints += question.getPoints();
		pointsInTest = totalPoints;
	}

}
