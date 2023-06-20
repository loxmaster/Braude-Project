package clientControllers;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import logic.Statistics;
import logic.Test;

/**
 * 
 * This controller handles the view of statistical information on courses for
 * the Head of Department (HOD).
 * 
 * It extends from the BasicController class.
 */
public class HODOnCourseGraphController extends BasicController {
    // Define UI elements
    @FXML
    private Text Course_NameID;

    @FXML
    private Text a_pass;

    @FXML
    private Text average_fill;

    @FXML
    private Text b_pass;

    @FXML
    private Button backButton;

    @FXML
    private Text c_pass;

    @FXML
    private Text d_pass;

    @FXML
    private Text e_pass;

    @FXML
    private Button exitbutton;

    @FXML
    private Text f_pass;

    @FXML
    private Text fail_precentage;

    @FXML
    private Text g_pass;

    @FXML
    private BarChart<String, Number> graph_barchart;

    @FXML
    private Text h_pass;

    @FXML
    private Button logo;

    @FXML
    private Text median_fill;

    @FXML
    private Text num_a;

    @FXML
    private Text num_b;

    @FXML
    private Text num_c;

    @FXML
    private Text num_d;

    @FXML
    private Text num_e;

    @FXML
    private Text num_f;

    @FXML
    private Text num_fail;

    @FXML
    private Text num_g;

    @FXML
    private Text num_h;

    @FXML
    private PieChart piechart;

    @FXML
    private Text total_students;

    @FXML
    private Text total_above;

    @FXML
    private Text total_below;

    @FXML
    private Label live_time;

    /**
     * This function initializes the controller.
     * It starts the clock on the UI.
     */
    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

    /**
     * 
     * This function handles the action of clicking the "Back to Lecturer" button.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void backToLecturer(ActionEvent event) {
        // Handle back to lecturer action
    }

    /**
     * 
     * This function handles the action of clicking the "Back to Statistical"
     * button.
     * It navigates the user back to the statistical information on courses page.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void backtoStatistical(ActionEvent event) {
        HODStatisticOnCourseController hod = (HODStatisticOnCourseController) openScreen(
                "/clientFXMLS/HodStatisticOnCourses.fxml", "Statistical Information On Courses", event);
        hod.load();
    }

    /**
     * 
     * This function handles the action of clicking the "Exit" button.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void exitPressed(ActionEvent event) {
        // Handle exit action
    }

    /**
     * 
     * This function populates the UI with course statistics.
     * 
     * @param data                  The Statistics object containing the course
     *                              statistics.
     * @param AllcompletedTestsList The list of all completed tests for the course.
     */
    public void CoursesdInfo(Statistics data, ArrayList<Test> AllcompletedTestsList) {

        // Create pie chart data
        PieChart.Data passData = new PieChart.Data("Pass Rate", data.getPass_Rate());
        PieChart.Data failData = new PieChart.Data("Fail Rate", data.getFail_Rate());
        // Add data to the pie chart
        piechart.getData().addAll(passData, failData);

        // Populating UI elements with stats data
        total_students.setText(Integer.toString(data.getTotal_Number_of_Students()));
        total_above.setText(Integer.toString(data.getNumber_of_Students_Above_Average()));
        total_below.setText(Integer.toString(data.getNumber_of_Students_Below_Average()));
        average_fill.setText(Integer.toString(data.getAverage()));
        median_fill.setText(data.getMedian());
        Course_NameID.setText(data.getName_of_Courses() + "(" + data.getCourse() + ")");

        int[] presentage_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] presentage_precentage = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        // ... Other text elements ...
        for (Test test : AllcompletedTestsList) {
            if (test.getId().substring(0, 4).equals(data.getCourse())) {
                if (Integer.parseInt(test.getGrade()) <= 54) {
                    presentage_num[0]++;
                } else if (Integer.parseInt(test.getGrade()) <= 64 && Integer.parseInt(test.getGrade()) >= 55) {
                    presentage_num[1]++;
                } else if (Integer.parseInt(test.getGrade()) <= 69 && Integer.parseInt(test.getGrade()) >= 65) {
                    presentage_num[2]++;
                } else if (Integer.parseInt(test.getGrade()) <= 74 && Integer.parseInt(test.getGrade()) >= 70) {
                    presentage_num[3]++;
                } else if (Integer.parseInt(test.getGrade()) <= 79 && Integer.parseInt(test.getGrade()) >= 75) {
                    presentage_num[4]++;
                } else if (Integer.parseInt(test.getGrade()) <= 84 && Integer.parseInt(test.getGrade()) >= 80) {
                    presentage_num[5]++;
                } else if (Integer.parseInt(test.getGrade()) <= 89 && Integer.parseInt(test.getGrade()) >= 85) {
                    presentage_num[6]++;
                } else if (Integer.parseInt(test.getGrade()) <= 94 && Integer.parseInt(test.getGrade()) >= 90) {
                    presentage_num[7]++;
                } else if (Integer.parseInt(test.getGrade()) <= 100 && Integer.parseInt(test.getGrade()) >= 95) {
                    presentage_num[8]++;
                }
            }
        }
        // Calculating the percentage of students in each category

        for (int i = 0; i < presentage_num.length; i++) {
            presentage_precentage[i] = (int) ((double) presentage_num[i] / data.getTotal_Number_of_Students() * 100);
        }

        // Populating UI elements with percentage data
        fail_precentage.setText(Integer.toString(presentage_precentage[0]));
        a_pass.setText(Integer.toString(presentage_precentage[1]));
        b_pass.setText(Integer.toString(presentage_precentage[2]));
        c_pass.setText(Integer.toString(presentage_precentage[3]));
        d_pass.setText(Integer.toString(presentage_precentage[4]));
        e_pass.setText(Integer.toString(presentage_precentage[5]));
        f_pass.setText(Integer.toString(presentage_precentage[6]));
        g_pass.setText(Integer.toString(presentage_precentage[7]));
        h_pass.setText(Integer.toString(presentage_precentage[8]));

        num_fail.setText(Integer.toString(presentage_num[0]));
        num_a.setText(Integer.toString(presentage_num[1]));
        num_b.setText(Integer.toString(presentage_num[2]));
        num_c.setText(Integer.toString(presentage_num[3]));
        num_d.setText(Integer.toString(presentage_num[4]));
        num_e.setText(Integer.toString(presentage_num[5]));
        num_f.setText(Integer.toString(presentage_num[6]));
        num_g.setText(Integer.toString(presentage_num[7]));
        num_h.setText(Integer.toString(presentage_num[8]));

        // Creating a bar series for the bar chart

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Define categories
        String[] categories = { "Fail", "55-64", "65-69", "70-74", "75-79", "80-84", "85-89", "90-94", "95-100" };

        // Add data to the series
        for (int i = 0; i < presentage_num.length; i++) {
            series.getData().add(new XYChart.Data<>(categories[i], presentage_num[i]));
        }

        // Add the series to the bar chart
        graph_barchart.getData().add(series);

        // After adding the series, change the color of bars
        for (XYChart.Data<String, Number> DataXY : series.getData()) {
            Node node = DataXY.getNode();
            if (DataXY.getXValue().equals("Fail")) {
                // Set the color of 'Fail' category bar to red
                node.setStyle("-fx-bar-fill: red;");
            } else if (DataXY.getXValue().equals("55-64") || DataXY.getXValue().equals("65-69")
                    || DataXY.getXValue().equals("70-74") || DataXY.getXValue().equals("75-79")) {
                // Set the color of other bars to yellow
                node.setStyle("-fx-bar-fill: yellow;");
            } else {
                // Set the color of other bars to green
                node.setStyle("-fx-bar-fill: green;");
            }
        }

    }

}
