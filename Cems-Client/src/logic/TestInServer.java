package logic;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Button;

public class TestInServer implements Serializable {

    private ArrayList<Question> quesitonsInTest;
    private String id, subject, author;
    private String duration, testComment, testCode;
    private String time, dateString;
    // private DatePicker date;
    private int totalPoints;
    private String course;
    private String TimeLeft;

    // ongoing_tests
    private String timeToAdd,ReasonForTimeExtension;
    private Button LockTest;
    private Button AddTime;
    private Button UnlockTest;
    private boolean LockBtnPressed = false;
    private boolean ongoingTest_approved = false;
    private boolean ongoingTest_denied = false;
    // HOD time extension
    private Button Approve;
    private Button Deny;

    public TestInServer() {
        id = new String();
        subject = new String();
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isOngoingTest_approved() {
        return ongoingTest_approved;
    }

    public void setOngoingTest_approved(boolean ongoingTest_approved) {
        this.ongoingTest_approved = ongoingTest_approved;
    }

    public boolean isOngoingTest_denied() {
        return ongoingTest_denied;
    }

    public void setOngoingTest_denied(boolean ongoingTest_denied) {
        this.ongoingTest_denied = ongoingTest_denied;
    }

    public Button getLockTest() {
        return LockTest;
    }

    public void setLockTest(Button lockTest) {
        this.LockTest = lockTest;
    }

    public Button getAddTime() {
        return AddTime;
    }

    public void setAddTime(Button addTime) {
        this.AddTime = addTime;
    }

    public String getTimeLeft() {
        return TimeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        TimeLeft = timeLeft;
    }

    public Button getApprove() {
        return Approve;
    }

    public void setApprove(Button approve) {
        Approve = approve;
    }

    public Button getDeny() {
        return Deny;
    }

    public void setDeny(Button deny) {
        Deny = deny;
    }

    public String getTimeToAdd() {
        return timeToAdd;
    }

    public void setTimeToAdd(String timeToAdd) {
        this.timeToAdd = timeToAdd;
    }

    public String getReasonForTimeExtension() {
        return ReasonForTimeExtension;
    }

    public void setReasonForTimeExtension(String reasonForTimeExtension) {
        ReasonForTimeExtension = reasonForTimeExtension;
    }

    public boolean isLockBtnPressed() {
        return LockBtnPressed;
    }

    public void setLockBtnPressed(boolean lockBtnPressed) {
        LockBtnPressed = lockBtnPressed;
    }

    public Button getUnlockTest() {
        return UnlockTest;
    }

    public void setUnlockTest(Button unlockTest) {
        UnlockTest = unlockTest;
    }
}
