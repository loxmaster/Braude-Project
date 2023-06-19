package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    //Checked.setCellValueFactory(new PropertyValueFactory<>("checked"));
    student_id.setCellValueFactory(new PropertyValueFactory<>("Student ID"));
    Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
    courseName.setCellValueFactory(new PropertyValueFactory<>("Course"));
    subjectName.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    test_id.setCellValueFactory(new PropertyValueFactory<>("Test ID"));

    //create a new "manual resolution" button
    

      
  }

    //get test data from database



  

    public Button createEditButton(QuestionModel question) {
      Button edit = new Button("Edit");

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
                  root = loader.load(getClass().getResource("/clientFXMLS/EditQuestion.fxml").openStream());
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
              eqc.loadQuestion(question.getQuestion(), question.getId());
              System.out.println("opening edit question" + question.getId());
              Scene scene = new Scene(root);
              scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
              currentStage.initStyle(StageStyle.UNDECORATED);
              currentStage.setScene(scene);
              currentStage.setTitle("CEMS System - Lecturer - Edit Question");
              currentStage.show();
          }
      });

      edit.setId("editbutton");
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
