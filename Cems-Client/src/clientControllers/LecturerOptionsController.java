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

/**
 * This controller handles the lecturer's options in the application.
 * It extends from the BasicController class.
 */
public class LecturerOptionsController extends BasicController {

    @FXML
    private Button BtnInfo; // Button for displaying information

    @FXML
    private Label welcomeLabel; // Label for displaying the welcome message
    
    @FXML
	private Label live_time; // Label for displaying the live time

	/**
	 * This function initializes the controller.
	 * It starts the clock on the UI.
	 */
	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

    /**
     * This function is called when the Create Question button is pressed.
     * It opens the CreateQuestionController and passes the subjects list to it.
     *
     * @param event The ActionEvent object
     * @throws IOException If an input or output exception occurred
     */
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
 
    /**
     * This function is called when the Update Question button is pressed.
     * It sends a data pull request and opens the LecturerQuestionsTableController, loading the questions into the table.
     *
     * @param event The ActionEvent object
     * @throws IOException If an input or output exception occurred
     */
    @FXML
    void UpdateQuestionPressed(ActionEvent event) throws IOException {
        // Sends data pull request
        LecturerController.setQuestions(new ArrayList<QuestionModel>());
        ClientUI.chat.GetLecturersQuestions(ClientHandler.user.getUsername());
        
        // Open the LecturerQuestionsTableController and load the questions into the table
        LecturerQuestionsTableController lqtc = (LecturerQuestionsTableController) openScreen("/clientFXMLS/LecturerQuestionsTable.fxml", "CEMS System - Lecturer - Questions Table", event);
        lqtc.loadTable();
    }
}

