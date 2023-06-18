package clientControllers;

import java.util.ArrayList;
import java.util.HashSet;

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

public class EditQuestionController extends BasicController {

	private ObservableList<String> subjectList;
	private ObservableList<String> courseList;
	private String originalId;

	@FXML
	private ToggleGroup editGroupEditQuestion;

	@FXML
	private TextField qA, qB, qC, qD;

	@FXML
	private Button BtnInfo;

	@FXML
	private TextField body;

	@FXML
	private RadioButton A, B, C, D;

	@FXML
	private TextField qNumber;

	@FXML
	private ComboBox<String> subjectCombobox;

	@FXML
	private ComboBox<String> courseComboBox;

	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	public void loadEditQuestionScreen() {
		subjectList = FXCollections.observableArrayList(LecturerController.getSubjectsList());
		courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());

		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);

		courseComboBox.getItems().removeAll();
		courseComboBox.setItems(courseList);

	}

	// load question details under 'Edit Question'
	public void loadQuestion(QuestionModel question, String id) {
		// loadEditQuestionScreen();

		body.setText(question.getQuestiontext());
		HashSet<String> subjectUpdated = new HashSet<>();
		HashSet<String> courseUpdated = new HashSet<>();

		subjectUpdated.addAll(LecturerController.subjectsList);
		subjectUpdated.add(question.getSubject());

		courseUpdated.addAll(LecturerController.coursesList);
		courseUpdated.add(question.getCoursename());

		subjectList = FXCollections.observableArrayList(subjectUpdated);
		courseList = FXCollections.observableArrayList(courseUpdated);

		courseComboBox.setItems(courseList);
		courseComboBox.setValue(question.getCoursename());
	

		subjectCombobox.setItems(subjectList);
		subjectCombobox.setValue(question.getSubject());

		qA.setText(question.getOptionA());
		qB.setText(question.getOptionB());
		qC.setText(question.getOptionC());
		qD.setText(question.getOptionD());

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

		qNumber.setText(question.getQuestionnumber());

		originalId = id;
	}

	@FXML
	void SavePressed(ActionEvent event) {

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
		if (courseComboBox.getValue() == "" || courseComboBox.getValue() == " " || courseComboBox.getValue() == null) {
			courseComboBox.setStyle("-fx-background-color: red;"); // Set red background color
			JOptionPane.showMessageDialog(null, "Course Not Picked!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			courseComboBox.setStyle("-fx-background-color: transparent;");
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
		sendQandANStoSQL(subjectCombobox.getValue(),courseComboBox.getValue(), body.getText(), qNumber.getText(), qA.getText(), qB.getText(),qC.getText(), qD.getText(), correctAnswer);
		
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

		

        LecturerController.setQuestions(new ArrayList<QuestionModel>());
        ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
        
        // Open the LecturerQuestionsTableController and load the questions into the table
        LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
        lqtc.loadTable();

	}

	
	public void sendQandANStoSQL(String subject,String course,String qBody, String qnumber, String optionA, String optionB,
			String optionC, String optionD, String correctAnswer) {
		// getting subject id from data
		String subjectid;
		ClientUI.chat.GetSubjectIDfromSubjectCourses(subject);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		subjectid = CreateQuestionController.getSubjectID();
		System.out.println("CreateQuestion: " + subjectid);
		subjectid += qnumber;
		ClientUI.chat.EditQuestion(subjectid, subject,course, qBody, qnumber,originalId);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ClientUI.chat.EditAnswers(subjectid, optionA,optionB, optionC ,optionD, correctAnswer);
	}


	@FXML
	void backPressed(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen(
				"/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
		lqtc.loadTable();
	}

	@FXML
	void cancelPressed(ActionEvent event) {
		// TODO cancel does the same as back maybe remove the 'Back' utton?
		backPressed(event);
	}

	@FXML
    void DeletePressed(ActionEvent event) {
		ClientUI.updatestatus = 1;

		ClientUI.chat.DeleteQuestion(originalId);

		if (ClientUI.updatestatus == 0){
		JOptionPane.showMessageDialog(null, "Question NOT Deleted! Qustion Number Exist In DB", "Fail!", JOptionPane.WARNING_MESSAGE);
		ClientUI.updatestatus =1;
			return;
		}
		JOptionPane.showMessageDialog(null, "Question Deleted!", "Success!", JOptionPane.WARNING_MESSAGE);

		
        LecturerController.setQuestions(new ArrayList<QuestionModel>());
        ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
        
        // Open the LecturerQuestionsTableController and load the questions into the table
        LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
        lqtc.loadTable();

    }
}
