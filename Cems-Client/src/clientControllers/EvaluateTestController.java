package clientControllers;

import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Question;
import logic.QuestionModel;
import logic.Test;

public class EvaluateTestController extends BasicController {

  private ChangeListener<? super String> questionPointsListener; // Listener for points TextBox
  private ArrayList<QuestionModel> questionlist;
  private int pointsInTest = 0;
  private static Test localtest;

  public static Test getLocaltest() {
    return localtest;
  }

  public static void setLocaltest(Test test) {
    EvaluateTestController.localtest = test;
  }

  @FXML
  private Button BtnInfo;
  @FXML
  private Button savebutton;
  @FXML
  private TextField totalPoints;
  @FXML
  private TextArea qBody;
  @FXML
  private TextArea OptionD;
  @FXML
  private TextArea OptionC;
  @FXML
  private TextArea OptionB;
  @FXML
  private TextArea OptionA;
  @FXML
  private TextField qID;
  @FXML
  private TextArea qPoints;
  @FXML
  private RadioButton A;
  @FXML
  private ToggleGroup toggleGroup;
  @FXML
  private RadioButton B;
  @FXML
  private RadioButton C;
  @FXML
  private RadioButton D;
  @FXML
  private VBox questionTracker;
  @FXML
  private TextField studentGrade;

  // @FXML
  // void initialize() {
  // Timenow(live_time);
  // }

  public void loadTest(Test test) {
    // loadEditQuestionScreen();
    //this.localtest = test;

    //this function init setLocalTest()  the test returned will be in localtest
    ClientUI.chat.getTestWithCodeFor_CompletedTest(test);
    
    try {
      Thread.sleep(250);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(test.getQuesitonsInTest());
    questionlist = test.getQuesitonsInTest();

    int index = 1;
    for (QuestionModel question : localtest.getQuesitonsInTest()) {
      // Creates and adds the question to the VBox
      questionTracker.getChildren().add(createQuestionInTestButton(question, index));
      index++;
    }
    


  }

  public void loadQuestion(QuestionModel question) {
    // loadEditQuestionScreen();

    qBody.setText(question.getQuestiontext());

    OptionA.setText(question.getOptionA());
    OptionB.setText(question.getOptionB());
    OptionC.setText(question.getOptionC());
    OptionD.setText(question.getOptionD());

    switch (question.getAnswer()) {
      case "a":
        A.setSelected(true);
        break;
      case "b":
        B.setSelected(true);
        break;
      case "c":
        C.setSelected(true);
        break;
      case "d":
        D.setSelected(true);
        break;

    }
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

  private HBox createQuestionInTestButton(QuestionModel question, int index) {
    // HBox for the question button and the corresponding delete button
    HBox hBox = new HBox();

    // Creating the question button
    Button questionInTestButton = new Button("Q: " + index);
    questionInTestButton.setPadding(new Insets(4, 4, 4, 4));
    // Handle what happens when you press the button
    questionInTestButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {

        // Defining the total points field listener
        // Removes the current listener
        if (questionPointsListener != null)
          qPoints.textProperty().removeListener(questionPointsListener);
        // Creates new listener for this question and puts it in questionPointsListener
        questionPointsListener = new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            try {
              if (newValue == null)
                question.setPoints("0");
              else
                question.setPoints(newValue);
              updateTotalPoints();
              totalPoints.setText(String.valueOf(pointsInTest));
            } catch (Exception e) {
              question.setPoints("0");
            }
          }
        };
        // Inserts the Listener to the qPoints field
        qPoints.textProperty().addListener(questionPointsListener);

        // Updates the view for the user
        qBody.setText(question.getQuestiontext());
        qBody.setWrapText(true);
        OptionA.setText(question.getOptionA());
        OptionB.setText(question.getOptionB());
        OptionC.setText(question.getOptionC());
        OptionD.setText(question.getOptionD());
        qID.setText(question.getId());
        qPoints.setText("" + question.getPoints());

        A.setSelected(false);
        B.setSelected(false);
        C.setSelected(false);
        D.setSelected(false);

        switch (question.getAnswer()) {
          case "a":
            A.setSelected(true);
            break;
          case "b":
            B.setSelected(true);
            break;
          case "c":
            C.setSelected(true);
            break;
          case "d":
            D.setSelected(true);
            break;

        }
      }
    });

    questionInTestButton.setPrefWidth(70);
    questionInTestButton.setPrefHeight(10);

    // adds the button to the HBox
    hBox.getChildren().add(0, questionInTestButton);

    // Creates a delete button for this question
    Button deleteButton = new Button();
    deleteButton.setId("questionDeleteInTest");
    deleteButton.setPrefWidth(30);
    deleteButton.setPrefHeight(10);
    // deleteButton.set
    // Handles what happens wheb you press the button
    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // Removes the question from the questionTracker (VBox of questions)
        questionTracker.getChildren().remove(hBox);
        // Removes the corresponding question from the current test
        ArrayList<QuestionModel> tempQuestionList = localtest.getQuesitonsInTest();
        tempQuestionList.remove(question);
        localtest.setQuesitonsInTest(tempQuestionList);
      }

    });

    // Adds the button to the HBox
    hBox.getChildren().add(1, deleteButton);
    hBox.setAlignment(Pos.CENTER);

    return hBox;
  }

  private void updateTotalPoints() {
    int totalPoints = 0;
    ArrayList<QuestionModel> tempQuestionList = localtest.getQuesitonsInTest();
    for (QuestionModel question : tempQuestionList)
      totalPoints += Integer.parseInt(question.getPoints());
    pointsInTest = totalPoints;
  }

}
