package clientControllers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.QuestionModel;
import logic.Test;

public class CreateTestController extends BasicController {


	// ############################### Local Variables #################################################################
	ObservableList<String> subjectList;
	ObservableList<String> courseList;

	private Test test = new Test();

	private int pointsInTest = 0;

	private ChangeListener<? super String> questionPointsListener; // Listener for points TextBox

	private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}$");

	
	// ############################### FXML Variables ###################################################################


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
	@FXML
	private ComboBox<String> courseComboBox;


// ############################### FXML Methods #######################################################################
	


  /**
	 * Hook method , called when this screen is opened and sets the subject
	 * ComboBox . P.S. Since the subjectList is static we can access it and
	 * no parameters needed when calling this function.
	 */ 
public void loadFilterComboboxes() {
	subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
	courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

	subjectComboBox.getItems().removeAll();
	subjectComboBox.setItems(subjectList);

	courseComboBox.getItems().removeAll();
	courseComboBox.setItems(courseList);
	
}

	/**
	 * Method handeling the pressing of 'Add Test Comments' button .
	 * @param event
	 */
	@FXML
    void editCommentsPressed(ActionEvent event) {
		
		// Opens the comment screen and waits for it too be closed. when closed
		// get the comment using the controller that is returned.
		//BasicController basicCtrl = new CreateTestController();
		openPopupCommentScreen( "/clientFXMLS/TestComments.fxml" , "Add Comments", this, test.getTestComments());
		
		// Because we've sent this instance of CreateTestController as the parameter for the FXMLoader , 
		// the 'comment' value changes accordingly here . (See the code at the end for refrence)
		test.setTestComments(comment);
		System.out.println("CreateTese: The Comments: " + comment);
    }

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
		test.setTestCode(code.getText());
		
		// TODO add course to build
		test.setId( buildIdForTest( test.getSubject(), "01"));

		// Checks if the subject has been picked
		if(subjectComboBox.getValue() != null) {
			subjectComboBox.setStyle("-fx-background-color: transparent;");
			test.setSubject(subjectComboBox.getValue());
		} else {
			subjectComboBox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Please select related subject !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks if the test points are in order 
		if(totalPoints.getText().equals("100")) {
			totalPoints.setStyle("-fx-background-color: transparent;");
			test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
		} else {
			totalPoints.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Points dont add up to 100 !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks if the date has been picked
		if(date.getValue() != null) {
			date.setStyle("-fx-background-color: transparent;");
			test.setDate(date);
		} else {
			date.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Date Not Picked !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks if the Start time matches the wanted style
		if (TIME_PATTERN.matcher(startTime.getText()).matches()) {
				startTime.setStyle("-fx-background-color: transparent;");
                test.setTime(startTime.getText());
        } else {
            startTime.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Please insert time in a HH:MM format !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (TIME_PATTERN.matcher(duration.getText()).matches() && !duration.getText().equals("00:00")) {
				duration.setStyle("-fx-background-color: transparent;");
                test.setDuration(duration.getText());
        } else {
            duration.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Please insert duration in a HH:MM format and above 0 !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(test.getQuesitonsInTest().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Sends the test to the database using the ClientController 'chat' in the ClientUI
		ClientUI.chat.sendTestToDatabase(test);

		// Goes to lecturer screen
		// TODO show some prompt of finishing or preview of what is gonna be sent
		backToLecturer(event);
	}


	// ############################### Local Methods ########################################################################

	

	/**
	 * Method to set the test we were working on when returning to this screen.
	 * @param test the test to add. Of instance Test.
	 */
	public void setTest(Test test) {

		// loads the subjects in the subjects combobox
		loadFilterComboboxes();

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
	 * @param question the QuestionModel where all the data.
	 * @param index of the question in the list.
	 * @return Button.
	 */
	private HBox createQuestionInTestButton(QuestionModel question, int index) {
		// HBox for the question button and the corresponding delete button
		HBox hBox = new HBox();
		
		// Creating the question button
		Button questionInTestButton = new Button("Q: " + index);

		// Handle what happens when you press the button
		questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// Defining the total points field listener
				// Removes the current listener
				if (questionPointsListener != null)
					qPoints.textProperty().removeListener(questionPointsListener); 
				// Creates new listener for this question and puts it in questionPointsListener
				questionPointsListener = new ChangeListener<String>() {
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
				qPoints.textProperty().addListener(questionPointsListener);
				
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

		questionInTestButton.setPrefWidth(70);
		questionInTestButton.setPrefHeight(10);
		// questionInTestButton.setPadding(new Insets(20, 0, 20, 0));
		// Sets the button to the question 'Edit' field
		question.setEdit(questionInTestButton);

		// adds the button to the HBox
		hBox.getChildren().add(0, questionInTestButton);

		// Creates a delete button for this question
		Button deleteButton = new Button();
		deleteButton.setId("questionDeleteInTest");
		deleteButton.setPrefWidth(30);
		deleteButton.setPrefHeight(10);
		//deleteButton.set
		// Handles what happens wheb you press the button
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Removes the question from the questionTracker (VBox of questions)
				questionTracker.getChildren().remove(hBox);
				// Removes the corresponding question from the current test
				ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();
				tempQuestionList.remove(question);
				test.setQuesitonsInTest(tempQuestionList);
			}
			
		});

		// Adds the button to the HBox
		hBox.getChildren().add(1, deleteButton);
		hBox.setAlignment(Pos.CENTER);

		return hBox;
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

	/**
	 * Method to create test id from given subject and course.
	 * @param subject of the test.
	 * @param course of the test.
	 * @return the string representing the test id.
	 */
	public String buildIdForTest(String subject, String course) {
		//TODO get subject+course id string + number of test
		return "010104";
	}


	// ############################### Controller for comment screen ########################################################
	
	/**
	 * Since this controller is incharge of the comment screen also , this part will 
	 * serve as implementation for his controller.
	 */


	// Local variable for comment in test.
	private String comment;

	// FXML variable for the text box.
	@FXML
    private TextArea commentsBox;

	/**
	 * Method for setting the comment. Should be Called from BasicController -> openPopupCommentScreen(...) 
	 * after opening the screen to set the current existing comment in the test .
	 * @param comment the comment to add. If null not added.
	 */
	public void setComments(String comment) {
		this.comment = comment;
		if(comment != null)
			commentsBox.setText(comment);
	}


	/** TODO maybe for future refrence
	 * Method returning the string that is in the comment TextBox. 
	 * Called by the controller that opened this popup.
	private String getComment() {
		return comment;
	}*/


	/**
	 * Method Handeling the pressing of 'Cancel' button in comment creen.
	 * Closes the creen and returns to previous screen.
	 * @param event
	 */
    @FXML
    void cancelComments(ActionEvent event) {
		Stage popupStage = (Stage) commentsBox.getScene().getWindow();
    	popupStage.close();
    }

	/**
	 * Method Handeling the pressing of 'Save' button in comment creen.
	 * Remembers the text in the TextBox and returning to the previous window.
	 * @param event
	 */
    @FXML
    void saveComments(ActionEvent event) {
		comment = commentsBox.getText();
		Stage popupStage = (Stage) commentsBox.getScene().getWindow();
    	popupStage.close();
    }

}
