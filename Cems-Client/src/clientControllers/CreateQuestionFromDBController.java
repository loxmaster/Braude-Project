package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.QuestionModel;
import logic.Test;

public class CreateQuestionFromDBController extends BasicController {



	@FXML
	private TextField qNumber;

	@FXML
	private RadioButton A, B, C, D;

	@FXML
    private ToggleGroup CorectAnswer;

	@FXML
	private Button savebutton,cancelbutton, logo;

	@FXML
	private TextField body, qA, qB, qC, qD;

	@FXML
	private ComboBox<String> subjectCombobox;

	@FXML
	private ComboBox<String> courseCombobox;

	@FXML
	private Label live_time;

	// ############################### Local Variables
	// #################################################################
	public static String subjectID, courseID;

	ObservableList<String> subjectList;
	ObservableList<String> courseList;
	public static String testCount;
	private Test test = new Test();

	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
	public static void setSubjectID(String subject) {
		CreateQuestionController.subjectID = subject;
	}

	public static String getSubjectID() {
		return subjectID;
	}

	public static void setCourseID(String course) {
		CreateQuestionController.courseID = course;
	}

	public static String getCourseID() {
		return courseID;
	}

	public void loadFilterComboboxes() {
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);

		courseCombobox.getItems().removeAll();
		courseCombobox.setItems(courseList);
	}

	@FXML
	void confirmPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		String correctAnswer = null;

		// Checks if the subject has been picked
		if (subjectCombobox.getValue() == "" || subjectCombobox.getValue() == ""|| subjectCombobox.getValue() == null) {
			subjectCombobox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Subject Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			subjectCombobox.setStyle("-fx-background-color: white;");
		}

		// Checks if the course has been picked
		if (courseCombobox.getValue() == "" || courseCombobox.getValue() == " " || courseCombobox.getValue() == null) {
			courseCombobox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Course Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			courseCombobox.setStyle("-fx-background-color: white;");
		}
		if (body.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions text !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		if (qA.getText().isEmpty()||qB.getText().isEmpty()||qC.getText().isEmpty()||qD.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions all answer !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		if (qNumber.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add question number !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		try {
    		int number = Integer.parseInt(qNumber.getText());
			if (number < 1 || number > 999 ) {
				JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{
				int lenght = qNumber.getText().length();
				if (lenght ==1)
				qNumber.setText("00" + qNumber.getText ());
				if (lenght ==2)
				qNumber.setText("0" + qNumber.getText ());
				if (lenght > 3){
					JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} catch (NumberFormatException e) {
    			JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
				return;
		}

		if (!A.isSelected() && !B.isSelected() && !C.isSelected() && !D.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select correct answer !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 
			

		// checks who is the correct answer
		if (A.isSelected())
			correctAnswer = "a";
		else if (B.isSelected())
			correctAnswer = "b";
		else if (C.isSelected())
			correctAnswer = "c";
		else if (D.isSelected())
			correctAnswer = "d";

		ClientUI.updatestatus = 1;

		sendQandANStoSQL(subjectCombobox.getValue(),courseCombobox.getValue(), body.getText(), qNumber.getText(), qA.getText(), qB.getText(),qC.getText(), qD.getText(), correctAnswer);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		if (ClientUI.updatestatus == 0){
		JOptionPane.showMessageDialog(null, "Changes NOT Saved! Qustion Number Exist In DB", "Fail!", JOptionPane.WARNING_MESSAGE);
		ClientUI.updatestatus =1;
			return;
		}
		JOptionPane.showMessageDialog(null, "Changes Saved!", "Success!", JOptionPane.WARNING_MESSAGE);
			test.setAuthor(ClientHandler.user.getpName());
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		ClientUI.chat.GetLecturersQuestions("*");
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
				"CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
		
	}

	public void sendQandANStoSQL(String subject,String course,String qBody, String qnumber, String optionA, String optionB,
			String optionC, String optionD, String correctAnswer) {
		// getting subject id from data
		ClientUI.chat.GetSubjectIDfromSubjectCourses(subject);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		subjectID =CreateQuestionController.getSubjectID();
		System.out.println("CreateQuestion: " + subjectID);
		subjectID += qnumber;
		ClientUI.chat.CreateQuestion(subjectID, subject,course, qBody, qnumber);
		ClientUI.chat.CreateAnswers(optionA, optionB, optionC, optionD,correctAnswer,subjectID);
	}

	@FXML
	void addAnotherPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		String correctAnswer = null;
		// Checks if the subject has been picked
		if (subjectCombobox.getValue() == "" || subjectCombobox.getValue() == ""|| subjectCombobox.getValue() == null) {
			subjectCombobox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Subject Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			subjectCombobox.setStyle("-fx-background-color: white;");
		}

		// Checks if the course has been picked
		if (courseCombobox.getValue() == "" || courseCombobox.getValue() == " " || courseCombobox.getValue() == null) {
			courseCombobox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Course Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			courseCombobox.setStyle("-fx-background-color: white;");
		}

		if (body.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions text !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		if (qA.getText().isEmpty()||qB.getText().isEmpty()||qC.getText().isEmpty()||qD.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add questions all answer !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		if (qNumber.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please add question number !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 

		try {
    		int number = Integer.parseInt(qNumber.getText());
			if (number < 1 || number > 999 ) {
				JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{
				int lenght = qNumber.getText().length();
				if (lenght ==1)
				qNumber.setText("00" + qNumber.getText ());
				if (lenght ==2)
				qNumber.setText("0" + qNumber.getText ());
				if (lenght > 3){
					JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} catch (NumberFormatException e) {
    			JOptionPane.showMessageDialog(null, "Question number must be between 1 and 999 !", "Error", JOptionPane.ERROR_MESSAGE);
				return;
		}
		if (!A.isSelected() && !B.isSelected() && !C.isSelected() && !D.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select correct answer !", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 
			
		// checks who is the correct answer
		if (A.isSelected())
			correctAnswer = "a";
		else if (B.isSelected())
			correctAnswer = "b";
		else if (C.isSelected())
			correctAnswer = "c";
		else if (D.isSelected())
			correctAnswer = "d";

		ClientUI.updatestatus =1;
		sendQandANStoSQL(subjectCombobox.getValue(),courseCombobox.getValue(), body.getText(), qNumber.getText(), qA.getText(), qB.getText(),qC.getText(), qD.getText(), correctAnswer);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		if (ClientUI.updatestatus == 0){
		JOptionPane.showMessageDialog(null, "Changes NOT Saved! Qustion Number Exist In DB", "Fail!", JOptionPane.WARNING_MESSAGE);
		ClientUI.updatestatus =1;
			return;
		}
		JOptionPane.showMessageDialog(null, "Changes Saved!", "Success!", JOptionPane.WARNING_MESSAGE);

		correctAnswer = null;
		subjectCombobox.setStyle("-fx-background-color: white;"); // Set red background color
		subjectCombobox.setValue("");
		courseCombobox.setStyle("-fx-background-color: white;"); // Set red background color
		courseCombobox.setValue("");
		body.setText("");
		qNumber.setText("");
		qA.setText("");
		qB.setText("");
		qC.setText("");
		qD.setText("");
		A.setSelected(false);
		B.setSelected(false);
		C.setSelected(false);
		D.setSelected(false);

	}

	@FXML
	void cancelPressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getpName());
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		ClientUI.chat.GetLecturersQuestions("*");
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
				"CEMS System - Lecturer - Create Test - Question Data Base", event);
		
		dbq.load(test);
	}

	@FXML
	void backPressed(ActionEvent event) {
		test.setAuthor(ClientHandler.user.getpName());
		LecturerController.setQuestions(new ArrayList<QuestionModel>());
		ClientUI.chat.GetLecturersQuestions("*");
		DBQController dbq = (DBQController) openScreen("/clientFXMLS/LecturerDBQ.fxml",
				"CEMS System - Lecturer - Create Test - Question Data Base", event);
		dbq.load(test);
	}
	public void setTest(Test testToReturn) {
		test = testToReturn;
	}
}