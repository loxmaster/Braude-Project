package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
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

	/**
	 * Method to reurn to create test screen with the selected questions.
	 * @param event
	 */
	@FXML
	void addQuestionPressed(ActionEvent event) {
		// Remembers the questions that needs to be added
		//setQuestionsToAdd(new ArrayList<QuestionModel>());
		//ArrayList<String> idList = new ArrayList<String>();

		ObservableList<QuestionModel> tempQuestionList = table.getItems();
		ArrayList<QuestionModel> questionsToAdd = new ArrayList<>();

		for (int i = 0 ; i < tempQuestionList.size() ; i++) 
			if(Check.getCellObservableValue(tempQuestionList.get(i)).getValue().isSelected())
				if (!testToReturn.getQuesitonsInTest().contains(tempQuestionList.get(i))) {
					questionsToAdd.add(tempQuestionList.get(i));
					//idList.add(tempQuestionList.get(i).getId());
				}

		
		//ClientUI.chat.getAnswersForQuestion(idList);

		testToReturn.addToQuestions(questionsToAdd);
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

	/*public static ArrayList<QuestionModel> getQuestionsToAdd() {
		return questionsToAdd;
	}

	public static void addToQuestionsToAdd(QuestionModel q) {
		DBQController.questionsToAdd.add(q);
	}

	public static void setQuestionsToAdd(ArrayList<QuestionModel> questionsToAdd) {
		DBQController.questionsToAdd = questionsToAdd;
	}*/

	@FXML
	void helpPressed(ActionEvent event) {

	}
}
