package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateQuestionController extends BasicController {

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

	public void loadSubjects(ArrayList<String> list) {
		ObservableList<String> subjectList = FXCollections.observableArrayList(list);
		subjectCombobox.getItems().removeAll();
		subjectCombobox.setItems(subjectList);
	}

	@FXML
	void confirmPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		ArrayList<String> q = new ArrayList<String>();
		String correctAnswer = null;
		String subject = subjectCombobox.getValue();
		String qBody = body.getText();
		String optionA = qA.getText();
		String optionB = qB.getText();
		String optionC = qC.getText();
		String optionD = qD.getText();
		boolean answerA = A.isSelected();
		boolean answerB = B.isSelected();
		boolean answerC = C.isSelected();
		boolean answerD = D.isSelected();

		if (subject == null || qBody == null || optionA == null || optionB == null || optionC == null || optionD == null
				||
				(!answerA && !answerB && !answerC && !answerD)) {
			// error
			JOptionPane.showMessageDialog(null, "Please dont be stupid !", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			// checks who is the correct answer
			if (answerA)
				correctAnswer = "A";
			else if (answerB)
				correctAnswer = "B";
			else if (answerC)
				correctAnswer = "C";
			else if (answerD)
				correctAnswer = "D";

			q.add(subject);
			q.add(qBody);
			q.add(optionA);
			q.add(optionB);
			q.add(optionC);
			q.add(optionD);
			q.add(optionA);
			q.add(correctAnswer);

			LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml",
					"CEMS System - Lecturer", event);
			lc.loadLecturer(ClientHandler.user);
			System.out.println("Opening Lecturer screen...");
		}

		// ClientUI.chat.sendQuestion(q);
	}

	@FXML
	void addAnotherPressed(ActionEvent event) {
		// [subject, qBody, optionA, optionB, optionC, optionD, correctAnswer]
		ArrayList<String> q = new ArrayList<String>();
		String correctAnswer = null;
		String subject = subjectCombobox.getValue();
		String qBody = body.getText();
		String optionA = qA.getText();
		String optionB = qB.getText();
		String optionC = qC.getText();
		String optionD = qD.getText();
		boolean answerA = A.isSelected();
		boolean answerB = B.isSelected();
		boolean answerC = C.isSelected();
		boolean answerD = D.isSelected();

		if (subject == null || qBody == null || optionA == null || optionB == null || optionC == null || optionD == null
				||
				(!answerA && !answerB && !answerC && !answerD)) {
			// error
			JOptionPane.showMessageDialog(null, "Please dont be stupid !", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			// checks who is the correct answer
			if (answerA)
				correctAnswer = "A";
			else if (answerB)
				correctAnswer = "B";
			else if (answerC)
				correctAnswer = "C";
			else if (answerD)
				correctAnswer = "D";

			q.add(subject);
			q.add(qBody);
			q.add(optionA);
			q.add(optionB);
			q.add(optionC);
			q.add(optionD);
			q.add(optionA);
			q.add(correctAnswer);
		}
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

	@FXML
	void cancelPressed(ActionEvent event) {
		// Opens lecturer screen from existing stage
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}

	@FXML
	void backPressed(ActionEvent event) {
		// goes back to options screen
		openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
	}

}
