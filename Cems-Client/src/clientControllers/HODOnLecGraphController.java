package clientControllers;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import logic.Statistics;
import logic.Test;

/**
 * 
 * This controller handles the view of statistical information on a lecturer for
 * the Head of Department (HOD).
 * 
 * It extends from the BasicController class.
 */
public class HODOnLecGraphController extends BasicController {

    // FXML variables used to interface with the UI
    @FXML
    private Text Exam_NameID;

    @FXML
    private Text Lecturer_Name;

    // ... Other FXML variables ...
    @FXML
    private Text a_pass;

    @FXML
    private Text average_fill;

    @FXML
    private Text b_pass;

    @FXML
    private BarChart<String, Number> graph_barchart;

    @FXML
    private Text c_pass;

    @FXML
    private Text d_pass;

    @FXML
    private Text date_fill;

    @FXML
    private Text e_pass;

    @FXML
    private Text f_pass;

    @FXML
    private Text fail_precentage_fill;

    @FXML
    private Text fail_precentage;

    @FXML
    private Text g_pass;

    @FXML
    private Text h_pass;

    @FXML
    private Text high_fill;

    @FXML
    private Text low_fill;

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

    // Buttons in the UI
    @FXML
    private Button backButton;

    @FXML
    private Button exitbutton;

    @FXML
    private Button logo;

    @FXML
    private Label live_time;

    @FXML
    void initialize() {
        // Start the clock
        Timenow(live_time);
    }

    /**
     * 
     * This function handles the action of clicking the "Back" button.
     * It navigates the user back to the statistical information on lecturer screen.
     * 
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    void backtoStatistical(ActionEvent event) {
        HODStatisticOnLecturerController Hod = (HODStatisticOnLecturerController) openScreen(
                "/clientFXMLS/HodStatisticOnLecturer.fxml", "Statistical Informatrion On Lecturer", event);
        Hod.load();
    }

    /**
     * 
     * This function sets the data for the statistical information on a lecturer
     * view.
     * 
     * @param stats              The Statistics object containing the lecturer's
     *                           statistics.
     * @param completedTestsList The list of completed tests for the lecturer.
     */
    public void setData(Statistics stats, ArrayList<Test> completedTestsList) {
        // Arrays to store the number of students and their percentage in each grade
        // category

        int[] presentage_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] presentage_precentage = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        // Populating UI elements with stats data

        Exam_NameID.setText(stats.getCourse() + "(" + stats.getTestID() + ")");
        date_fill.setText(stats.getDate());
        average_fill.setText(Integer.toString(stats.getAverage()));
        median_fill.setText(stats.getMedian());
        high_fill.setText(Integer.toString(stats.getHighes()));
        low_fill.setText(Integer.toString(stats.getLowest()));
        // ... Other text elements ...
        for (Test test : completedTestsList) {
            if (test.getId().equals(stats.getTestID())) {
                Lecturer_Name.setText(test.getAuthor());
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
            presentage_precentage[i] = (int) ((double) presentage_num[i] / stats.getTotal_Number_of_Students() * 100);
        }

        // Populating UI elements with percentage data

        fail_precentage_fill.setText(Integer.toString(presentage_precentage[0]));
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
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            if (data.getXValue().equals("Fail")) {
                // Set the color of 'Fail' category bar to red
                node.setStyle("-fx-bar-fill: red;");
            } else if (data.getXValue().equals("55-64") || data.getXValue().equals("65-69")
                    || data.getXValue().equals("70-74") || data.getXValue().equals("75-79")) {
                // Set the color of other bars to yellow
                node.setStyle("-fx-bar-fill: yellow;");
            } else {
                // Set the color of other bars to green
                node.setStyle("-fx-bar-fill: green;");
            }
        }
    }

}
