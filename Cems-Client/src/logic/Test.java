package logic;

import java.util.ArrayList;

import javafx.scene.control.DatePicker;

public class Test {

    private ArrayList<QuestionModel> quesitonsInTest;
    private String id, author, subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String duration, testComment, testCode;
    private String time, dateString;
    private DatePicker date;
    private int totalPoints;
    private String course;
    private String StudentsName, StudentID, Grade;
    private String status, questionsString, type, tested;

    public Test() {
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
        quesitonsInTest = new ArrayList<QuestionModel>();
    }

    // noah - added getters and setters for course - not yet updated in the sql -
    // talk to me
    public void setCourse(String course) {
        this.course = course;
    }

    public Test(String id, String subject, String StudentsName, String StudentID, String Grade) {
        this.id = id;
        this.course = subject;
        this.StudentsName = StudentsName;
        this.StudentID = StudentID;
        this.Grade = Grade;
    }

    public Test(String id, String subject, String author, String duration, String testComments, String testCode,
            String dateString, String time, ArrayList<QuestionModel> quesitonsInTest) {
        this.id = id;
        this.course = subject;
        this.author = author;
        this.duration = duration;
        this.testComment = testComment;
        this.testCode = testCode;
        this.dateString = dateString;
        this.time = time;
        totalPoints = 0;
        
        setQuesitonsInTest(quesitonsInTest);
    }

    ////// is this the one u looking for???
    public Test(String id, String StudentID, String Grade, String author, String testCode, String dateString,
            String time, String duration, String questionsString, String type, String status, String tested) {
        this.id = id;
        this.StudentID = StudentID;
        this.Grade = Grade;
        this.author = author;
        this.testCode = testCode;
        this.dateString = dateString;
        this.time = time;
        this.duration = duration;
        this.questionsString = questionsString;
        this.type = type;
        this.status = status;
        this.tested = tested;
    }

    public String getStatus() {
        return status;
    }

    public String getQuestionsString() {
        return questionsString;
    }

    public String getType() {
        return type;
    }

    public String getGrade() {
        return Grade;
    }

    
    
    
    public String getTested() {
        return tested;
    }

    public String getId() {
        return id;
    }

    public String getStudentID() {
        return StudentID;
    }
    

    public String getStudentsName() {
        return StudentsName;
    }

    public String getDateString() {
        return dateString;
    }
    public String getCourse() {
        return course;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public DatePicker getDate() {
        return date;
    }

    public String getTestCode() {
        return testCode;
    }

    public String getDuration() {
        return duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuestionsString(String questionsString) {
        this.questionsString = questionsString;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public void setStudentsName(String studentsName) {
        StudentsName = studentsName;
    }

    public void setQuesitonsInTest(ArrayList<QuestionModel> quesitonsInTest) {
        this.quesitonsInTest = quesitonsInTest;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public void setDate(DatePicker date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void addToQuestions(ArrayList<QuestionModel> list) {
        for (QuestionModel q : list)
            if (!quesitonsInTest.contains(q))
                quesitonsInTest.add(q);
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public ArrayList<QuestionModel> getQuesitonsInTest() {
        return quesitonsInTest;
    }
}
