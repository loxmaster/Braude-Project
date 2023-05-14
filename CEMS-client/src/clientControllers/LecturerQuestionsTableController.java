package clientControllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.QuestionModel;

public class LecturerQuestionsTableController extends BasicController{

    @FXML
    private Button BtnInfo;

    @FXML
    private TableView<QuestionModel> table;
    
    @FXML
    private TableColumn<QuestionModel, String> answer;

    @FXML
    private TableColumn<QuestionModel, Button> edit;

    @FXML
    private TableColumn<QuestionModel, Integer> id;

    @FXML
    private TableColumn<QuestionModel, String> question;

    public void loadTable(ArrayList<String> list) {

        answer.setCellValueFactory(new PropertyValueFactory<>("Answer"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        question.setCellValueFactory(new PropertyValueFactory<>("Question"));

        ArrayList<QuestionModel> qList = new ArrayList<QuestionModel>();
        for (String s : list) {
            qList.add(new QuestionModel(s));
        }
        ObservableList<QuestionModel> questions = FXCollections.observableArrayList(qList);
        table.setItems(questions);
    }

    @FXML
    void backPressed(ActionEvent event) {

    }

    @FXML
    void helpPressed(ActionEvent event) {

    }

    @FXML
    void LogoutPressed(ActionEvent event) {
        logoutPressed(event);
    }

}
