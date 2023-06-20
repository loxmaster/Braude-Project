package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.QuestionModel;

public class LecturerOptionsController extends BasicController {

    @FXML
    private Button BtnInfo;

    @FXML
    private Label welcomeLabel;
    
    @FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}
    @FXML
    void CreateQuestionPressed(ActionEvent event) throws IOException {
        if (LecturerController.subjectsList.isEmpty())
            JOptionPane.showMessageDialog(null, "Lecturer has no subjects!", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            // Open the CreateQuestionController and pass the subjects list
            CreateQuestionController ctc = (CreateQuestionController) openScreen("/clientFXMLS/LecturerCreateQ.fxml", "CEMS System - Lecturer - Create Question", event);
           ctc.loadFilterComboboxes();
        }
    }
 
    @FXML
    void UpdateQuestionPressed(ActionEvent event) throws IOException {
        // Sends data pull request
        LecturerController.setQuestions(new ArrayList<QuestionModel>());
        ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
        
        // Open the LecturerQuestionsTableController and load the questions into the table
        LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
        lqtc.loadTable();
    }

    @FXML
    void HelpPressed(ActionEvent event) {
        // Handle the Help button action if needed
    }
}

