package clientControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import logic.QuestionModel;
import logic.Test;

public class StudentExamController extends BasicController {
    public static ArrayList<String> questionList;
    private String test_id;

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

    @FXML
    private ChoiceBox<?> qComboBox;

    @FXML
    private TextField questionBody;

    @FXML
	private Label live_time;

	@FXML
	void submitPressed(ActionEvent event){}
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
    

    @FXML
    void NextQuestionPressed(ActionEvent event) {

    }

    @FXML
    void qComboBox(MouseEvent event) {

        // loadQuestions(questionList);

    }

    // Private variable that the data from the server regarding the current
    // test will be entered to.
    private static Test test; 

    /*private StudentExamController() {
        test = new Test();
    }*/


    /////////////////////////////// FXML variables. ///////////////////////////////


    @FXML
    private RadioButton A, B, C, D;

    @FXML
    private TextArea OptionA, OptionB, OptionC, OptionD;

    @FXML
    private TextField code, courseField, qPoints, startTime;

    @FXML
    private TextArea qBody;

    @FXML
    private TextField testComments, duration, subjectField, qID;

    @FXML
    private Button exitbutton, logo;

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
			if (question.getSelected().isEmpty()) {
				JOptionPane.showMessageDialog(null, "You didnt select all the questions !", "Warning !", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		ClientUI.chat.sendToCompletedTest(localTest);
		JOptionPane.showMessageDialog(null, "Good Luck !", "Success !", JOptionPane.WARNING_MESSAGE);
		backToStudent(event);

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
        if(localTest == null) {
			System.out.println("No test.");
            return; // TODO add here something
        } else {
            // Setts every non-editable field in the screen.
            code.setText(localTest.getTestCode());
            courseField.setText(localTest.getCourse());
            startTime.setText(localTest.getTime());
            testComments.setText(localTest.getTestComments());
            subjectField.setText(localTest.getSubject());

            // Adds every question in the test to the questioinTracker , which is a VBox of question buttons.
            int index = 1; // Index for the question number (1,2,3,..)
            for( QuestionModel question : localTest.getQuesitonsInTest()) {
                questionTracker.getChildren().add(createQuestionInTestButton(question, index));
			    index++;
            }

			// Defining listeners for radio buttons to change the selected option for the question
       		A.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for(QuestionModel question : localTest.getQuesitonsInTest())
					if(question.getId().equals(questionID))
						question.setSelected("A");
			});

        	B.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for(QuestionModel question : localTest.getQuesitonsInTest())
					if(question.getId().equals(questionID))
						question.setSelected("B");
			});

        	C.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for(QuestionModel question : localTest.getQuesitonsInTest())
					if(question.getId().equals(questionID))
						question.setSelected("C");
			});

        	D.selectedProperty().addListener((observable, oldValue, newValue) -> {
				String questionID = qID.getText();
				for(QuestionModel question : localTest.getQuesitonsInTest())
					if(question.getId().equals(questionID))
						question.setSelected("D");
			});

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
				}
				else {
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
     * @return instace of Test.
     */
    public static Test getTest() {
        if( test == null )
            setTest(new Test());
        return test;
    }

    /**
     * Setter for the current test.
     * @param test
     */
    public static void setTest(Test test) {
        StudentExamController.test = test;
    }


    /////////////////////////////// Implementation for Choose Test Type ///////////////////////

	private String testCode;

    @FXML
	private Button selectDirectory;

    @FXML
    private TextField directoryPath;

   @FXML
	void AutomaticPressed(ActionEvent event) {
		StudentExamController sec = (StudentExamController)openScreen("/clientFXMLS/StudentExam.fxml", "CEMS System - Student - Exam", event);
		sec.load(testCode);
	}

	@FXML
	void ManualPressed(ActionEvent event) {
		openScreen("/clientFXMLS/getTestFile.fxml", "CEMS System - Student - Exam", event);
	}

	@FXML
	void openDirectoryChooser(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose a directory");
		Stage stage = (Stage) selectDirectory.getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			String directory = selectedDirectory.getAbsolutePath();
			directoryPath.setText(directory);
		}
	}

	@FXML
	void ClickDownload(ActionEvent event) {

		File receivedFile = null;
		try {
			/*
			 * check test id and validate from db
			 * it exists as an uploaded file
			 * if so, concat the 'Testid' with the id
			 */
			File newFile = new File("Cems-Server/TestFiles/Testid" + "123");
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileOutputStream fis = new FileOutputStream(newFile);
			fis.write(mybytearray);

			// bufferedIn.write(fis);

			// bufferedIn.read(mybytearray,0,mybytearray.length);

			System.out.println("File received from server.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return receivedFile;
 		
	}

	 public void loadTestCode(String testCode) {
        this.testCode = testCode;
    }
}
