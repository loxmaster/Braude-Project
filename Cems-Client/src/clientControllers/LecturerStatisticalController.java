package clientControllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Statistics;

public class LecturerStatisticalController extends BasicController {

	@FXML
    private TableColumn<Statistics, String> Average;

    @FXML
    private TableColumn<Statistics, String> Course;

    @FXML
    private TableColumn<Statistics, String> Date;

    @FXML
    private TableColumn<Statistics, String> Median;

    @FXML
    private TableColumn<Statistics, String> TestID;

	@FXML
    private TableColumn<Statistics, Button> Distribution;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private TableView<Statistics> table;


	// TODO need to find a way to populate the pie chart and get rid on the new
	// "PieChart.Data(Testing, 50)"
	// * need to add a Sleep option


	public void load() {

		Average.setCellValueFactory(new PropertyValueFactory<>("Average"));
        Course.setCellValueFactory(new PropertyValueFactory<>("Course"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Median.setCellValueFactory(new PropertyValueFactory<>("Median"));
        TestID.setCellValueFactory(new PropertyValueFactory<>("TestID"));
		Distribution.setCellValueFactory(new PropertyValueFactory<>("Distribution"));

		Statistics stat1 = new Statistics("56.78", "malam", "2023-05-29", "79", "020301");
		Statistics stat2 = new Statistics("74", "malam", "2023-04-30", "70", "020302");
		Statistics stat3 = new Statistics("94", "algebra1", "2022-01-25", "91", "010201");

		ArrayList<Statistics> tempArray = new ArrayList<>();
		tempArray.add(stat1);
		tempArray.add(stat2);
		tempArray.add(stat3);

		ObservableList<Statistics> listToAdd = FXCollections.observableArrayList(tempArray);
		table.setItems(listToAdd);

	 }

	@FXML
	void backtoLecturerMain(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		backToLecturer(event);
	}
}
