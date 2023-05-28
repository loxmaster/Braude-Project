package logic;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.DatePicker;

public class Test {
    private ArrayList<QuestionModel> quesitonsInTest;
    private String id, subject, author;
    private String duration, TestComments, testCode;
    private String time, dateString;
    private DatePicker date;

    public Test() {
        id = new String();
        subject = new String();
        author = new String();
        duration = new String();
        TestComments = new String();
        testCode = new String();
        time = new String();
        dateString = new String();
     }

    public Test(String id, String subject, String author, String duration, String testComments, String testCode,
            String dateString, String time) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.duration = duration;
        TestComments = testComments;
        this.testCode = testCode;
        this.dateString = dateString;
        this.time = time;
    }

    public ArrayList<QuestionModel> getQuesitonsInTest() {
        return quesitonsInTest;
    }

    public void setQuesitonsInTest(ArrayList<QuestionModel> quesitonsInTest) {
        this.quesitonsInTest = quesitonsInTest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public void setDate(DatePicker date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTestComments() {
        return TestComments;
    }

    public void setTestComments(String testComments) {
        TestComments = testComments;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DatePicker getDate() {
        return date;
    }
}
