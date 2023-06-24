package serverUI;

import java.io.IOException; 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serverControllers.ServerController;

/**
 * Class for opening the server UI. Also opens and loads the 
 * text into screen.
 */
public class ServerUI extends Application {
	
    // Static instance of ServerController
	public static ServerController sController;

    // Main method that launches the application
	public static void main(String[] args) {
		launch(args);
	}

    // Start method that is called when the application is launched
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Root pane for the UI
		AnchorPane root = null;
		// New stage for the UI
		Stage currentStage = new Stage();
		// FXMLLoader to load the FXML file for the UI
		FXMLLoader loader = new FXMLLoader();
		try {
		    // Load the FXML file into the root pane
			root = loader.load(getClass().getResource("/serverFXMLS/ServerUI.fxml").openStream());
		} catch (IOException e) {
		    // Print the stack trace if an IOException occurs
			e.printStackTrace();
		}
		// Create a new scene with the root pane
		Scene scene = new Scene(root);
		// Add the CSS file to the scene
		//scene.getStylesheets().add(getClass().getResource("/serverFXMLS/background.css").toExternalForm());
		// Get the controller from the FXMLLoader
		sController = loader.getController();
		// Call the load method on the controller
		sController.load();
		// Set the scene on the stage
		currentStage.setScene(scene);
		// Set the title of the stage
		currentStage.setTitle("CEMS System - Server");
		// Show the stage
		currentStage.show();
	}
}
