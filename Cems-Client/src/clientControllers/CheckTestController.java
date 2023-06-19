package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import logic.QuestionModel;
import logic.Test;

public class CheckTestController extends BasicController {

  private static ArrayList<Test> completedTestsList;


  @FXML
  private Button exitbutton;

  @FXML
  private TableView<?> table;

  @FXML
  private TableColumn<Test, String> test_id;

  @FXML
  private TableColumn<Test, String> subjectName;

  @FXML
  private TableColumn<Test, String> courseName;

  @FXML
  private TableColumn<Test, String> Date;

  @FXML
  private TableColumn<Test, String> student_id;

  @FXML
  private TableColumn<Test, String> Checked;

  @FXML
  private Button logo;

  @FXML
  private Label live_time;

  @FXML
  void initialize() {
    Timenow(live_time); 
  }

  public void loadTable() {

        //Checked.setCellValueFactory(new PropertyValueFactory<>("checked"));
    test_id.setCellValueFactory(new PropertyValueFactory<>("Test ID"));
    subjectName.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    courseName.setCellValueFactory(new PropertyValueFactory<>("Course"));
    Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
    student_id.setCellValueFactory(new PropertyValueFactory<>("Student ID"));
    Checked.setCellValueFactory(new PropertyValueFactory<>("Checked"));
  
    int cap = 20;
        while (LecturerController.getQuestions().isEmpty() && (cap > 0)) {//??
            try {
                Thread.sleep(250);
                cap--;
            } catch (InterruptedException e) {
            }
        }
        if (LecturerController.questions.isEmpty()) {//?
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else {
            ObservableList<QuestionModel> questionList = FXCollections
                    .observableArrayList(LecturerController.questions);
            for (QuestionModel question : questionList){///?
                question.setEdit(createCheckButton(question));
                System.err.println(question.getAnswer() + "ANSWER");
                System.err.println(question.getCoursename() + "COURSE NAME");
                System.err.println(question.getId() + "ID");
                System.err.println(question.getLecturer() + "LECTURER");
                System.err.println(question.getQuestionnumber() + "QUESTION NUMBER");
                System.err.println(question.getQuestiontext() + "QUESTION TEXT");
                System.err.println(question.getSubject() + "SUBJECT");
            }
        }
  }

    //get test data from database



  

    public Button createCheckButton(QuestionModel question) {
      Button edit = new Button("To Check");

      edit.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              // remember all the data of the question
              // send to EditQuestionScreen
              ((Node) event.getSource()).getScene().getWindow().hide();
              AnchorPane root = null;
              Stage currentStage = new Stage();


              FXMLLoader loader = new FXMLLoader();
              try {
                  final double[] offsets = new double[2];
                  root = loader.load(getClass().getResource("/clientFXMLS/EditQuestion.fxml").openStream());//?
                  root.setOnMousePressed(mouseEvent -> {
                      offsets[0] = mouseEvent.getSceneX();
                      offsets[1] = mouseEvent.getSceneY();
                  });
          
                  root.setOnMouseDragged(mouseEvent -> {
                      currentStage.setX(mouseEvent.getScreenX() - offsets[0]);
                      currentStage.setY(mouseEvent.getScreenY() - offsets[1]);
                  });
              } catch (IOException e) {
                  e.printStackTrace();
              }
              EditQuestionController eqc = loader.getController();
              eqc.loadQuestion(question.getQuestion(), question.getId());//?
              System.out.println("opening check tests" + question.getId());
              Scene scene = new Scene(root);
              scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
              currentStage.initStyle(StageStyle.UNDECORATED);
              currentStage.setScene(scene);
              currentStage.setTitle("CEMS System - Lecturer - Check Test");
              currentStage.show();
          }
      });

      edit.setId("checkbutton");
      edit.setPrefWidth(60);
      edit.setPrefHeight(20);
      return edit;
  }
  @FXML
  void backPressed(ActionEvent event) {
    openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
  }

  @FXML
  void backToLecturer(ActionEvent event) {
    openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
  }

}
