package serverUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.BackgroundImage;
//import javafx.scene.layout.BackgroundPosition;
//import javafx.scene.layout.BackgroundRepeat;
//import javafx.scene.layout.BackgroundSize;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginScreenFX extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		AnchorPane root;
		FXMLLoader loader = new FXMLLoader();
		stage.setTitle("CEMS System - Login");
		loader.setLocation(getClass().getResource("/serverFXMLS/LoginScreen.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// Image backgroundImage = new Image("Login.jpg");
		// BackgroundImage background = new BackgroundImage(backgroundImage,
		// BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		// BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		// Background transparentBackground = new Background(new
		// BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
		// BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new
		// BackgroundSize(1.0, 1.0, true, true, false, true)));
		// BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(0, 0, 0, 0.5),
		// null, null);
		// root.setBackground(transparentBackground);
		// transparentBackground.setBackgroundFill(new BackgroundFill(Color.rgb(0, 0, 0,
		// 0.5), null, null));

		// Background transparentBackground = new Background(new
		// BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
		// BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new
		// BackgroundSize(1.0, 1.0, true, true, false, true)), new
		// BackgroundFill(Color.rgb(0, 0, 0, 0.5), null, null));

		Scene scene = new Scene(root);
		// Image backgroundImage = new Image("Login.jpg");
		// BackgroundImage background = new BackgroundImage(backgroundImage,
		// BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		// BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		// scene.
		stage.setScene(scene);
		stage.show();
	}
}