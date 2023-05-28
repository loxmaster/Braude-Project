package clientControllers;

import java.util.Observable;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class LecturerStatisticalController extends BasicController {

	@FXML
	private TableView<?> table;
	@FXML
	private PieChart pieChart;
	@FXML
	private Button backToLecturer;
	@FXML
	private Button backButton;
	@FXML
	private Button viewGraph;

	// FIXME
	// TODO need to find a way to populate the pie chart and get rid on the new
	// "PieChart.Data(Testing, 50)"
	public void initialize() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("A", 50),
				new PieChart.Data("B", 30), new PieChart.Data("C", 50));
		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), "amount", data.pieValueProperty())));
		pieChart.getData().addAll(pieChartData);
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
		openScreen("/clientFXMLS/LecturerStatistical_GraphView.fxml", "CEMS System - Viewing Graph", event);
	}
	
	@FXML
	void backtoStatistical_Main(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Visualization - Statistical Information", event);
		
		// FIXME fix this load table thingy
		// lecturerStatisticalController.loadTable();

	}

}
