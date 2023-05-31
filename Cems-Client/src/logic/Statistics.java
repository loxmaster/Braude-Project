package logic;

import clientControllers.BasicController;
import clientControllers.GraphController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Statistics extends BasicController {
    private String Average, Course, Date, Median, TestID;
    private Button distribution;

    public Statistics(String average, String course, String date, String median, String testID) {
        Average = average;
        Course = course;
        Date = date;
        Median = median;
        TestID = testID;
    }

    public String getAverage() {
        return Average;
    }

    public void setAverage(String avarage) {
        Average = avarage;
    }

    public String getCourse() {
        return Course;
    }

    public Button getDistribution() {
        distribution = new Button("Distribution");

		distribution.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
                GraphController lsc = (GraphController) openScreen("/clientFXMLS/LecturerStatistical_GraphView.fxml", "CEMS System - Viewing Graph", event);
		        lsc.initialize_BarChart(TestID);
			}
		});

		// questionInTestButton.setId("questionbutton");
		distribution.setPrefWidth(120);
		distribution.setPrefHeight(10);

		return distribution;
    }

    public void setDistribution(Button distribution) {
        this.distribution = distribution;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMedian() {
        return Median;
    }

    public void setMedian(String median) {
        Median = median;
    }

    public String getTestID() {
        return TestID;
    }

    public void setTestID(String testID) {
        TestID = testID;
    }

}
