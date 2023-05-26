package logic;

import java.util.ArrayList;

public class Test {
    private ArrayList<QuestionModel> quesitonsInTest;
    private String id, subject, author;
    private String duration, testComments, testCode;
    private String date, time;

    public Test(String id, String subject, String author, String duration, String testComments, String testCode,
            String date, String time) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.duration = duration;
        this.testComments = testComments;
        this.testCode = testCode;
        this.date = date;
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
        return testComments;
    }

    public void setTestComments(String testComments) {
        testComments = testComments;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
