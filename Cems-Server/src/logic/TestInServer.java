package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class TestInServer implements Serializable {

    private ArrayList<Question> quesitonsInTest;
    private String id, subject, author;
    private String duration, testComment, testCode;
    private String time, dateString;
    //private DatePicker date;
    private int totalPoints;

    // noah - added getters and setters for course - not yet updated in the sql -
    // talk to me
    private String course;

    public TestInServer() {
        id = new String();
        subject = new String();
        
        // noah - added getters and setters for course - not yet updated in the sql -
        // talk to me
        course = new String();

        author = new String();
        duration = new String();
        testComment = new String();
        testCode = new String();
        time = new String();
        dateString = new String();
        totalPoints = 0;
        quesitonsInTest = new ArrayList<Question>();
    }

    // noah - added getters and setters for course - not yet updated in the sql -
    // talk to me
    public String getCourse() {
        return course;
    }

    // noah - added getters and setters for course - not yet updated in the sql -
    // talk to me
    public void setCourse(String course) {
        this.course = course;
    }

    public TestInServer(String id, String subject, String author, String duration, String testComment, String testCode,
            String dateString, String time, ArrayList<Question> quesitonsInTest) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.duration = duration;
        this.testComment = testComment;
        this.testCode = testCode;
        this.dateString = dateString;
        this.time = time;
        totalPoints = 0;
        setQuesitonsInTest(quesitonsInTest);
    }

    public ArrayList<Question> getQuesitonsInTest() {
        return quesitonsInTest;
    }

    public void setQuesitonsInTest(ArrayList<Question> quesitonsInTest) {
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
        return testComment;
    }

    public void setTestComments(String testComment) {
        this.testComment = testComment;
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

    public void addToQuestions(ArrayList<Question> list) {
        for (Question q : list)
            if (!quesitonsInTest.contains(q))
                quesitonsInTest.add(q);
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
