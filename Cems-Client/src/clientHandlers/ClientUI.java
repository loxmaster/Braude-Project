package clientHandlers;

import clientControllers.LoginScreenController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
    public static ClientController chat;
    public static int updatestatus;

    public static void main(String[] args) {
        launch(args);
    } 

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of ClientController
        chat = new ClientController("localhost", 5555);
        // Create an instance of LoginScreenController
        LoginScreenController loginController = new LoginScreenController();
        // Start the login screen
        loginController.start(primaryStage);
    }
}