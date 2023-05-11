package clientControllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class BasicController {
	public void openScreen(String fxml, Stage currentStage) {
		AnchorPane root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/clientFXMLS/background.css");
		//scene.getStylesheets().add(getClass().getResource("/clientFXMLS/background.css").toExternalForm());
		currentStage.setScene(scene);
	}
}
