package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class Test implements Serializable{
    private ArrayList<QuestionModel> quesitonsInTest;
    private String id, Subject, author;
    private String duration, TestComments, Code;
    private String timeToAdd,ReasonForTimeExtension;
	private String time, dateString;
    private DatePicker date;
	private String TimeLeft;

    //ongoing_tests 
    private Button LockTest;
	private Button AddTime;
    private Button UnlockTest;
	private boolean LockBtnPressed = false;
	private boolean ongoingTest_approved=false;
	private boolean ongoingTest_denied=false;
	//HOD time extension
    private Button Approve;
	private Button Deny;

	



    public Test() {
        id = new String();
        Subject = new String();
        author = new String();
        duration = new String();
        TestComments = new String();
        Code = new String();
        time = new String();
        dateString = new String();
        quesitonsInTest = new ArrayList<QuestionModel>();
    }

    public Test(String id, String subject, String author, String duration, String testComments, String testCode,
            String dateString, String time) {
        this.id = id;
        this.Subject = subject;
        this.author = author;
        this.duration = duration;
        TestComments = testComments;
        this.Code = testCode;
        this.dateString = dateString;
        this.time = time;
        quesitonsInTest = new ArrayList<QuestionModel>();
    }

    public ArrayList<QuestionModel> getQuesitonsInTest() {
        return quesitonsInTest;
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
        return Subject;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
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

    public String getCode() {
        return Code;
    }

    public void setCode(String testCode) {
        this.Code = testCode;
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

    public void addToQuestions(ArrayList<QuestionModel> list) {
        for (QuestionModel q : list)
            if(!quesitonsInTest.contains(q))
                quesitonsInTest.add(q);
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


