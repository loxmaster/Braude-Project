package clientControllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import logic.Test;

public class TestCardController {

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

    public void setCard(Test test, ArrayList<String> SubjectCourse) {
        code_Exam.setText(test.getId());
        test_name.setText(SubjectCourse.get(2));
        if (Integer.parseInt(test.getGrade()) < 55) {
            grade.setStyle("-fx-fill: red;");
        }
        grade.setText(test.getGrade());
        test_date.setText(test.getDateString());
        switch ((int) ((Math.random() * 17)%3)) {

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
            default:
                studentexamcard.setStyle("-fx-background-color: #5490a9"
                        + ";" + "-fx-background-radius: 15;"
                        + "-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");
                break;
        }
    }
}
