package logic;

import clientControllers.BasicController;

public class Statistics extends BasicController {
    private String Course, Date, Median, TestID, Grade, LecturerName, Number_of_Exams;
    private int Average, Total_Number_of_Students, Highes, Lowest, Number_of_Students_Above_Average;
    private int Number_of_Students_Below_Average, Pass_Rate, Fail_Rate, Grade_Distribution;
    private String StudentName, StudentID, Email, Number_Of_Courses,Name_of_Courses;

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

    public Statistics(String LecturerName, String TestID, String course, String Date, int Total_Number_of_Students,
            int Average) {
        this.LecturerName = LecturerName;
        this.TestID = TestID;
        this.Course = course;
        this.Date = Date;
        this.Total_Number_of_Students = Total_Number_of_Students;
        this.Average = Average;
    }

    public Statistics(int Average, String course, String date, String median, String testID, String Grade) {
        this.Average = Average;
        this.Course = course;
        this.Date = date;
        this.Median = median;
        this.TestID = testID;
        this.Grade = Grade;
    }

    public Statistics(String StudentName, String StudentID, String Email, int Average, String Number_Of_Courses) {
        this.StudentName = StudentName;
        this.StudentID = StudentID;
        this.Email = Email;
        this.Average = Average;
        this.Number_Of_Courses = Number_Of_Courses;

    }

    public Statistics(String Name_of_Courses, String CourseID, int Average, String Number_of_Exams,
            int Total_Number_of_Students, String Median, int Pass_Rate, int Fail_Rate,
            int Number_of_Students_Above_Average, int Number_of_Students_Below_Average) {
                this.Name_of_Courses = Name_of_Courses;
                this.Course = CourseID;
                this.Average = Average;
                this.Number_of_Exams = Number_of_Exams;
                this.Total_Number_of_Students = Total_Number_of_Students;
                this.Median = Median;
                this.Pass_Rate = Pass_Rate;
                this.Fail_Rate = Fail_Rate;
                this.Number_of_Students_Above_Average = Number_of_Students_Above_Average;
                this.Number_of_Students_Below_Average = Number_of_Students_Below_Average;
    }



    public String getNumber_of_Exams() {
        return Number_of_Exams;
    }

    public void setNumber_of_Exams(String number_of_Exams) {
        Number_of_Exams = number_of_Exams;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber_Of_Courses() {
        return Number_Of_Courses;
    }

    public void setNumber_Of_Courses(String number_Of_Courses) {
        Number_Of_Courses = number_Of_Courses;
    }

    public String getLecturerName() {
        return LecturerName;
    }

    public void setLecturerName(String authorName) {
        LecturerName = authorName;
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

    public String getName_of_Courses() {
        return Name_of_Courses;
    }

    public void setName_of_Courses(String name_of_Courses) {
        Name_of_Courses = name_of_Courses;
    }

}
