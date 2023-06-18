package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

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
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private TextField questionBody;
    
    //load relevant data!!
    public void load(String test_id) {
        this.test_id = test_id;
        try {
            ClientUI.chat.getTestFromId(test_id);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void NextQuestionPressed(ActionEvent event) {

    }

    @FXML
    void qComboBox(MouseEvent event) {

        // loadQuestions(questionList);

    }

    @FXML
    void submitPressed(ActionEvent event) {

    }

    // public void loadQuestions(ArrayList<String> list) {
    // ObservableList<String>questionList = FXCollections.observableArrayList(list);
    // qComboBox.getItems().removeAll();
    // qComboBox.setItems(questionList);
    // }

   

}
