package logic;

import clientControllers.BasicController;
import clientControllers.GraphController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Statistics extends BasicController {
    private String Course, Date, Median, TestID, Grade;
    private int Average,Total_Number_of_Students, Highes, Lowest, Number_of_Students_Above_Average;
    private int Number_of_Students_Below_Average, Pass_Rate, Fail_Rate, Grade_Distribution;
    private Button distribution;

    public Statistics(String TestID) {
        this.TestID = TestID;
    }

    public Statistics(String TestID, String course, String Date, int Total_Number_of_Students, int Average) {
        this.TestID = TestID;
        this.Course = course;
        this.Date = Date;
        this.Total_Number_of_Students = Total_Number_of_Students;
        this.Average = Average;
    }

    public Statistics(int average, String course, String date, String median, String testID, String Grade) {
        this.Average = average;
        this.Course = course;
        this.Date = date;
        this.Median = median;
        this.TestID = testID;
        this.Grade = Grade;
    }

    public int getTotal_Number_of_Students() {
        return Total_Number_of_Students;
    }

    public void setTotal_Number_of_Students(int total_Number_of_Students) {
        Total_Number_of_Students = total_Number_of_Students;
    }

    public int getHighes() {
        return Highes;
    }

    public void setHighes(int highes) {
        Highes = highes;
    }

    public int getLowest() {
        return Lowest;
    }

    public void setLowest(int lowest) {
        Lowest = lowest;
    }

    public int getNumber_of_Students_Above_Average() {
        return Number_of_Students_Above_Average;
    }

    public void setNumber_of_Students_Above_Average(int number_of_Students_Above_Average) {
        Number_of_Students_Above_Average = number_of_Students_Above_Average;
    }

    public int getNumber_of_Students_Below_Average() {
        return Number_of_Students_Below_Average;
    }

    public void setNumber_of_Students_Below_Average(int number_of_Students_Below_Average) {
        Number_of_Students_Below_Average = number_of_Students_Below_Average;
    }

    public int getPass_Rate() {
        return Pass_Rate;
    }

    public void setPass_Rate(int pass_Rate) {
        Pass_Rate = pass_Rate;
    }

    public int getFail_Rate() {
        return Fail_Rate;
    }

    public void setFail_Rate(int fail_Rate) {
        Fail_Rate = fail_Rate;
    }

    public int getGrade_Distribution() {
        return Grade_Distribution;
    }

    public void setGrade_Distribution(int grade_Distribution) {
        Grade_Distribution = grade_Distribution;
    }

    public int getAverage() {
        return Average;
    }

    public void setAverage(int avarage) {
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
                GraphController lsc = (GraphController) openScreen("/clientFXMLS/LecturerStatistical_GraphView.fxml",
                        "CEMS System - Viewing Graph", event);
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
        this.Course = course;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getMedian() {
        return Median;
    }

    public void setMedian(String median) {
        this.Median = median;
    }

    public String getTestID() {
        return TestID;
    }

    public void setTestID(String testID) {
        this.TestID = testID;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

}
