package clientHandlers;
 
import clientControllers.LoginScreenController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
    public static ClientController chat;
 
    public static void main(String[] args) {
        launch(args);
    }  

    @Override
    public void start(Stage primaryStage) throws Exception {
        chat = new ClientController("localhost", 5555);
        LoginScreenController loginController = new LoginScreenController();
        loginController.start(primaryStage);
    }
}

