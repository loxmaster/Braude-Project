package clientControllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.QuestionModel;
import logic.Test;

public class EvaluateTestController extends BasicController {
  private String student_id;
  private ChangeListener<? super String> questionPointsListener; // Listener for points TextBox
  private ArrayList<QuestionModel> questionlist;
  private static ArrayList<String> selectedAnswers;

  String testcomments;

  public String getTestcomments() {
    return testcomments;
  }

  public void setTestcomments(String testcomments) {
    this.testcomments = testcomments;
  }

  public ArrayList<String> getSelectedAnswers() {
    return selectedAnswers;
  }

  public static void setSelectedAnswers(ArrayList<String> selectedAnswer) {
    selectedAnswers = selectedAnswer;
  }

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
    // this.localtest = test;

    // this function init setLocalTest() the test returned will be in localtest
    ClientUI.chat.getTestWithCodeFor_CompletedTest(test);
    student_id = test.getStudentID();

    int waitCap = 500; // wait max 5 seconds for sql
    while (localtest == null && waitCap != 0) {
      System.out.println("waiting for selected answers...");
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        System.out.println(
            "FAILED AT GRABBING COMPLETED TEST FROM DB (getTestWithCodeFor_CompletedTest EvaluateTestController)");
        e.printStackTrace();
      }
      waitCap--;
    }
    // set grade in Student Grade:
    studentGrade.setText(test.getGrade());
    questionlist = test.getQuesitonsInTest();

    int index = 1;
    for (QuestionModel question : localtest.getQuesitonsInTest()) {
      // Creates and adds the question to the VBox
      questionTracker.getChildren().add(createQuestionInTestButton(question, index));
      index++;
    }
    System.out.println("");

    // get the selected answers and mark them
    ClientUI.chat.getSelectedAnswers(test.getStudentID(), test.getId());

    waitCap = 500; // wait max 5 seconds for sql
    while (selectedAnswers == null && waitCap != 0) {
      System.out.println("i loop in selected answers");
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.out.println("FAILED AT GRABBING ANSWERS FROM DB(getSelectedAnswers EvalucateTestController)");
      }
      waitCap--;
    }
    // reset radio buttons when switching between the sub-questions
    A.setSelected(false);
    B.setSelected(false);
    C.setSelected(false);
    D.setSelected(false);

    // mark the selected answer that the student put in
    for (int i = 0; i < selectedAnswers.size(); i++) {
      switch (selectedAnswers.get(i)) {
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

    EventHandler<ActionEvent> radioButtonHandler = event -> {
      Object source = event.getSource();
      if (source instanceof RadioButton) {
        RadioButton selectedRadioButton = (RadioButton) source;
        String answer = selectedRadioButton.getText();

        // reset radio button styles
        A.setStyle("");
        B.setStyle("");
        C.setStyle("");
        D.setStyle("");

        // Set the style for the selected radio button
        selectedRadioButton.setStyle("-fx-color: green");

        // mark selected answer
        switch (answer) {
          case "a":
            break;
          case "b":
            break;
          case "c":
            break;
          case "d":
            break;
        }
      }
    };

    // Add the EventHandler to the radio buttons
    A.setOnAction(radioButtonHandler);
    B.setOnAction(radioButtonHandler);
    C.setOnAction(radioButtonHandler);
    D.setOnAction(radioButtonHandler);

  }

  public void loadQuestion(QuestionModel question) {
    // set relevant data into the fields:
    qBody.setText(question.getQuestiontext());
    OptionA.setText(question.getOptionA());
    OptionB.setText(question.getOptionB());
    OptionC.setText(question.getOptionC());
    OptionD.setText(question.getOptionD());

    // mark the correct answer in green
    switch (question.getAnswer()) {
      case "a":
        A.setStyle("-fx-color: green");
        break;
      case "b":
        B.setStyle("-fx-color: green");
        break;
      case "c":
        C.setStyle("-fx-color: green");
        break;
      case "d":
        D.setStyle("-fx-color: green");
        break;
    }

  }

  @FXML
  void savePressed(ActionEvent event) {
    int val = Integer.parseInt(totalPoints.getText());
    if (!isGradeValid(val)) {
      JOptionPane.showMessageDialog(null, "Test grade must be under 100 or non negative!", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    ArrayList<String> saveChanges = new ArrayList<>();
    saveChanges.add(localtest.getId());
    saveChanges.add(localtest.getStudentID());
    saveChanges.add(totalPoints.getText());
    saveChanges.add(testcomments);
    ClientUI.updatestatus = 1;
    ClientUI.chat.SendEvaluatedTest(localtest.getId(), student_id, totalPoints.getText(), testcomments);
    if (ClientUI.updatestatus == 0) {
      JOptionPane.showMessageDialog(null, "Changes could not be saved! (idExists)", "Error",
          JOptionPane.ERROR_MESSAGE);
      ClientUI.updatestatus = 1;
      return;
    }
    JOptionPane.showMessageDialog(null, "Changes saved!", "Success!",
        JOptionPane.ERROR_MESSAGE);
    CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml",
        "CEMS System - Lecturer", event);
    ctc.loadTable();
  }

  private boolean isGradeValid(int parseInt) {
    return (parseInt > 100 || parseInt < 0) ? false : true;
  }

  @FXML
  void editCommentsPressed(ActionEvent event) {
    TextInputDialog dialog = new TextInputDialog(testcomments);
    dialog.setTitle("Edit Comments");
    dialog.setHeaderText(null);
    dialog.setContentText("Enter your comments:");

    // Show the dialog and wait for the user's input
    dialog.showAndWait().ifPresent(newComments -> {
      // Save the comments if the user clicked "OK"
      setTestcomments(newComments);
      System.out.println("The Comments: " + newComments);
    });
  }

  @FXML
  void backPressed(ActionEvent event) {
    CheckTestController ctc = (CheckTestController) openScreen("/clientFXMLS/LecturerCheckAutomatingTest.fxml",
        "CEMS System - Lecturer", event);
    ctc.loadTable();
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

        // A.styleProperty().addListener();

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
        A.setStyle("-fx-color:lightgrey;");
        B.setStyle("-fx-color:lightgrey;");
        C.setStyle("-fx-color:lightgrey;");
        D.setStyle("-fx-color:lightgrey;");

        String selectedAnswer = selectedAnswers.get(index - 1);
        switch (selectedAnswer) {
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

        switch (question.getAnswer()) {
          case "a":
            A.setStyle("-fx-color:green;");
            break;
          case "b":
            B.setStyle("-fx-color:green;");
            break;
          case "c":
            C.setStyle("-fx-color:green;");
            break;
          case "d":
            D.setStyle("-fx-color:green;");
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
