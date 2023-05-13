package clientControllers;

import java.io.IOException;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LecturerOptionsController extends BasicController{

    @FXML
    private Button BtnInfo;

    @FXML
    private Label welcomeLabel;

    @FXML
    void CreateQuestionPressed(ActionEvent event) throws IOException {
        if (LecturerController.subjectsList.isEmpty())
            System.out.println("Couldnt load subjects");
        else {
            ((Node) event.getSource()).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            Stage currentStage = new Stage();
            AnchorPane root = loader.load(getClass().getResource("/clientFXMLS/LecturerCreateQ.fxml").openStream());
            CreateQuestionController cqc = loader.getController();
            cqc.loadSubjects(LecturerController.subjectsList);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
            currentStage.setTitle("CEMS System - Lecturer - Create Questions");
            currentStage.setScene(scene);
            currentStage.show();
        }
    }

    @FXML
    void UpdateQuestionPressed(ActionEvent event) throws IOException {
        
        // goes to question db
        Thread t = new Thread(new Runnable() {
            public void run() {
                ClientUI.chat.GetLeturersQuestions(ClientHandler.user.getUsername());
            }
        });
        //
        t.start();
        try {
            Thread.sleep(1500);
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //
        if (LecturerController.questions.isEmpty()) {
            System.out.println("Error getting the questions. (OptionsController)");
        }
        else {
            ((Node) event.getSource()).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            Stage currentStage = new Stage();
            AnchorPane root = loader.load(getClass().getResource("/clientFXMLS/LecturerQuestionsTable.fxml").openStream());
            LecturerQuestionsTableController lqtc = loader.getController();
            lqtc.loadTable(LecturerController.questions);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
            currentStage.setTitle("CEMS System - Lecturer - Questions Table");
            currentStage.setScene(scene);
            currentStage.show();
        }
    }

    @FXML
    void HelpPressed(ActionEvent event) {

    }

    @FXML
    void LogOutPressed(ActionEvent event) {
        // open Login screen from existing stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openScreen("/clientFXMLS/LoginScreen.fxml", currentStage);
        currentStage.setTitle("CEMS System - Login");
        ClientHandler.resetClientData();
    }

    @FXML
    void backPressed(ActionEvent event) {
        // open Login screen from existing stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        openScreen("/clientFXMLS/Lecturer1.fxml", currentStage);
        currentStage.setTitle("CEMS System - Lecturer");
    }

}