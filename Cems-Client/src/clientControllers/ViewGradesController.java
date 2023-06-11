package clientControllers;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import logic.Test;

public class ViewGradesController extends BasicController {

    @FXML
    private GridPane ExamContainer;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private ToggleButton backButton;

    @FXML
    private List<Test> Exams;

    @FXML
    void backButtonPressed(ActionEvent event) {
        // Loading student main screen 
        openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
    }

    @FXML
    void ExamLoad(){
        Exams = new ArrayList<>(Examstoadd());
        int column=0;
        int row=1;
    try {
        for(Test test : Exams){
           FXMLLoader fxmlLoader = new FXMLLoader();
           AnchorPane testcard = fxmlLoader.load(getClass().getResource("/clientFXMLS/StudentTestCard.fxml").openStream()); 
           TestCardController cardController = fxmlLoader.getController();
           cardController.setCard(test);
           if(column == 4)
           {
            column = 0;
            ++row;
           }

           ExamContainer.add(testcard, column++, row);
           GridPane.setMargin(testcard, new Insets(10));
           
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    private List<Test> Examstoadd(){
        List<Test> ls = new ArrayList<>();
        Test test = new Test("020301","algebra1","misha","020211","69");  
        ls.add(test);
        test = new Test("020301","algebra1","misha2","0211","6");  
        ls.add(test);
        test = new Test("020301","algebra1","misha3","056411","100");  
        ls.add(test);
        return ls;
  }

}