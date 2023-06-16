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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateQuestionController extends BasicController {
	public static String testcount;

	public static String getTestcount() {
		return testcount;
	}

	public static void setTestcount(String testcount) {
		CreateQuestionController.testcount = testcount;
	}

	public static String subjectID, courseID;

	@FXML
	private TextField qNumber;

	@FXML
	private CheckBox A, B, C, D;

	@FXML
	private Button savebutton;

	@FXML
	private TextField body, qA, qB, qC, qD;

	@FXML
	private ComboBox<String> subjectCombobox;

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

	public void loadSubjects(ArrayList<String> list) {
		ObservableList<String> subjectList = FXCollections.observableArrayList(list);
		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);
	}

	@FXML
	void confirmPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		String correctAnswer = null;
		int countcheck = 0;
		if (A.isSelected()) {
			countcheck++;
		}
		if (B.isSelected()) {
			countcheck++;
		}
		if (C.isSelected()) {
			countcheck++;
		}
		if (D.isSelected()) {
			countcheck++;
		}
		if (subjectCombobox.getValue() == null || qNumber == null || body.getText().isEmpty() || qA.getText().isEmpty()
				|| qB.getText().isEmpty() || qC.getText().isEmpty() || qD.getText().isEmpty()
				|| (!A.isSelected() && !B.isSelected() && !C.isSelected() && !D.isSelected())
				|| countcheck != 1) {
			// error
			JOptionPane.showMessageDialog(null,
					"Please don't leave any fields empty and make sure to select only one correct answer", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// checks who is the correct answer
			if (A.isSelected())
				correctAnswer = "A";
			else if (B.isSelected())
				correctAnswer = "B";
			else if (C.isSelected())
				correctAnswer = "C";
			else if (D.isSelected())
				correctAnswer = "D";

			sendQandANStoSQL(subjectCombobox.getValue(), body.getText(), qNumber.getText(), qA.getText(), qB.getText(),
					qC.getText(), qD.getText(), correctAnswer);
			LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml",
					"CEMS System - Lecturer",
					event);
			lc.loadLecturer(ClientHandler.user);
			System.out.println("Opening Lecturer screen...");
		}
	}

	public void sendQandANStoSQL(String subject, String qBody, String qnumber, String optionA, String optionB,
			String optionC, String optionD, String correctAnswer) {
		// getting subject id from data
		ClientUI.chat.GetSubjectIDfromSubjectCourses(subject);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CreateQuestion: " + subjectID);
		subjectID += qnumber;
		ClientUI.chat.CreateQuestion(subjectID, subject, qBody, qnumber);
		ClientUI.chat.CreateAnswers(optionA, optionB, optionC, optionD, correctAnswer, subjectID);
	}

	@FXML
	void addAnotherPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		String correctAnswer = null;
		int countcheck = 0;
		if (A.isSelected()) {
			countcheck++;
		}
		if (B.isSelected()) {
			countcheck++;
		}
		if (C.isSelected()) {
			countcheck++;
		}
		if (D.isSelected()) {
			countcheck++;
		}
		if (subjectCombobox.getValue() == null || qNumber == null || body.getText().isEmpty() || qA.getText().isEmpty()
				|| qB.getText().isEmpty() || qC.getText().isEmpty() || qD.getText().isEmpty()
				|| (!A.isSelected() && !B.isSelected() && !C.isSelected() && !D.isSelected())
				|| countcheck != 1) {
			// error
			JOptionPane.showMessageDialog(null,
					"Please don't leave any fields empty and make sure to select only one correct answer", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// checks who is the correct answer
			if (A.isSelected())
				correctAnswer = "A";
			else if (B.isSelected())
				correctAnswer = "B";
			else if (C.isSelected())
				correctAnswer = "C";
			else if (D.isSelected())
				correctAnswer = "D";

			sendQandANStoSQL(subjectCombobox.getValue(), body.getText(), qNumber.getText(), qA.getText(), qB.getText(),
					qC.getText(), qD.getText(), correctAnswer);
			correctAnswer = null;
			subjectCombobox.setValue("");
			body.setText("");
			qA.setText("");
			qB.setText("");
			qC.setText("");
			qD.setText("");
			A.setSelected(false);
			B.setSelected(false);
			C.setSelected(false);
			D.setSelected(false);
		}

	}

	@FXML
	void cancelPressed(ActionEvent event) {
		// Opens lecturer screen from existing stage
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// goes back to options screen
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

}
