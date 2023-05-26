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


public class DBQController extends BasicController {

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

	public void loadTable() {

		Check.setCellValueFactory(new PropertyValueFactory<>("CheckBox"));
        LecturerName.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        Question.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
        QuestionID.setCellValueFactory(new PropertyValueFactory<>("Id"));
		
		QuestionModel q = new QuestionModel("01002", "math", "malam", "Why>", "001", "noah");	
		ArrayList<QuestionModel> qArray = new ArrayList<>();
		qArray.add(q);
		ObservableList<QuestionModel> observArray = FXCollections.observableArrayList(qArray);	
		table.setItems(observArray);
		
		// if (LecturerController.questions.isEmpty()) {
        //    JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        //} else {
        //    ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
        //    table.setItems(questionList);
        //}
		
	}

	@FXML
	void addQuestionPressed(ActionEvent event) {
		// open Create Tests 
		openScreen("/clientFXMLS/LecturerCreateTes.fxml", "CEMS System - Lecturer - Create Tests", event);
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
