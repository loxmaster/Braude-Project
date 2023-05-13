package clientControllers;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import logic.QuestionModel;

public class LecturerQuestionsTableController {

    @FXML
    private Button BtnInfo;

    @FXML
    private TableView<QuestionModel> table;
    

    public void loadTable(ArrayList<String> list) {
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
    void logoutPressed(ActionEvent event) {

    }

}
