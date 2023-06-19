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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.QuestionModel;

public class LecturerQuestionsTableController extends BasicController {

    ObservableList<String> courseList;

    @FXML
    private Button BtnInfo;

    @FXML
    private TableView<QuestionModel> table;

    @FXML
    private TableColumn<QuestionModel, String> id;

    @FXML
    private TableColumn<QuestionModel, String> subject;

    @FXML
    private TableColumn<QuestionModel, String> coursename;

    @FXML
    private TableColumn<QuestionModel, String> questiontext;

    @FXML
    private TableColumn<QuestionModel, String> questionnumber;

    @FXML
    private TableColumn<QuestionModel, String> lecturer;

    @FXML
    private TableColumn<QuestionModel, Button> edit;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    public void loadFilterComboboxes() {
        courseList = FXCollections.observableArrayList(LecturerController.getCoursesList());
        courseComboBox.getItems().removeAll();
        courseComboBox.setItems(courseList);
    }

    private void updatePredicate(FilteredList<QuestionModel> filteredList) {
		String selectedCourse = courseComboBox.getValue();

		// add new filters here as needed, dont forget to add a new listener
		filteredList.setPredicate(questionModel -> {
			return selectedCourse == null || selectedCourse.isEmpty()
					|| questionModel.getCoursename().contains(selectedCourse)
					|| questionModel.getCoursename().contains(selectedCourse);
		});}

    public void loadTable() {
        loadFilterComboboxes();

        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        coursename.setCellValueFactory(new PropertyValueFactory<>("Coursename"));
        questiontext.setCellValueFactory(new PropertyValueFactory<>("Questiontext"));
        questionnumber.setCellValueFactory(new PropertyValueFactory<>("Questionnumber"));

        // Waits 5 seconds for data to be found
        int cap = 20;
        while (LecturerController.getQuestions().isEmpty() && (cap > 0)) {
            try {
                Thread.sleep(250);
                cap--;
            } catch (InterruptedException e) {
            }
        }
        if (LecturerController.questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else {
            ObservableList<QuestionModel> questionList = FXCollections.observableArrayList(LecturerController.questions);
            for (QuestionModel question : questionList){
                question.setEdit(createEditButton(question));
            }
            FilteredList<QuestionModel> filteredList = new FilteredList<>(questionList);
            table.setItems(filteredList);
            // listener - this will update the table to the filtered COMBOBOX SUBJECT
            courseComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                updatePredicate(filteredList);
            });
        }
    }

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
        LecturerController.questions = new ArrayList<QuestionModel>();
        openScreen("/clientFXMLS/LecturerOptions.fxml", "CEMS System - Lecturer", event);
    }

    @FXML
    void helpPressed(ActionEvent event) {

    }

}
