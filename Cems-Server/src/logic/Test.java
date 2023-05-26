package logic;

import java.util.Date;

public class Test {
    private Question[] questions;
    

    private Lecturer auther,managingLecturer;
    private int id,duration;
    private String comment,code;
    private Date date;
    private Boolean isComputerized;

    public Test(Question[] question, int duration,int id, String comment, Lecturer auther, Lecturer managingLecturer, String code, String manaString, Boolean isComputerized,Date date){
        this.questions = question;
        this.duration = duration;
        this.comment = comment;
        this.auther = auther;
        this.managingLecturer = managingLecturer;
        //check if id/code needs to be calculated here
        this.code =code;
        this.id = id;
        //* *//
        this.date = date;
        this.isComputerized = isComputerized;
    }

    public Question[] getQuestions() {
        return this.questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Lecturer getAuther() {
        return this.auther;
    }

    public Lecturer getManagingLecturer() {
        return this.managingLecturer;
    }

    public int getId() {
        return this.id;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getComment() {
        return this.comment;
    }

    public String getCode() {
        return this.code;
    }

    public Date getDate() {
        return this.date;
    }

    public Boolean getIsComputerized() {
        return this.isComputerized;
    }

    public void setAuther(Lecturer auther) {
        this.auther = auther;
    }

    public void setManagingLecturer(Lecturer managingLecturer) {
        this.managingLecturer = managingLecturer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIsComputerized(Boolean isComputerized) {
        this.isComputerized = isComputerized;
    }
    
}

