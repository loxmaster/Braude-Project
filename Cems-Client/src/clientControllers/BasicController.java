package clientControllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;

/**
 * Super Class for controllers to save some codespace
 */
public class BasicController {

	/**
	 * Method for opening a screen from given fxml path and title.
	 * 
	 * @param fxml fxml path.
	 * @param title title of the screen.
	 * @param event the triger event.
	 */ 
	public BasicController openScreen(String fxml, String title ,ActionEvent event) {
		if(event != null){
			((Node) event.getSource()).getScene().getWindow().hide();
		}
		final double[] offsets = new double[2];
		AnchorPane root = null;
		Stage currentStage = new Stage();
		currentStage.initStyle(StageStyle.UNDECORATED); // Removes the windows top panel bar
		FXMLLoader loader = new FXMLLoader();
		try {
			root = loader.load(getClass().getResource(fxml).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
		
        // Add mouse event handlers for window movement
        root.setOnMousePressed(mouseEvent -> {
            offsets[0] = mouseEvent.getSceneX();
            offsets[1] = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            currentStage.setX(mouseEvent.getScreenX() - offsets[0]);
            currentStage.setY(mouseEvent.getScreenY() - offsets[1]);
        });

		currentStage.setScene(scene);
		currentStage.setTitle(title);
		currentStage.show();
		return loader.getController();
	}

	/**
	 * Method for opening a popup screen from given fxml path and title.
	 * @param fxml fxml path.
	 * @param title title of the screen.
	 * @param shouldBeBasicCtrl the controller incharge of the screen.
	 * @param currentComment the comment currently saved.
	 */
	public void openPopupCommentScreen(String fxml, String title, CreateTestController shouldBeBasicCtrl, String currentComment) {
		
		// Load the FXML file for the pop-up window
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		try {
			//CreateTestController ctc = (CreateTestController) shouldBeBasicCtrl;
			
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

	// logout method is same for everyone, goes back to the main login screen.
	@FXML
	void logoutPressed(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
        ClientHandler.resetClientData();
		ClientUI.chat.quit();
		openScreen("/clientFXMLS/LoginScreen.fxml", "CEMS System - Login", event);
	}

	@FXML
	void backToLecturer(ActionEvent event) {
		LecturerController lc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
		lc.setWelcomeLabel();
	}

	@FXML
	void backToStudent(ActionEvent event) {
		openScreen("/clientFXMLS/StudentScreen.fxml", "CEMS System - Student", event);
	}

	@FXML
	void exitPressed(ActionEvent event) {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		((Node) event.getSource()).getScene().getWindow().hide();
        ClientHandler.resetClientData();
		ClientUI.chat.quit();}
		else return;
		}

	private volatile boolean stop = false;

		public void Timenow(Label live_time) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				while (!stop) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println(e);
					}
					final String timenow = sdf.format(new Date());
					Platform.runLater(() -> {
						live_time.setText(timenow); // This is the label
					});
				}
			}
		});
		thread.start();
	}
	

}
