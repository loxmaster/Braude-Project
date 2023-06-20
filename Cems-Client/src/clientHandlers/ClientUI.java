package clientHandlers;

import java.util.Optional;

import clientControllers.LoginScreenController;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

// public class ClientUI extends Application {
//     public static ClientController chat;
//     public static int updatestatus;

//     public static void main(String[] args) {

//         launch(args);
//     } 
// //new client that logs in will use these as input
//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         // Create an instance of ClientController

//         chat = new ClientController("localhost", 5555);
//         // Create an instance of LoginScreenController
//         LoginScreenController loginController = new LoginScreenController();
//         // Start the login screen
//         loginController.start(primaryStage);
//     }
public class ClientUI extends Application {
    public static ClientController chat;
    public static int updatestatus;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextInputDialog dialog = new TextInputDialog("localhost:5555");
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText("Enter host and port:");
        dialog.setContentText("Please enter host and port (format: host:port):");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String[] hostAndPort = result.get().split(":");
            String host = hostAndPort[0];
            int port = Integer.parseInt(hostAndPort[1]);
            chat = new ClientController(host, port);
            LoginScreenController loginController = new LoginScreenController();
            loginController.start(primaryStage);
        } else {
            System.out.println("No host and port provided. Exiting...");
            System.exit(0);
        }
    }
}

