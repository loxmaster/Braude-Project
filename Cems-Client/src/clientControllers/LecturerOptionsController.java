package clientControllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LecturerOptionsController extends BasicController {

    @FXML
    private Button BtnInfo;

    @FXML
    private Label welcomeLabel;

    @FXML
    void CreateQuestionPressed(ActionEvent event) throws IOException {
        if (LecturerController.subjectsList.isEmpty())
            JOptionPane.showMessageDialog(null, "Lecturer has no subjects !", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            CreateQuestionController cqc = (CreateQuestionController) openScreen("/clientFXMLS/LecturerCreateQ.fxml", "CEMS System - Lecturer - Create Question", event);
            cqc.loadSubjects(LecturerController.subjectsList);
        }
    }
 
    @FXML
    void UpdateQuestionPressed(ActionEvent event) throws IOException {
        // Sends data pull request
        ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
        
        // opens the question data base screen for this lecturer
        // and loads the questions into table.
        LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
        lqtc.loadTable(LecturerController.questions);
    }

    @FXML
    void HelpPressed(ActionEvent event) {

    }
}