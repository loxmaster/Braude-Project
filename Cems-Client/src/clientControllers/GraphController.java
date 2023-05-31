package clientControllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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

	public void initialize_BarChart() {// pass data as argument (?)

		/*CategoryAxis xAxis = new CategoryAxis();
        
        // Create a NumberAxis for the y-axis
        NumberAxis yAxis = new NumberAxis();
        
        // Create a BarChart with String as the x-axis type and Number as the y-axis type
        barchart = new BarChart<>(xAxis, yAxis);
		barchart.setTitle("Example Bar Chart");
		
		ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList(
                new XYChart.Data<>("Entry 1", 10),
                new XYChart.Data<>("Entry 2", 20),
                new XYChart.Data<>("Entry 3", 15),
                new XYChart.Data<>("Entry 4", 5),
                new XYChart.Data<>("Entry 5", 30),
                new XYChart.Data<>("Entry 6", 25),
                new XYChart.Data<>("Entry 7", 12),
                new XYChart.Data<>("Entry 8", 18),
                new XYChart.Data<>("Entry 9", 22),
                new XYChart.Data<>("Entry 10", 8)
        );

		XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setData(data);
        
        // Add the series to the bar chart
        barchart.getData().add(series);

        
		System.out.println(1);

		*/
        XYChart.Series series1 = new XYChart.Series<>();

		series1.setName("Test ID - 020302");
		series1.getData().add(new XYChart.Data("0-50", 46));
		series1.getData().add(new XYChart.Data("55-59", 17));
		series1.getData().add(new XYChart.Data("60-70", 46));
        series1.getData().add(new XYChart.Data("71-80", 38));
        series1.getData().add(new XYChart.Data("81-90", 38));
        series1.getData().add(new XYChart.Data("91-100", 139));
		

		barchart.getData().addAll(series1);

	}
}
