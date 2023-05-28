package clientControllers;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private TableView<Statistics> table;

	@FXML
    private TableColumn<Statistics, Button> viewGraph;

    @FXML
    private Button viewGraphButton;

	@FXML
	private PieChart pieChart;

	@FXML
	private BarChart<String, Number>  barchart;

	@FXML
	private Button backToLecturer;

	@FXML
	private Button backButton;


	// TODO need to find a way to populate the pie chart and get rid on the new
	// "PieChart.Data(Testing, 50)"
	// * need to add a Sleep option


	public void load() {
		Average.setCellValueFactory(new PropertyValueFactory<>("Average"));
        Course.setCellValueFactory(new PropertyValueFactory<>("Course"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Median.setCellValueFactory(new PropertyValueFactory<>("Median"));
        TestID.setCellValueFactory(new PropertyValueFactory<>("TestID"));
		viewGraph.setCellValueFactory(new PropertyValueFactory<>("viewGraph"));

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
		LecturerOptionsController lecturerOptionsController = (LecturerOptionsController) openScreen(
				"/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
		lecturerOptionsController.openScreen(null, null, event);
	}

	@FXML
	void viewGraph_ButtonPressed(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		GraphController lsc = (GraphController) openScreen("/clientFXMLS/LecturerStatistical_GraphView.fxml", "CEMS System - Viewing Graph", event);
		lsc.initialize_PieChart();
		lsc.initialize_BarChart();
	}

	@FXML
	void backtoStatistical(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Visualization - Statistical Information",event);
		// FIXME fix this load table thingy
		// lecturerStatisticalController.loadTable();

	}

}
