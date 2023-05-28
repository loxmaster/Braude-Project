package logic;

import javafx.scene.control.Button;

public class Statistics {
    private String Average, Course, Date, Median, TestID;
    private Button viewGraph;

    public Statistics(String average, String course, String date, String median, String testID) {
        Average = average;
        Course = course;
        Date = date;
        Median = median;
        TestID = testID;
        viewGraph = new Button("Distribution");
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

    public Button getViewGraph() {
        return viewGraph;
    }

    public void setViewGraph(Button viewGraph) {
        this.viewGraph = viewGraph;
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
