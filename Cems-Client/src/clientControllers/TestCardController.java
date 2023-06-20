package clientControllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import logic.Test;

public class TestCardController extends BasicController{

    @FXML
    private Text code_Exam;

    @FXML
    private Button ReviewExam;

    @FXML
    private Text grade;

    @FXML
    private AnchorPane studentexamcard;

    @FXML
    private Text test_name;

    @FXML
    private Text test_date;

    @FXML
	private Label live_time;

	

    // Method to set the details of the test card
    public void setCard(Test test, ArrayList<String> SubjectCourse) {
        // Set the exam code
        code_Exam.setText(test.getId());
        // Set the test name
        test_name.setText(SubjectCourse.get(2));
        // If the grade is less than 55, set the text color to red
        if (Integer.parseInt(test.getGrade()) < 55) {
            grade.setStyle("-fx-fill: red;");
        }
        // Set the grade
        grade.setText(test.getGrade());
        // Set the test date
        test_date.setText(test.getDateString());
        // Randomly set the background color of the card
        switch ((int) ((Math.random() * 20)%6)) {
            case 1:
                studentexamcard.setStyle("-fx-background-color: #B9E5FF"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
            case 2:
                studentexamcard.setStyle("-fx-background-color: #BDB2FE"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
            case 3:
                studentexamcard.setStyle("-fx-background-color: #FB9AA8"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
            case 4:
                studentexamcard.setStyle("-fx-background-color: #FFD700"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
            case 5:
                studentexamcard.setStyle("-fx-background-color: #ADFF2F"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
            default:
                studentexamcard.setStyle("-fx-background-color: #5490a9"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
        }
    }
}
