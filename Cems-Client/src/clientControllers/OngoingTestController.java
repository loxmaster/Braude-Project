package clientControllers;

import java.io.IOException;
import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Test;

@SuppressWarnings("unused")

public class OngoingTestController extends BasicController {

	private static ArrayList<String> SubjectCourses;

	@FXML
	private TableView<Test> table;

	@FXML
	private TableColumn<Test, String> Code;

	@FXML
	private TableColumn<Test, String> Subject;

	@FXML
	private TableColumn<Test, String> TimeLeft;

	@FXML
	private TableColumn<Test, Button> AddTime;

	@FXML
	private TableColumn<Test, Button> LockTest;

	@FXML
	private TableColumn<Test, Button> UnlockTest;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private Button backBTN;

	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	public void load() {

		//table.getItems().clear();
		//table.refresh();

		Code.setCellValueFactory(new PropertyValueFactory<>("Code"));
		Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
		TimeLeft.setCellValueFactory(new PropertyValueFactory<>("TimeLeft"));
		AddTime.setCellValueFactory(new PropertyValueFactory<>("AddTime"));
		LockTest.setCellValueFactory(new PropertyValueFactory<>("LockTest"));
		UnlockTest.setCellValueFactory(new PropertyValueFactory<>("UnlockTest"));

		// get the ongoing tests from the db
		ClientUI.chat.GetOngoingTests();

		System.out.println("tests fetched from db");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// get the ongoing tests
		ArrayList<Test> tests = LecturerController.getOngoingTests();

		System.out.println("tests are taken from LecturerController.getOngoingTests()");

		if (tests != null) {
			for (Test test : tests) {
				// get course for test from the test id from db
				ClientUI.chat.getCourseForTest(test.getId());
				System.out.println("course for test getted");
				// get the subject returned from the db and set it to the test subject
				test.setSubject(getSubjectCourses().get(0));

				int cap = 0;
				while (getSubjectCourses().isEmpty() && cap < 20) {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cap++;
				}
				// create first all buttons instances we need
				Button addtimeBTN = new Button("AddTime");
				Button lockBTN = new Button("LockTest");
				Button UnlockBTN = new Button("UnlockTest");
				// create action for them and assign for each test
				test.setAddTime(createAddTimeButton(test, "AddTime", addtimeBTN));
				test.setLockTest(createLockTestButton(test, "LockTest", lockBTN, addtimeBTN, UnlockBTN));
				test.setUnlockTest(createUnlockTestButton(test, "UnlockTest", UnlockBTN, addtimeBTN, lockBTN));

				// check if lock button is pressed and change the viability of the others
				if (test.isLockBtnPressed() == true) {
					lockBTN.setDisable(true);
					addtimeBTN.setDisable(true);
					UnlockBTN.setDisable(false);
					// Change button color to red
					lockBTN.setStyle("-fx-background-color: red;");

				} else {
					lockBTN.setDisable(false);
					addtimeBTN.setDisable(false);
					UnlockBTN.setDisable(true);
					// Change button color to red
					UnlockBTN.setStyle("-fx-background-color: red;");

				}
			}
		}

		ObservableList<Test> testList = FXCollections.observableArrayList(tests);
		table.setItems(testList);

	}

	public Button createAddTimeButton(Test test, String btnName, Button addtime) {

		addtime.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				((Node) event.getSource()).getScene().getWindow().hide();
				AnchorPane root = null;
				Stage currentStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				try {
					root = loader.load(getClass().getResource("/clientFXMLS/TimeExtensionRequest.fxml").openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				// load the test to the next controller for the TimeExtensionRequest screen
				TestTimeAddController ttac = loader.getController();
				ttac.load(test);

				System.out.println("opening time change reqest");
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/gui/Stylesheet.css").toExternalForm());
				currentStage.setScene(scene);
				currentStage.setTitle("CEMS System - Lecturer - time extension request");
				currentStage.show();
			}
		});

		addtime.setId(btnName);
		addtime.setPrefWidth(100);
		addtime.setPrefHeight(20);
		return addtime;
	}

	private Button createUnlockTestButton(Test test, String btnName, Button UnlockBTN, Button addtimeBTN,
			Button lockBTN) {
		UnlockBTN.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientUI.chat.updateLockButton_DB(test, "FALSE");
				test.setLockBtnPressed(false);
				// Disable the button
				UnlockBTN.setDisable(true);
				// Change button color to red
				UnlockBTN.setStyle("-fx-background-color: red;");

				// disable and recolor the lock button
				lockBTN.setDisable(false);
				lockBTN.setStyle("-fx-background-color:;");

				// also set the other button to disable
				addtimeBTN.setDisable(false);
			}
		});

		UnlockBTN.setId(btnName);
		UnlockBTN.setPrefWidth(100);
		UnlockBTN.setPrefHeight(20);
		return UnlockBTN;
	}

	public Button createLockTestButton(Test test, String btnName, Button locked, Button addtimeBTN, Button UnlockBTN) {
		locked.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ClientUI.chat.updateLockButton_DB(test, "TRUE");

				test.setLockBtnPressed(true);
				// Disable the button and Change button color to red
				locked.setDisable(true);
				locked.setStyle("-fx-background-color: red;");

				UnlockBTN.setDisable(false);
				UnlockBTN.setStyle("-fx-background-color: ;");

				// also set the other button to disable
				addtimeBTN.setDisable(true);
			}
		});

		locked.setId(btnName);
		locked.setPrefWidth(100);
		locked.setPrefHeight(20);
		return locked;
	}

	@FXML
	void AddTimePressed(ActionEvent event) throws IOException {
		TestTimeAddController cqc = (TestTimeAddController) openScreen("/clientFXMLS/LecturerTimeExtensionRequest.fxml",
				"CEMS System - Lecturer - Test time Extension Request", event);
		// we load(test) inside the addtime button handle action
		//cqc.load();
	}

	public static ArrayList<String> getSubjectCourses() {
		return SubjectCourses;
	}

	public static void setSubjectCourses(ArrayList<String> subjectCourses) {
		SubjectCourses = subjectCourses;
	}
}
