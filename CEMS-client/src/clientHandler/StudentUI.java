package clientHandlers;

import clientControllers.LoginScreenController;
import javafx.application.Application;
import javafx.stage.Stage;

public class StudentUI extends Application {
    public static StudentController chat;
 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        chat = new StudentController("localhost", 5555);
        LoginScreenController loginController = new LoginScreenController();
        loginController.start(primaryStage);
    }
}
