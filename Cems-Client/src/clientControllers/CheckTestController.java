package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.QuestionModel;
import logic.Test;

public class CheckTestController extends BasicController {

  private static ArrayList<Test> completedTestsList;
  private ChangeListener<? super String> questionPointsListener; // Listener for points TextBox
  private int pointsInTest = 0;

  @FXML
  private Button exitbutton;
  @FXML
  private TableView<Test> table;
  @FXML
  private TableColumn<Test, String> test_id;
  @FXML
  private TableColumn<Test, String> grade;
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

    // get completed tests list initilized
    ClientUI.chat.getcompletedTestsForLecturerList();
    try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
      }
    

    // Checked.setCellValueFactory(new PropertyValueFactory<>("checked"));
    test_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    grade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
    Date.setCellValueFactory(new PropertyValueFactory<>("dateString"));
    student_id.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
    Checked.setCellValueFactory(new PropertyValueFactory<>("check"));

    int cap = 20;
    // wait until we have a questions pulled from database
    while (completedTestsList.isEmpty() && (cap > 0)) {
      try {
        Thread.sleep(250);
        cap--;
      } catch (InterruptedException e) {
      }
    }
    // check if it is actually empty
    if (completedTestsList.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Error getting completed tests!", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
      ObservableList<Test> testlist_observable = FXCollections.observableArrayList(completedTestsList);
      for (Test test : completedTestsList) {/// ?
          test.setCheck(createCheckButton(test));
        }
        FilteredList<Test> filteredList = new FilteredList<>(testlist_observable);
        
       //  ObservableList<Test> testlist_observable = FXCollections.observableArrayList(completedTestsList);


      table.setItems(filteredList);
      //table.setItems(testlist_observable);
     // listener - this will update the table to the filtered COMBOBOX SUBJECT
      // courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable,
      // oldValue, newValue) -> {

     // updatePredicate(filteredList);
    }
  }


  // MANUAL RESOLUTION BUTTON ACTION
  public Button createCheckButton(Test test) {
    Button checked = new Button("Manual Resolution");
    
    checked.setOnAction(new EventHandler<ActionEvent>() {
      
      @Override
      public void handle(ActionEvent event) {
        // remember all the data of the question
        // send to EditQuestionScreen
        ((Node) event.getSource()).getScene().getWindow().hide();
        AnchorPane root = null;
        Stage currentStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        try {
          // open manual resolution
          final double[] offsets = new double[2];
          root = loader.load(getClass().getResource("/clientFXMLS/LecturerEvaluateTest.fxml").openStream());// ?
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
        EvaluateTestController eqc = loader.getController();
        //TODO get question from the test we sent to this method
        eqc.loadTest(test);
        //System.out.println("opening edit question" + question.getId());
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
        currentStage.initStyle(StageStyle.UNDECORATED);
        currentStage.setScene(scene);
        currentStage.show();
      }
    });

    checked.setId("checked");
    checked.setPrefWidth(180);
    checked.setPrefHeight(20);
    return checked;
  }

  @FXML
  void savePressed(ActionEvent event) {

  }

  @FXML
  void editCommentsPressed(ActionEvent event) {

  }

  @FXML
  void backPressed(ActionEvent event) {
    openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
  }

  @FXML
  void backToLecturer(ActionEvent event) {
    openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
  }

  public static ArrayList<Test> getCompletedTestsList() {
    return completedTestsList;
  }

  public static void setCompletedTestsList(ArrayList<Test> completedTestsList) {
    CheckTestController.completedTestsList = completedTestsList;
  }

}
