package clientControllers;

import java.util.Observable;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LecturerStatisticalController extends BasicController {

	@FXML
	private TableView<?> table;
	@FXML
	private PieChart pieChart;
	@FXML
	private BarChart<?, ?> barchart;
	@FXML
	private Button backToLecturer;
	@FXML
	private Button backButton;
	@FXML
	private Button viewGraph;

	// TODO need to find a way to populate the pie chart and get rid on the new
	// "PieChart.Data(Testing, 50)"
	// * need to add a Sleep option

	public void initialize_PieChart() {// pass data as argument (?)

		AnchorPane root = null;
		Stage currentStage = new Stage();

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		pieChartData.add(new PieChart.Data("Passed", 40));
		pieChartData.add(new PieChart.Data("Failed", 60));

		// this should add little arrows to the piechart:
		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), "amount",
						data.pieValueProperty())));

		// assign the data to the pieChart
		pieChart.getData().addAll(pieChartData);

		Group r = new Group(pieChart);
		Scene s = new Scene(r, 600, 400);

		currentStage.setTitle("PieChart!!!!!!!!!!!!!!!!!!!!");
		currentStage.setScene(s);
		currentStage.show();

		System.out.println(pieChart.getData());
	}

	// public void initialize_BarChart() {// pass data as argument (?)

	// XYChart.Series series1 = new XYChart.Series<>();

	// series1.setName("2023");
	// series1.getData().add(new XYChart.Data("tinkywinky", 23532));
	// series1.getData().add(new XYChart.Data("lala", 666));
	// series1.getData().add(new XYChart.Data("po", 11111));

	// XYChart.Series series2 = new XYChart.Series<>();

	// series2.setName("2022");
	// series2.getData().add(new XYChart.Data("abc", 123));
	// series2.getData().add(new XYChart.Data("def", 456));
	// series2.getData().add(new XYChart.Data("ghi", 678));

	// barchart.getData().addAll(series1, series2);

	// }

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
		LecturerStatisticalController lsc = (LecturerStatisticalController) openScreen(
				"/clientFXMLS/LecturerStatistical_GraphView.fxml", "CEMS System - Viewing Graph", event);
		lsc.initialize_PieChart();
	}

	@FXML
	void backtoStatistical(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Visualization - Statistical Information",
				event);
		// FIXME fix this load table thingy
		// lecturerStatisticalController.loadTable();

	}

}
