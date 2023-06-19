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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.QuestionModel;
import logic.Test;

public class CreateTestController extends BasicController {

	// ############################### Local Variables
	// ###########################################

	/**
	 * Observable lists for subject and course data.
	 */
	ObservableList<String> subjectList;
	ObservableList<String> courseList;

	/**
	 * Test object for the current test.
	 */
	protected Test test = new Test();

	/**
	 * The total points in the current test.
	 */
	private int pointsInTest = 0;

	/**
	 * Listener for changes in the points TextBox.
	 */
	private ChangeListener<? super String> questionPointsListener;

	/**
	 * Static variables for test count and the next test number.
	 */
	public static String testCount;
	private static String nextTestNumber;

	/**
	 * Pattern for time validation.
	 */
	private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}$");

	// ############################### FXML Variables
	// ############################################

	/**
	 * ToggleGroup for radio buttons.
	 */
	@FXML
	protected ToggleGroup toggleGroup;

	/**
	 * Radio buttons for multiple choice options.
	 */
	@FXML
	protected RadioButton A, B, C, D;

	/**
	 * TextField for question ID.
	 */
	@FXML
	protected TextField qID;

	/**
	 * TextAreas for multiple choice options and question body.
	 */
	@FXML
	protected TextArea OptionA;
	@FXML
	protected TextArea OptionB;
	@FXML
	protected TextArea OptionC;
	@FXML
	protected TextArea OptionD;
	@FXML
	protected TextArea qBody;

	/**
	 * TextFields for code, duration, start time, and total points.
	 */
	@FXML
	protected TextField code, duration, startTime, totalPoints;

	/**
	 * DatePicker for test date.
	 */
	@FXML
	protected DatePicker date;

	/**
	 * VBox for question tracker.
	 */
	@FXML
	protected VBox questionTracker;

	/**
	 * Buttons for exit and logo.
	 */
	@FXML
	protected Button exitbutton;
	@FXML
	protected Button logo;

	/**
	 * Button for save operation.
	 */
	@FXML
	protected Button savebutton;

	/**
	 * TextArea for question points.
	 */
	@FXML
	protected TextArea qPoints;

	/**
	 * ComboBoxes for subject and course selection.
	 */
	@FXML
	protected ComboBox<String> subjectComboBox;
	@FXML
	protected ComboBox<String> courseComboBox;

	/**
	 * Label for live time display.
	 */
	@FXML
	private Label live_time;

	// ############################### FXML Methods
	// ###########################################

	/**
	 * This method is called to initialize a controller after its root element has
	 * been completely processed.
	 * It starts the clock and sets default values for the test if it is null.
	 */
	@FXML
	void initialize() {
		// Start the clock
		if (test == null) {
			code.setText("XXXX");
			duration.setText("00:00");
			startTime.setText("8:30");
		}
		Timenow(live_time);
	}

	/**
	 * This method is used to get the next test number.
	 *
	 * @return The next test number.
	 */
	public static String getNextTestNumber() {
		return nextTestNumber;
	}

	/**
	 * This method is used to set the next test number.
	 *
	 * @param nextNumber The next test number to be set.
	 */
	public static void setNextTestNumber(String nextNumber) {
		nextTestNumber = nextNumber;
	}

	/**
	 * This method is used to load the filter options into the comboboxes.
	 * It fetches the subjects and courses lists from the LecturerController and
	 * sets them in the respective comboboxes.
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
	 * Method handling the pressing of 'Add Test Comments' button.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void editCommentsPressed(ActionEvent event) {

		// Opens the comment screen and waits for it too be closed.
		openPopupCommentScreen("/clientFXMLS/TestComments.fxml", "Add Comments", this, test.getTestComments());

		// Because we've sent this instance of CreateTestController as the parameter for
		// the FXMLoader ,
		// the 'comment' value changes accordingly here . (See the code at the end for
		// refrence)
		test.setTestComments(comment);
		System.out.println("CreateTese: The Comments: " + comment);
	}

	/**
	 * This method is triggered when the add question button is pressed.
	 * It is responsible for handling the operation of adding a question.
	 *
	 * @param event The action event that triggers this method.
	 */

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// test is current test
		// set all information so when we come back we

		// Remembers all the data from the screen and sends it to DataBase controller
		test.setAuthor(ClientHandler.user.getpName());
		test.setSubject(subjectComboBox.getValue());

		// noah - changed here - get course - talk to me
		test.setCourse(courseComboBox.getValue());

		test.setTestCode(code.getText());
		test.setTime(startTime.getText());
		test.setDate(date);
		test.setDuration(duration.getText());
		test.setTotalPoints(Integer.parseInt(totalPoints.getText()));
		// TODO test.setId(null);

		// Gets all the questions from DataBase
		LecturerController.setQuestions(new ArrayList<QuestionModel>());

		// FIXME fix the query in
		// ClientHandler/GetLeturersQuestions_Handler so it returns only the
		// courses the username's courses
		// for example: lecturer noah does MATH and TOHNA, it should only have access to
		// the questions written by these subject\departments
		// and not show stuff from mech engineer

		// @kookmao - changed to it returns every question in the database,
		ClientUI.chat.GetLecturersQuestions("*");
		// ClientUI.chat.getSubjectsForLecturer(ClientHandler.user.getUsername());

		// Opens the Question DataBase screen , remembers the controller and loads the
		// test to controller
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
				"CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
	}

	/**
	 * This method is triggered when the save button is pressed.
	 * It is responsible for handling the save operation.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void savePressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getUsername()); // TODO change to pName and not username
		test.setTestCode(code.getText());

		// grab course values from the combobox and get course id from db
		ClientUI.chat.GetCourseIDfromSubjectCourses(courseComboBox.getValue());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String courseid = CreateQuestionController.courseID;

		// grab subject values from the combobox and get subject id from db
		ClientUI.chat.GetSubjectIDfromSubjectCourses(subjectComboBox.getValue());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String subjectid = CreateQuestionController.getSubjectID();

		// grab concat values of subjectid and courseid get the next test number from db
		ClientUI.chat.getNextFreeTestNumber(subjectid + courseid);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// String nextTestNumber = subjectid + courseid + "03";

		// send all three so set a new testID
		String numberid = subjectid + courseid + nextTestNumber;
		test.setId(numberid);

		// Checks if the test points are in order
		if (totalPoints.getText().equals("100")) {
			totalPoints.setStyle("-fx-background-color: transparent;");
			test.setTotalPoints(Integer.parseInt(totalPoints.getText()));

		} else {
			totalPoints.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Points should be equal to 100!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks if the date has been picked
		if (date.getValue() != null) {
			date.setStyle("-fx-background-color: transparent;");
			test.setDate(date);

		} else {
			date.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Date Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Checks if the course has been picked
		if (courseComboBox.getValue() == "" || courseComboBox.getValue() == " " || courseComboBox.getValue() == null) {
			courseComboBox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Course Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			courseComboBox.setStyle("-fx-background-color: transparent;");
			test.setCourse(courseComboBox.getValue());
		}

		// Checks if the subject has been picked
		if (subjectComboBox.getValue() == "" || subjectComboBox.getValue() == ""
				|| subjectComboBox.getValue() == null) {
			subjectComboBox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Subject Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			subjectComboBox.setStyle("-fx-background-color: transparent;");
			test.setSubject(subjectComboBox.getValue());
		}

		// Checks if the Start time matches the wanted style
		if (TIME_PATTERN.matcher(startTime.getText()).matches()) {
			startTime.setStyle("-fx-background-color: transparent;");
			test.setTime(startTime.getText());

		} else {
			startTime.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Please insert time in a HH:MM format!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (TIME_PATTERN.matcher(duration.getText()).matches() && !duration.getText().equals("00:00")) {
			duration.setStyle("-fx-background-color: transparent;");
			test.setDuration(duration.getText());

		} else {
			duration.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Please insert duration in a HH:MM format and above 0!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (test.getQuesitonsInTest().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Sends the test to the database using the ClientController 'chat' in the
		// ClientUI, while showing a message to the user
		ClientUI.chat.sendTestToDatabase(test);
		JOptionPane.showMessageDialog(null, "Changes Saved!", "Success!", JOptionPane.WARNING_MESSAGE);
		// Goes to lecturer screen
		backToLecturer(event);
	}

	// #########################################################
	// ######################### Local Methods #################
	// #########################################################

	/**
	 * Method to set the test we were working on when returning to this screen.
	 * 
	 * @param test the test to add. Of instance Test.
	 */
	public void setTest(Test test) {

		// loads the subjects in the subjects combobox
		loadFilterComboboxes();

		// Updates the current test and sets the view for the client
		this.test = test;
		test.setAuthor(ClientHandler.user.getUsername());
		subjectComboBox.setValue(test.getCourse());
		courseComboBox.setValue(test.getCourse());
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
	 * 
	 * @param question the QuestionModel where all the data.
	 * @param index    of the question in the list.
	 * @return Button.
	 */
	private HBox createQuestionInTestButton(QuestionModel question, int index) {
		// HBox for the question button and the corresponding delete button
		HBox hBox = new HBox();

		// Creating the question button
		Button questionInTestButton = new Button("Q: " + index);
		questionInTestButton.setPadding(new Insets(4, 4, 4, 4));
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
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						try {
							if (newValue == null)
								question.setPoints("0");
							else
								question.setPoints(newValue);
							updateTotalPoints();
							totalPoints.setText(String.valueOf(pointsInTest));
						} catch (Exception e) {
							question.setPoints("0");
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

		// adds the button to the HBox
		hBox.getChildren().add(0, questionInTestButton);

		// Creates a delete button for this question
		Button deleteButton = new Button();
		deleteButton.setId("questionDeleteInTest");
		deleteButton.setPrefWidth(30);
		deleteButton.setPrefHeight(10);
		// deleteButton.set
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
	 * going over all the questions in the test and adding up all the questions
	 * points.
	 * Remembers the result in local variable pointsInTest.
	 */
	private void updateTotalPoints() {
		int totalPoints = 0;
		ArrayList<QuestionModel> tempQuestionList = test.getQuesitonsInTest();
		for (QuestionModel question : tempQuestionList)
			totalPoints += Integer.parseInt(question.getPoints());
		pointsInTest = totalPoints;
	}

	/**
	 * Method to create test id from given subject and course.
	 * 
	 * @param subject of the test.
	 * @param course  of the test.
	 * @return the string representing the test id.
	 */
	public String buildIdForTest(String subject, String course) {
		// TODO get subject+course id string + number of test
		return "010110";
	}

	// ############################### Controller for comment screen
	// ########################################################

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
	 * Method for setting the comment. Should be Called from BasicController ->
	 * openPopupCommentScreen(...)
	 * after opening the screen to set the current existing comment in the test .
	 * 
	 * @param comment the comment to add. If null not added.
	 */
	public void setComments(String comment) {
		this.comment = comment;
		if (comment != null)
			commentsBox.setText(comment);
	}

	/**
	 * TODO maybe for future refrence
	 * Method returning the string that is in the comment TextBox.
	 * Called by the controller that opened this popup.
	 * private String getComment() {
	 * return comment;
	 * }
	 */

	/**
	 * This method is triggered when the cancel button is pressed in the comments
	 * screen.
	 * It closes the comments screen.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void cancelComments(ActionEvent event) {
		Stage popupStage = (Stage) commentsBox.getScene().getWindow();
		popupStage.close();
	}

	/**
	 * Method handling the pressing of 'Save' button in comment screen.
	 * It remembers the text in the TextBox and returns to the previous window.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void saveComments(ActionEvent event) {
		comment = commentsBox.getText();
		Stage popupStage = (Stage) commentsBox.getScene().getWindow();
		popupStage.close();
	}

	/**
	 * This method is triggered when the back button is pressed.
	 * It navigates back to the options screen.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void backPressed(ActionEvent event) {
		// goes back to options screen

		openScreen("/clientFXMLS/LecturerManageTest.fxml", "CEMS System - Lecturer", event);

	}

}
