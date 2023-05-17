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
            CreateQuestionController cqc;
            cqc = (CreateQuestionController) openScreen("/clientFXMLS/LecturerCreateQ.fxml",
                    "CEMS System - Lecturer - Create Question", event);
            cqc.loadSubjects(LecturerController.subjectsList);
        }
    }
 
    @FXML
    void UpdateQuestionPressed(ActionEvent event) throws IOException {

        // creates new thread to go to db and waits for it to finish
        Thread t = new Thread(new Runnable() {
            public void run() {
                ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
            }
        });

        t.start();
        try {
            Thread.sleep(1500);
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (LecturerController.questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error getting the question!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            LecturerQuestionsTableController lqtc;
            lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
            lqtc.loadTable(LecturerController.questions);
        }
    }

    @FXML
    void HelpPressed(ActionEvent event) {

    }
}