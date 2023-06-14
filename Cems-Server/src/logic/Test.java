package logic;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class Test implements Serializable{
//    private String id, subject, author;
//    private String duration, TestComments, testCode;
//	Button LockTest;
//    private String time, dateString;
//	Button AddTime;
//    private DatePicker date;
//	private String TimeLeft;
    private String id, subject, author;
    private String duration, TestComments, testCode;
    transient Button LockTest;
    private String time, dateString;
    transient Button AddTime;
    transient DatePicker date;
    private String TimeLeft;

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


	public void setAddTime(Button AddTime) {
        this.AddTime = AddTime;
		
	}

	public void setLockTest(Button LockTest) {
        this.LockTest = LockTest;
		
	}

	public void setTimeLeft(String TimeLeft) {
		 this.TimeLeft = TimeLeft;
				
	}
}
