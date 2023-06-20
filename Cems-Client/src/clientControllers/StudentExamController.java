package clientControllers;

import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.QuestionModel;
import logic.Test;

public class StudentExamController extends BasicController {

	/////////////////////////////// Local Variables ///////////////////////////////

	// Private variable that the data from the server regarding the current
	// test will be entered to.
	private static Test test;

	public static ArrayList<String> questionList;

	private String test_id;

	private LocalTime targetTime;

	private boolean timeRanOut = false;

	/////////////////////////////// FXML variables ////////////////////////////////

	@FXML
	private ChoiceBox<?> qComboBox;

	@FXML
	private TextField questionBody;

	@FXML
	private Label live_time;

	@FXML
	private RadioButton A, B, C, D;

	@FXML
	private TextArea OptionA, OptionB, OptionC, OptionD;

	@FXML
	private TextField code, courseField, qPoints, startTime;

	@FXML
	private TextArea qBody;

	@FXML
	private TextField testComments, remaining, subjectField, qID;

	@FXML
	private Button exitbutton, logo, submitButton;

	@FXML
	private VBox questionTracker;

	@FXML
	private ToggleGroup toggleGroup;

	/////////////////////////////// FXML methods ///////////////////////////////

	@FXML
	void exitExam(ActionEvent event) {
		backToStudent(event);
	}

	@FXML
	void submitExam(ActionEvent event) {
		Test localTest = getTest();

		// Checks if any of the questions in test hasnt been selected.
		for (QuestionModel question : localTest.getQuesitonsInTest()) {
			if (question.getSelected() == null) {
				JOptionPane.showMessageDialog(null, "You didnt select all the questions !", "Warning !",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		ClientUI.chat.sendToCompletedTest(localTest);
		JOptionPane.showMessageDialog(null, "Good Luck !", "Success !", JOptionPane.WARNING_MESSAGE);
	
		backToStudent(event);

	}

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	
	/////////////////////////////// Instance Methods ///////////////////////////////


	public void load(String testCode) {

		ClientUI.chat.getTestWithCodeForStudent(testCode);

		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Test localTest = getTest();
		if (localTest == null) {
			JOptionPane.showMessageDialog(null, "Test not found !", "Warning !", JOptionPane.WARNING_MESSAGE);
			return;
		} else {

			// Setts every non-editable field in the screen.
			code.setText(localTest.getTestCode());
			courseField.setText(localTest.getCourse());
			startTime.setText(localTest.getTime());
			testComments.setText(localTest.getTestComments());
			subjectField.setText(localTest.getSubject());

			// Adds every question in the test to the questioinTracker , which is a VBox of
			// question buttons.
			int index = 1; // Index for the question number (1,2,3,..)
			for (QuestionModel question : localTest.getQuesitonsInTest()) {
				questionTracker.getChildren().add(createQuestionInTestButton(question, index));
				index++;
			}

			// Defining listeners for radio buttons to change the selected option for the
			// question
			A.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for (QuestionModel question : localTest.getQuesitonsInTest())
					if (question.getId().equals(questionID))
						question.setSelected("a");
			});

			B.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for (QuestionModel question : localTest.getQuesitonsInTest())
					if (question.getId().equals(questionID))
						question.setSelected("b");
			});

			C.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for (QuestionModel question : localTest.getQuesitonsInTest())
					if (question.getId().equals(questionID))
						question.setSelected("c");
			});

			D.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for (QuestionModel question : localTest.getQuesitonsInTest())
					if (question.getId().equals(questionID))
						question.setSelected("d");
			});

			// Sets the timer for the test.
			// Set the target time.
			int[] targetTimeString = getTargetTimeString(localTest.getTime(), localTest.getDuration());
			targetTime = LocalTime.of(targetTimeString[0], targetTimeString[1]);

			// Adds ActionListener for the timer to update
			ActionListener taskPerformer = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// Gets the current time
					LocalTime currentTime = LocalTime.now();
					if (currentTime.compareTo(targetTime) >= 0) {
					} else {
						// Calculate the remaining time
						int remainingHours = targetTime.getHour() - currentTime.getHour();
						int remainingMinutes = targetTime.getMinute() - currentTime.getMinute();
						int remainingSeconds = targetTime.getSecond() - currentTime.getSecond();

						// Adjust for negative seconds or minutes
						if (remainingSeconds < 0) {
							remainingMinutes--;
							remainingSeconds += 60;
						}
						if (remainingMinutes < 0) {
							remainingHours--;
							remainingMinutes += 60;
						}

						// Format the remaining time as a string
						String remainingTime = String.format("%02d:%02d:%02d", remainingHours, remainingMinutes,
								remainingSeconds);

						// Update the text field with the remaining time
						remaining.setText(remainingTime);
					}
				}
			};
			// Create the timer with a 1-second delay
			int delay = 1000; // milliseconds
			new Timer(delay, taskPerformer).start();

		}
	}
	
	public StudentExamController getInstance() {
		return this;
	}

	private void timeEnded() {
		
	}

	// Method for adding the time and duration
	public int[] getTargetTimeString(String time, String duration) {
		// Split the first time string
		String[] time1Parts = time.split(":");
		int hours1 = Integer.parseInt(time1Parts[0]);
		int minutes1 = Integer.parseInt(time1Parts[1]);

		// Split the second time string
		String[] time2Parts = duration.split(":");
		int hours2 = Integer.parseInt(time2Parts[0]);
		int minutes2 = Integer.parseInt(time2Parts[1]);

		// Add the hours and minutes
		int[] toReturn = new int[2];
		toReturn[0] = hours1 + hours2;
		toReturn[1] = minutes1 + minutes2;

		// Adjust the total hours and minutes
		if (toReturn[1] >= 60) {
			toReturn[0] += toReturn[1] / 60;
			toReturn[1] %= 60;
		}
		return toReturn;
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
		questionInTestButton.setPadding(new Insets(8, 4, 8, 4));
		// Handle what happens when you press the button
		questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// Updates the view for the student
				qBody.setText(question.getQuestiontext());
				qBody.setWrapText(true);
				OptionA.setText(question.getOptionA());
				OptionB.setText(question.getOptionB());
				OptionC.setText(question.getOptionC());
				OptionD.setText(question.getOptionD());
				qID.setText(question.getId());
				qPoints.setText("" + question.getPoints());

				if (question.getSelected() != null) {

					switch (question.getSelected()) {
						case "A":
							A.setSelected(true);
							break;
						case "B":
							B.setSelected(true);
							break;
						case "C":
							C.setSelected(true);
							break;
						case "D":
							D.setSelected(true);
							break;
					}
				} else {
					A.setSelected(false);
					B.setSelected(false);
					C.setSelected(false);
					D.setSelected(false);
				}
			}
		});

		questionInTestButton.setPrefWidth(70);
		questionInTestButton.setPrefHeight(10);

		// adds the button to the HBox
		hBox.getChildren().add(0, questionInTestButton);
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}

	/**
	 * Getter for the current test.
	 * 
	 * @return instace of Test.
	 */
	public static Test getTest() {
		if (test == null)
			setTest(new Test());
		return test;
	}

	/**
	 * Setter for the current test.
	 * 
	 * @param test
	 */
	public static void setTest(Test test) {
		StudentExamController.test = test;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public static ArrayList<String> getQuestionList() {
		return questionList;
	}

	public static void setQuestionList(ArrayList<String> questionList) {
		StudentExamController.questionList = questionList;
	}

	
	 /////////////////////////////// Implementation for Popup Screen ///////////////////////

	
	@FXML
    void okPressed(ActionEvent event) {
		submitExam(event);
    }
}
