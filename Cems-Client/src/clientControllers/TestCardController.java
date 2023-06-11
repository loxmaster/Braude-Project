package clientControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.Statistics;
import logic.Test;

public class TestCardController {

    @FXML
    private Text code_Exam;

    @FXML
    private Text grade;

    @FXML
    private AnchorPane studentexamcard;

    @FXML
    private Text test_name;

    private String [] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};

    public void setCard(Test  test){
        code_Exam.setText(test.getId());
        test_name.setText(test.getSubject());
        grade.setText(test.getGrade());
        // code_Exam.setText("020301");
        // test_name.setText("algebra1");
        // grade.setText("69");
        studentexamcard.setStyle("-fx-background-color: #"+colors[(int)Math.random()*colors.length]
        +";"+ "-fx-background-radius: 15;"+"-fx-effect: dropShadown(three-pass-box, rgba(0,0,0,1),10,0,0,10);");

    }
}
