package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import clientHandlers.ClientController;
import clientHandlers.ClientHandler;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Statistics;
import logic.Test;

import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.QuestionModel;
public class OngoingTestController extends BasicController {
private static ArrayList<Test> ongoingTests;
//    private static OngoingTestController instance;  // singleton instance
//
//    public OngoingTestController() {
//        instance = this;  // set the instance in constructor
//    }
//
//    public static OngoingTestController getInstance() {
//        return instance;  // getter for instance
//    }
	
	
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
	private Button exitbutton;
    
	@FXML
	private Button logo;
	
    @FXML
    private Button backBTN;


	
	public void load() {
	    Code.setCellValueFactory(new PropertyValueFactory<>("Code"));
	    Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
	    TimeLeft.setCellValueFactory(new PropertyValueFactory<>("TimeLeft"));
	    AddTime.setCellValueFactory(new PropertyValueFactory<>("AddTime"));
	    LockTest.setCellValueFactory(new PropertyValueFactory<>("LockTest"));

	    // Assume ClientHandler.fetchOngoingTests() is a synchronous call that returns a list of tests
	    //ClientUI.chat.GetOngoingTests();
//	    ArrayList<Test> tests = (ArrayList<Test>) ClientHandler.getOngoingTests();
	    ArrayList<Test> tests = (ArrayList<Test>)ongoingTests;
	    

	    // Waits 5 seconds for data to be found
//int cap = 20;
//	    while (tests.isEmpty() && (cap > 0)) {
//	        try {
//	            Thread.sleep(250);
//	            cap--;
//	        } catch (InterruptedException e) { }
//	    }
	    if (tests.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Error getting the ongoing tests!", "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
	        ObservableList<Test> testList = FXCollections.observableArrayList(tests);
	        for (Test test : testList) {
	            test.setAddTime(createActionButton(test, "AddTime"));
	            test.setLockTest(createActionButton(test, "LockTest"));
	        }
	        table.setItems(testList);
	    }
	}
//    public static  void newTestDataAvailable() {
//        // Call the load method again when new data arrives
//        getInstance().load();
//    }
    
	private Button createActionButton(Test test, String actionType) {
	    Button actionBtn = new Button(actionType);
	    actionBtn.setOnAction(event -> {
	        // Handle the action here
	        // Use 'test' object for the current row's data
	    });
	    return actionBtn;
	}

	public static void setOngoingTests(ArrayList<Test> tests) {
		OngoingTestController.ongoingTests = tests;
	}

}
