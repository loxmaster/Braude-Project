package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;
import logic.Test;


public class DBQController extends BasicController {
	private Test testToReturn;
	
	@FXML
    private TableColumn<QuestionModel, CheckBox> Check;

    @FXML
    private TableColumn<QuestionModel, String> LecturerName;

    @FXML
    private TableColumn<QuestionModel, String> Question;

    @FXML
    private TableColumn<QuestionModel, String> QuestionID;
	
	@FXML
    private Button exitbutton;

    @FXML
    private Button logo;
	
	@FXML
	private TableView<QuestionModel> table;

	public void load(Test test) {

		testToReturn = test;
		
		Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
        LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
        QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));

		if (LecturerController.questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
            table.setItems(questionList);
        }
	}

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Remembers the questions that needs to be added
		ObservableList<QuestionModel> tempQuestionList = table.getItems();
		ArrayList<QuestionModel> questionsToAdd = new ArrayList<QuestionModel>();

		for (int i = 0 ; i < tempQuestionList.size() ; i++) {
			//System.out.println(tempQuestionList.get(i));
			//if(Check.getCellObservableValue(tempQuestionList.get(i)).getValue().isSelected())
				//questionsToAdd.add(tempQuestionList.get(i));
		}


		testToReturn.setQuesitonsInTest(LecturerController.getQuestions());
		// open Create Tests back with already updated test
		CreateTestController ctc = (CreateTestController)openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
		ctc.setTest(testToReturn);
	}

	@FXML
	void backPressed(ActionEvent event) {
		// open Create Tests 
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
	}

	@FXML
	void createQuestionPressed(ActionEvent event) {
		// Open Create Question screen
		openScreen("/clientFXMLS/LecturerCreateQFromDB.fxml", "CEMS System - Lecturer - Create Tests - Create Questions", event);
	}

	@FXML
	void helpPressed(ActionEvent event) {

	}
}
