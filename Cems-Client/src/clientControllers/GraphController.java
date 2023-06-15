package clientControllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class GraphController extends BasicController{

    @FXML
    private Button backButton;

    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private PieChart pieChart;

    @FXML
    void backtoStatistical(ActionEvent event) {
        LecturerStatisticalController lsc = (LecturerStatisticalController) openScreen("/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical Information", event);
		lsc.load();
    }

	public void initialize_PieChart() {// pass data as argument (?)
		pieChart = new PieChart();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Passed", 40));
        pieChartData.add(new PieChart.Data("Failed", 60));

        // this should add little arrows to the piechart:
        pieChartData.forEach(
                data -> data.nameProperty().bind(Bindings.concat(data.getName(), "amount",
                        data.pieValueProperty())));

        // assign the data to the pieChart
        pieChart.getData().addAll(pieChartData);
		
	}
    @SuppressWarnings({"unchecked", "rawtypes"})
    // TODO pass data as argument 
	public void initialize_BarChart(String testID) {

        XYChart.Series series1 = new XYChart.Series<>();

		series1.setName("Test ID - " + testID);
		series1.getData().add(new XYChart.Data("0-50", 46));
		series1.getData().add(new XYChart.Data("55-59", 17));
		series1.getData().add(new XYChart.Data("60-70", 46));
        series1.getData().add(new XYChart.Data("71-80", 38));
        series1.getData().add(new XYChart.Data("81-90", 38));
        series1.getData().add(new XYChart.Data("91-100", 139));
		

		barchart.getData().addAll(series1);

	}
}
