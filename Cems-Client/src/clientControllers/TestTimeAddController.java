package clientControllers;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import clientHandlers.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.QuestionModel;
import logic.Test;

public class TestTimeAddController extends BasicController {

	
	@FXML
	private Button exitbutton;
    
	@FXML
	private Button logo;
	
    @FXML
    private Button backBTN;
    @FXML
    private Button submit;
    
    @FXML
    private TextField codeField;
    @FXML
    private TextField timeLeftField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField reasonForExtensionField;
    @FXML
    private TextField TimeToAddField;
    
    private Test test;
    
    
	public void load(Test test) {
		
		codeField.setText(test.getCode());
		timeLeftField.setText(test.getTimeLeft());
		subjectField.setText(test.getSubject());

		codeField.setDisable(true);
		timeLeftField.setDisable(true);
		subjectField.setDisable(true);

		this.test=test;
		
		
	}
	
	
	
	@FXML
	void submitPressed(ActionEvent event) {
	    String comments = reasonForExtensionField.getText();
	    String TimeToAdd = TimeToAddField.getText();

	    if (comments.isEmpty() && !TimeToAdd.matches("\\d{2}:\\d{2}")) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("no input");
	        alert.setContentText("Please enter the comments and time to add before sumbit.");
	        alert.showAndWait();
	        return;
	    }
	    	
	    	
	    if (comments.isEmpty()) {
	        // TimeToAdd format is not HH:MM
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Invalid comments error");
	        alert.setContentText("Please enter the comments before sumbit.");
	        alert.showAndWait();
	        return;
	    }

	    try {
	        LocalTime time = LocalTime.parse(TimeToAdd);

	        // Additional validation for the time range (00:00 - 23:59)
	        if (time.isBefore(LocalTime.MIN) || time.isAfter(LocalTime.MAX)) {
	            throw new DateTimeParseException("Invalid time range", TimeToAdd, 0);
	        }
	    } catch (DateTimeParseException e) {
	        // Invalid time format or range
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Invalid Time Format");
	        alert.setContentText("Please enter a valid time in HH:MM format (00:00 - 23:59).");
	        alert.showAndWait();
	        return;
	    }

	    // send the request for the extension adding to the relevant table in the database
	    ClientUI.chat.SendRequest_TimeExtention_toHOD(test.getId(), comments, TimeToAdd, test.getSubject());

	    // update the ongoingTests_permissions in HODController:
	    HODController.addTestTo_ongoingTests_permissions(test);
	    
	    //LecturerController controller = new LecturerController();

	    LecturerController lsc = (LecturerController) openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - HOD - permissions", event);
	
	    //openScreen("/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
	}
	

    @FXML
    void backPressed(ActionEvent event) {
	    //ClientUI.chat.GetOngoingTests();
    	OngoingTestController controller = new OngoingTestController();

		OngoingTestController lsc = (OngoingTestController) openScreen2("/clientFXMLS/LecturerOngoingTest.fxml", "CEMS System - HOD - permissions", event,controller);
		
//		OngoingTestController otc =(OngoingTestController) openScreen("/clientFXMLS/LecturerOngoingTest.fxml", "CEMS System - Lecturer - Menage Tests", event);
		lsc.load();    
		}
	
}
