package clientControllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Super Class for controllers to save some codespace
 */
public class BasicController {

	/**
	 * Method for opening a screen from given fxml path and title.
	 * 
	 * @param fxml  fxml path.
	 * @param title title of the screen.
	 * @param event the trigger event.
	 */
	public BasicController openScreen(String fxml, String title, ActionEvent event) {
		// If an event is provided, hide the window that the event originated from.
		if (event != null) {
			((Node) event.getSource()).getScene().getWindow().hide();
		}

		// Create an array to store offsets for window dragging.
		final double[] offsets = new double[2];

		// Create an AnchorPane to serve as the root of the scene.
		AnchorPane root = null;

		// Create a new Stage for the window.
		Stage currentStage = new Stage();

		// Remove the window's top panel bar.
		currentStage.initStyle(StageStyle.UNDECORATED);

		// Create a FXMLLoader to load the FXML file.
		FXMLLoader loader = new FXMLLoader();

		try {
			// Load the FXML file and set the root of the scene.
			root = loader.load(getClass().getResource(fxml).openStream());
		} catch (IOException e) {
			// If an IOException occurs, print the stack trace.
			e.printStackTrace();
		}

		// Create a new Scene with the root.
		Scene scene = new Scene(root);

		// Add a stylesheet to the scene.
		scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());

		// Add a mouse pressed event handler to the root.
		// This is used to get the initial mouse position for window dragging.
		root.setOnMousePressed(mouseEvent -> {
			offsets[0] = mouseEvent.getSceneX();
			offsets[1] = mouseEvent.getSceneY();
		});

		// Add a mouse dragged event handler to the root.
		// This is used to update the window position as the mouse is dragged.
		root.setOnMouseDragged(mouseEvent -> {
			currentStage.setX(mouseEvent.getScreenX() - offsets[0]);
			currentStage.setY(mouseEvent.getScreenY() - offsets[1]);
		});

		// Set the scene of the stage.
		currentStage.setScene(scene);

		// Set the title of the stage.
		currentStage.setTitle(title);

		// Show the stage.
		currentStage.show();

		// Return the controller of the loaded FXML file.
		return loader.getController();
	}

	/**
	 * Method for opening a popup screen from given fxml path and title.
	 * 
	 * @param fxml              fxml path.
	 * @param title             title of the screen.
	 * @param shouldBeBasicCtrl the controller incharge of the screen.
	 * @param currentComment    the comment currently saved.
	 */
	public void openPopupCommentScreen(String fxml, String title, CreateTestController shouldBeBasicCtrl,
			String currentComment) {

		// Load the FXML file for the pop-up window
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		try {
			// CreateTestController ctc = (CreateTestController) shouldBeBasicCtrl;

			loader.setController(shouldBeBasicCtrl);
			AnchorPane root = loader.load();
			shouldBeBasicCtrl.setComments(currentComment);

			// Create a new stage for the pop-up window
			Stage popupStage = new Stage();
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setTitle(title);

			// Set the scene with the loaded FXML layout
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
			popupStage.setScene(scene);

			// Show the pop-up window and wait until it finishes
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for opening a popup screen from given fxml path and title.
	 * 
	 * @param fxml              fxml path.
	 * @param title             title of the screen.
	 * @param shouldBeBasicCtrl the controller incharge of the screen.
	 * @param currentComment    the comment currently saved.
	 */
	public void openPopupScreen(String fxml, String title, BasicController basicCtrl) {

		// Load the FXML file for the pop-up window
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		try {
			// CreateTestController ctc = (CreateTestController) shouldBeBasicCtrl;

			loader.setController((StudentExamController) basicCtrl);
			AnchorPane root = loader.load();

			// Create a new stage for the pop-up window
			Stage popupStage = new Stage();
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setTitle(title);

			// Set the scene with the loaded FXML layout
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
			popupStage.setScene(scene);

			// Show the pop-up window and wait until it finishes
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is triggered when the logout button is pressed. It performs
	 * several actions:
	 * <ul>
	 * <li>Hides the current window. The source of the event is cast to a Node, and
	 * then the window that the node belongs to is retrieved and hidden.</li>
	 * <li>Resets client data using a method from the ClientHandler class.</li>
	 * <li>Quits the chat using a method from the ClientUI class.</li>
	 * <li>Opens the login screen. The path to the FXML file for the login screen is
	 * provided, along with the title for the new window.</li>
	 * </ul>
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void logoutPressed(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		ClientHandler.resetClientData();
		ClientUI.chat.quit();
		openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS System - Login", event);
	}

	/**
	 * This method is triggered when the back button is pressed in the lecturer
	 * screen. It performs two main actions:
	 * <ul>
	 * <li>Opens the lecturer screen and casts the returned controller to a
	 * LecturerController.</li>
	 * <li>Sets the welcome label on the lecturer screen.</li>
	 * </ul>
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void backToLecturer(ActionEvent event) {
		LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer",
				event);
		lc.setWelcomeLabel();
	}

	/**
	 * This method is triggered when the back button is pressed in the student
	 * screen.
	 * It is responsible for handling the transition back to the student screen.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void backToStudent(ActionEvent event) {
		// Open the student screen.
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
	}

	/**
	 * This method is triggered when the back button is pressed in the HOD (Head of
	 * Department) screen.
	 * It is responsible for handling the transition back to the HOD screen.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void backToHOD(ActionEvent event) {
		openScreen("/clientFXMLS/HOD.fxml", "CEMS System - Student", event);
	}

	/**
	 * This method is triggered when the exit button is pressed.
	 * It is responsible for handling the termination of the application.
	 *
	 * @param event The action event that triggers this method.
	 */
	@FXML
	void exitPressed(ActionEvent event) {
		// Show a confirmation dialog asking if the user is sure they want to exit.
		// If the user selects "Yes", then proceed with the exit.
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			// Hide the current window.
			((Node) event.getSource()).getScene().getWindow().hide();
			// Reset client data.
			ClientHandler.resetClientData();
			// Quit the chat.
			ClientUI.chat.quit();
		} else
			// If the user selects "No", then do nothing and return from the method.
			return;
	}

	// Declare a volatile boolean variable 'stop'.
	// The volatile keyword in Java is used as an indicator to the Java compiler
	// to always read the value of the volatile variable from the main memory,
	// not from the thread's local cache.
	private volatile boolean stop = false;

	/**
	 * This method is used to update the provided label with the current time.
	 *
	 * @param live_time The label that will be updated with the current time.
	 */
	public void Timenow(Label live_time) {
		// Create a new Thread object.
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// Create a SimpleDateFormat object 'sdf' and set the date format to "hh:mm:ss".
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				// Start a while loop that continues until 'stop' is true.
				while (!stop) {
					try {
						// Make the thread sleep for 1000 milliseconds (1 second).
						Thread.sleep(1000);
					} catch (Exception e) {
						// If an exception occurs, print the exception.
						System.out.println(e);
					}
					// Get the current date and time, format it according to 'sdf',
					// and store it in the String variable 'timenow'.
					final String timenow = sdf.format(new Date());
					// Use the Platform's 'runLater' method to ensure that the GUI update
					// is performed by the JavaFX Application thread.
					Platform.runLater(() -> {
						// Set the text of the 'live_time' label to 'timenow'.
						live_time.setText(timenow);
					});
				}
			}
		});
		// Start the thread.
		thread.start();
	}

}
