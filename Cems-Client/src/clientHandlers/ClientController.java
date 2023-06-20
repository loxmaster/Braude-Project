package clientHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import logic.FileDownloadMessage;
import logic.FileUploadMessage;
import logic.QuestionModel;
import logic.Test;

public class ClientController implements ChatIF {

    // Class variables *************************************************

    /**
     * @param DEFAULT_PORT the default port to connect on.
     * @param client       The instance of the client that created this
     *                     ClientController.
     */
    final public static int DEFAULT_PORT = 5555;
    ClientHandler client;

    // Constructors ****************************************************

    /**
     * Constructs an instance of the ClientConsole UI.
     *
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    public ClientController(String host, int port) {
        try {
            client = new ClientHandler(host, port, this);
        } catch (IOException exception) {
            System.out.println("Error: Can't setup connection!" + " Terminating client.");
            System.exit(1);
        }
    }

    /**
     * This method overrides the method in the ChatIF interface. It displays a
     * message onto the screen.
     *
     * @param message The string to be displayed.
     */
    @Override
    public void display(Object message) {
        if (message instanceof ResultSet) {
            ResultSet result = (ResultSet) message;
            try {
                System.out.println("Found: " + result);
            } catch (Exception e) {
                System.out.println("failed read");
                e.printStackTrace();
            }
        } else
            System.out.println("> " + (String) message.toString());
    }

    /**
     * Method that uses the handler function to close connection.
     * Notifices server when closed.
     */
    public void quit() {
        client.quit();
    }

    /**
     * Method that uses the handler function to open connection.
     * If connection already open nothing happans.
     */
    public void openConnection() {
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getSelectedAnswers(String studentID, String id) {
        ArrayList<String> list = new ArrayList<>();
        list.add("getSelectedAnswers");
        list.add("SELECT selected FROM projecton.completed_tests WHERE test_id='" + id + "' AND student_id= '"
                + studentID + "';");
        client.passToServer(list);
    }

    public void SendEvaluatedTest(String id, String studentID, String text, String testcomments) {
        ArrayList<String> saveChanges = new ArrayList<>();
        saveChanges.add("testEval");
        saveChanges.add("UPDATE `projecton`.`completed_tests` SET `grade` = '" + text
                + "', `tested` = 'true', `comment` = '" + testcomments + "' WHERE (`test_id` = '" + id
                + "') and (`student_id` = '" + studentID + "');");
        client.passToServer(saveChanges);
    }

    // /public void getSelectedAnswers(String studentID) {
    // getSelectedAnswers();
    // }

    /**
     * Accepts user input from the console and sends it to the server for
     * processing.
     */
    public void accept() {
        try {
            // Create a BufferedReader to read user input from the console.
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> Connected To Server ");

            // Read a line of input from the console.
            Object message = fromConsole.readLine();

            // Pass the message to the ClientHandler to handle and send to the server.
            client.handleMessageFromClientUI(message);
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
            ex.printStackTrace();
        }
    }

    /**
     * Accepts user input from the login screen.
     * 
     * @param username user username
     * @param password user password
     */
    public void loginVarification(Object username, Object password, Object type) {
        ArrayList<String> credentials = new ArrayList<String>();
        // create a query to grab username requested
        String name = (String) username;
        String pass = (String) password;
        String role = (String) type;
        String query = String.format(
                "SELECT * FROM projecton.users  WHERE username = '%s' AND password = '%s' AND type = '%s';",
                name, pass, role);
        credentials.addAll(Arrays.asList(query, name, pass, role));
        // pass email and password to the client for authentication
        client.passToServer((Object) credentials);
    }

    public void Update_HOD_permissionsTable_inDB(Test test) {
        ArrayList<String> request = new ArrayList<>();
        String query = "DELETE FROM hod_timeextensionrequests WHERE id = " + test.getId();
        request.add("updateHODPermissionsTable");
        request.add(query);
        openConnection();
        client.passToServer((Object) request);
    }

    public void fetch_ongoingTests_permissions_list() {
        ArrayList<String> ongoingTests_permissions_list = new ArrayList<String>();
        String query = "SELECT * FROM hod_timeextensionrequests;";
        ongoingTests_permissions_list.add("fetch_ongoingTests_permissions_FromDB");
        ongoingTests_permissions_list.add(query);
        openConnection();
        client.passToServer((Object) ongoingTests_permissions_list);
    }

    // get all the ongoing tests
    public void GetOngoingTests() {
        ArrayList<String> request = new ArrayList<>();
        request.add("fetchOngoingTests");
        client.passToServer((Object) request);
    }

    // update time extension requests in hod_timeextensionrequests table
    public void SendRequest_TimeExtention_toHOD(String id, String comments, String timeToAdd, String subject) {
        ArrayList<String> listToSend = new ArrayList<String>();
        String query = "INSERT INTO `projecton`.`hod_timeextensionrequests` (`id`,`Subject`, `TimeToAdd`, `Reason`) VALUES ('"
                + id + "','" + subject + "', '" + timeToAdd + "', '"
                + comments + "');";
        listToSend.add("Update_timeExtensionRequestsTable");
        listToSend.add(query);
        client.passToServer((Object) listToSend);
    }

    public void updateLockButton_DB(Test test, String value) {
        ArrayList<String> list = new ArrayList<String>();
        String query = "UPDATE ongoing_tests SET locked = '" + value + "' WHERE test_id = " + test.getId();
        list.add("updateLockButton_DB");
        list.add(query);
        client.passToServer((Object) list);
    }

    // gets all subject available for lecturer
    public void getcompletedTestsForStudentList() {
        ArrayList<String> list = new ArrayList<String>();
        String key = ClientHandler.user.getUser_id(); // TODO may couse problem
        String testType = "computer";
        String status = "completed";
        String tested = "true";
        String query = String.format(
                "SELECT * FROM projecton.completed_tests WHERE student_id='%s' AND test_type='%s' AND status='%s' AND tested='%s';",
                key, testType, status, tested);

        list.addAll(Arrays.asList("completedTestsForStudent", query));
        client.passToServer((Object) list);
        // client.getcompletedTestsForStudentList();
    }

    public void getcompletedTestsForLecturerList() {
        ArrayList<String> list = new ArrayList<String>();
        String status = "completed";
        String tested = "false";
        String query = String.format(
                "SELECT * FROM projecton.completed_tests WHERE authorsname='%s' AND status='%s' AND tested='%s';",
                ClientHandler.user.getUsername(), status, tested); // TODO may couse problem

        list.addAll(Arrays.asList("completedTestsForLecturer", query));
        client.passToServer((Object) list);
        // client.getcompletedTestsForLecturerList();
    }

    public void getAllTestsOfLecturer() {
        ArrayList<String> list = new ArrayList<String>();
        String query = String.format(
                "SELECT * FROM projecton.tests WHERE authorsname='%s' AND id NOT IN (SELECT test_id FROM projecton.completed_tests ) AND id NOT IN (SELECT test_id FROM projecton.ongoing_tests)",
                ClientHandler.user.getUsername()); // TODO may couse problems
        list.addAll(Arrays.asList("futureTests", query));
        client.passToServer((Object) list);
    }

    // fetch data for hod in statistic on lecterurs
    public void getLecturerListUnderSameDepartment() {
        ArrayList<String> list = new ArrayList<String>();
        String query = String.format("SELECT * FROM projecton.users  WHERE type = 'lecturer' AND department = '%s';",
                ClientHandler.user.getDepartment()); // TODO may couse problem
        list.addAll(Arrays.asList("LecturerListUnderSameDepartment", query));
        client.passToServer((Object) list);
        // client.getLecturerListUnderSameDepartment();
    }

    // fetch data for hod in statistic on lecterurs
    public void HodGETcompletedTestsForSpecificLecturerList(String userName) {
        ArrayList<String> list = new ArrayList<String>();
        String status = "completed";
        String tested = "true";
        String query = String.format(
                "SELECT * FROM projecton.completed_tests WHERE authorsname='%s' AND status='%s' AND tested='%s';",
                userName, status, tested);
        list.addAll(Arrays.asList("HodGETcompletedTestsForSpecificLecturerList", query));
        client.passToServer((Object) list);
        // client.HodGETcompletedTestsForSpecificLecturerList(userName);
    }

    // fetch data for hod in statistic on lecterurs
    public void getHodCourseForTestSpecificLec(Object id) {
        ArrayList<String> subjectcoursenameofcompletedtest = new ArrayList<String>();
        String subjectid = ((String) id).substring(0, 2);
        String courseid = ((String) id).substring(2, 4);
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses WHERE subjectid='%s' AND courseid='%s';",
                subjectid, courseid);
        subjectcoursenameofcompletedtest.addAll(Arrays.asList("getHodSubjectsCourseForTestSpecificLec", query));
        client.passToServer((Object) subjectcoursenameofcompletedtest);
        // client.getHodCourseForTestSpecificLec((String) id);
    }

    // fetch data for hod in statistic on Students
    public void geStudentListUnderSameDepartment() {
        ArrayList<String> list = new ArrayList<String>();
        String query = String.format(
                "SELECT * FROM projecton.users  WHERE type = 'student' AND department = '%s';",
                ClientHandler.user.getDepartment()); // TODO may couse problems
        list.addAll(Arrays.asList("studentListUnderSameDepartment", query));
        client.passToServer((Object) list);
        // client.geStudentListUnderSameDepartment();
    }

    // fetch data for hod in statistic on lecterurs
    public void HodGETcompletedTestsForSpecificStudentList(String userID) {
        ArrayList<String> list = new ArrayList<String>();
        String status = "completed";
        String tested = "true";
        String query = String.format(
                "SELECT * FROM projecton.completed_tests WHERE student_id='%s' AND status='%s' AND tested='%s';",
                userID, status, tested);

        list.addAll(Arrays.asList("HodGETcompletedTestsForSpecificStudentList", query));
        client.passToServer((Object) list);
        // client.HodGETcompletedTestsForSpecificStudentList(userID);
    }

    // fetch data for hod in statistic on lecterurs
    public void getHodCourseForTestSpecificStudent(Object id) {
        ArrayList<String> subjectcoursenameofcompletedtest = new ArrayList<String>();
        String subjectid = ((String) id).substring(0, 2);
        String courseid = ((String) id).substring(2, 4);
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses WHERE subjectid='%s' AND courseid='%s';",
                subjectid, courseid);
        subjectcoursenameofcompletedtest.addAll(Arrays.asList("getHodCourseForTestSpecificStudent", query));
        client.passToServer((Object) subjectcoursenameofcompletedtest);
    }

    // gets all subject available for lecturer
    public void getCourseForTest(Object id) {
        ArrayList<String> subjectcoursenameofcompletedtest = new ArrayList<String>();
        String subjectid = ((String) id).substring(0, 2);
        String courseid = ((String) id).substring(2, 4);
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses WHERE subjectid='%s' AND courseid='%s';",
                subjectid, courseid);
        subjectcoursenameofcompletedtest.addAll(Arrays.asList("getSubjectsCourseForTest", query));
        client.passToServer((Object) subjectcoursenameofcompletedtest);
    }

    // gets all subject available for lecturer
    public void getCourseForTestLec(Object id) {
        ArrayList<String> subjectcoursenameofcompletedtest = new ArrayList<String>();
        String subjectid = ((String) id).substring(0, 2);
        String courseid = ((String) id).substring(2, 4);
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses WHERE subjectid='%s' AND courseid='%s';",
                subjectid, courseid);
        subjectcoursenameofcompletedtest.addAll(Arrays.asList("getSubjectsCourseForTestLec", query));
        client.passToServer((Object) subjectcoursenameofcompletedtest);
    }

    // gets the id of the subject given
    public void GetSubjectIDfromSubjectCourses(String subjectname) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("getSubjectID",
                "SELECT subjectid FROM projecton.subjectcourses where ( `subjectname` = '" + subjectname + "' );"));
        client.passToServer((Object) list);
    }

    // gets all subject available for lecturer
    public void getSubjectsForLecturer(Object username) {
        ArrayList<String> subjectList = new ArrayList<String>();

        subjectList.addAll(Arrays.asList("lecturersubjects",
                "SELECT department FROM projecton.users WHERE (`username` = '" + (String) username + "');"));
        client.passToServer((Object) subjectList);
    }

    // gets all courses available for lecturer
    public void getCoursesForLecturer(Object username) {
        ArrayList<String> courseList = new ArrayList<String>();

        courseList.addAll(Arrays.asList("lecturercourses",
                "SELECT courses FROM projecton.lecturer WHERE username = '" + (String) username + "';"));
        client.passToServer((Object) courseList);
    }

    // sends query to create qeustion with data
    public void CreateQuestion(String Id, String subject, String course, String Body, String QNumber) {
        ArrayList<String> list = new ArrayList<String>();

        // Construct the INSERT query to create a new question
        list.addAll(Arrays.asList("createquestion",
                "INSERT INTO `projecton`.`questions` (`id`, `lecturer`, `subject`, `coursename`, `questiontext`, `questionnumber`) VALUES ('"
                        + Id + "','" + ClientHandler.user.getUsername() + "', '" + subject + "', '" + course + "', '"
                        + Body + "', '"
                        + QNumber + "');")); // TODO may couse problems ClientHandler.user.getUsername()
        client.passToServer((Object) list);
        // lient.CreateQuestion(Id, subject, course, Body, QNumber);
    }

    // sends query to create answers for question
    public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer,
            String subjectID) {
        ArrayList<String> list = new ArrayList<String>();
        // Construct the INSERT query to create a new answer
        String query = "INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
                + optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer + "', '"
                + subjectID + "');";
        list.add("createanswers");
        list.add(query);
        client.passToServer((Object) list);
    }

    // gets all of specific lecturer questions
    public void GetLecturersQuestions(Object username) {
        ArrayList<String> list = new ArrayList<String>();

        // '*' returns every question, it's used in CreateTestController
        if ((String) username == "*")
            list.addAll(Arrays.asList("lecturerquestions", "SELECT * FROM projecton.questions;"));
        else
            list.addAll(Arrays.asList("lecturerquestions",
                    "SELECT * FROM projecton.questions where ( `lecturer` = '" + (String) username + "' );"));
        client.passToServer((Object) list);
    }

    // Sends question to data base and updating existing one
    public void EditQuestion(String NewID, String subject, String course, String qBody, String qnumber,
            String originalId) {
        ArrayList<String> list = new ArrayList<String>();
        // ugly will stay ugly <3
        list.addAll(Arrays.asList("editquestion",
                "UPDATE `projecton`.`questions` SET `id` = '" + NewID
                        + "', `lecturer` = '" + ClientHandler.user.getUsername() // TODO can couse prooblems
                        + "', `subject` = '" + subject
                        + "', `coursename` = '" + course
                        + "', `questiontext` = '" + qBody
                        + "', `questionnumber` = '" + qnumber + "' WHERE (`id` = '" + originalId + "');"));
        client.passToServer((Object) list);
        // client.EditQuestion(NewID, subject, course, qBody, qnumber, originalId);
    }

    public void EditAnswers(String subjectid, String qA, String qB, String qC, String qD, String correctAnswer) {
        ArrayList<String> list = new ArrayList<String>();

        // ugly will stay ugly <3
        list.addAll(Arrays.asList("editquestion",
                "UPDATE `projecton`.`answers` SET `optionA` = '" + qA
                        + "', `optionB` = '" + qB
                        + "', `optionC` = '" + qC
                        + "', `optionD` = '" + qD
                        + "', `correctAnswer` = '" + correctAnswer + "' WHERE (`questionid` = '" + subjectid + "');"));
        client.passToServer((Object) list);
    }

    /**
     * Method to send question information to DataBase
     * 
     * @param test the test to add
     */
    public void sendTestToDatabase(Test test) {

        // Adding the Id`s of the questions in the test and its points
        ArrayList<String> questionIdList = new ArrayList<>();
        ArrayList<String> questionPoints = new ArrayList<>();
        for (QuestionModel question : test.getQuesitonsInTest()) {
            questionIdList.add(question.getId());
            questionPoints.add("" + question.getPoints());
        }

        String query = "INSERT INTO `projecton`.`tests` (`id`, `duration`, `testcomments`, `authorsname`, `code`, `date`, `time`, `questions`, `points`) VALUES ('"
                + test.getId() + "','" + test.getDuration() + "', '" + test.getTestComments() + "', '"
                + test.getAuthor() + "', '" + test.getTestCode() + "', '" + test.getDate().getValue().toString() + "','"
                + test.getTime() + "', '" + questionIdList + "', '" + questionPoints + "');";

        ArrayList<String> listToSend = new ArrayList<String>();
        listToSend.add("Addtesttodata");
        listToSend.add((String) query);
        client.passToServer((Object) listToSend);
    }

    public void getNextFreeTestNumber(Object coursename) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("testNumber",
                "SELECT MAX(CAST(SUBSTRING(id, 5, 2) AS UNSIGNED)) AS max_test_number FROM tests WHERE SUBSTRING(id, 1, 4) = '"
                        + (String) coursename + "';"));
        client.passToServer((Object)list);
    }

    public void GetCourseIDfromSubjectCourses(Object coursename) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("getCourseID",
                "SELECT courseid FROM projecton.subjectcourses where ( `coursename` = '" + (String) coursename
                        + "' );"));
        client.passToServer((Object)list);
    }

    public void getTestWithCodeForStudent(String testCode) {
        ArrayList<String> listOfCommands = new ArrayList<>();
        listOfCommands.addAll(
                Arrays.asList("gettestwithcode", "SELECT * FROM projecton.tests where code = '" + testCode + "';"));

        client.passToServer((Object) listOfCommands);
    }

    public void getTestWithCodeFor_CompletedTest(Test test) {
        ArrayList<String> listOfCommands = new ArrayList<>();
        listOfCommands.addAll(
                Arrays.asList("check test",
                        "SELECT * FROM projecton.completed_tests where test_id = '" + test.getId()
                                + "' AND student_id = '" + test.getStudentID() + "';"));
        client.passToServer((Object) listOfCommands);
    }

    public void sendToCompletedTest(Test localTest) {
        // Creating the quesitons Id list and selected question list.
        ArrayList<String> questionIdList = new ArrayList<>();
        ArrayList<String> SelectedQuestions = new ArrayList<>(); // This list represents the student selected questions
        ArrayList<String> pointsList = new ArrayList<>(); // A list for the questions points

        // Creates Id list of questions And list of points
        for (QuestionModel question : localTest.getQuesitonsInTest()) {
            questionIdList.add(question.getId());
            pointsList.add(question.getPoints());
            SelectedQuestions.add(question.getSelected());
        }

        // Runs over the current test and checks it - grades it.
        int grade = 0;
        for (QuestionModel question : localTest.getQuesitonsInTest()) {
            if (question.getAnswer().equals(question.getSelected())) {
                grade += Integer.parseInt(question.getPoints());
                System.out.println(question.getPoints());
            }
        }

        String query = "INSERT INTO `projecton`.`completed_tests` (`test_id`, `student_id`, `grade`, `authorsname`, `code`, `date`, `time`,"
                +
                " `duration`, `questions`, `test_type`, `status`,  `selected` , `points`) VALUES ('" + localTest.getId()
                + "', '" + ClientHandler.user.getUser_id() + "', '" + grade +
                "', '" + localTest.getAuthor() + "', '" + localTest.getTestCode() + "', '" + "13-05-2023" + "', '"
                + localTest.getTime() + "', '" + localTest.getDuration() +
                "', '" + questionIdList + "', '" + "computer" + "', '" + "completed" + "', '" + SelectedQuestions
                + "', '" + pointsList + "');";

        ArrayList<String> listToSend = new ArrayList<>();
        listToSend.addAll(Arrays.asList("sendtocompletedtest", query));
        client.passToServer((Object) listToSend);
    }

    public void uploadFile(String fileId, byte[] fileContent, String filename) {
        try {
            openConnection();
            client.sendToServer(new FileUploadMessage(fileId, fileContent, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String fileId) {
        try {
            client.sendToServer(new FileDownloadMessage(fileId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void isStudentTakingCourse() throws IOException {
        ArrayList<String> sendToServer = new ArrayList<>();
        sendToServer.add("isStudentTakingCourse");
        sendToServer.add(
                "SELECT code,id FROM student s JOIN subjectcourses sc ON FIND_IN_SET(sc.coursename, s.courses) > 0 JOIN tests t ON SUBSTRING(t.id, 3, 2) = sc.courseid WHERE s.username = '"
                        + ClientHandler.user.getUsername() + "' AND SUBSTRING(t.id, 1, 2) = sc.subjectid;");
        client.passToServer((Object) sendToServer);
    }

    public void isTestReady(String test_id) throws IOException {
        ArrayList<String> sendToServer = new ArrayList<>();
        sendToServer.add("isTestReady");
        sendToServer.add(test_id);

        sendToServer.add("SELECT test_id FROM ongoing_tests WHERE( (SELECT id FROM tests WHERE (id = '"
                + sendToServer.get(1) + "' ) AND id = test_id) )");
        client.passToServer((Object) sendToServer);
    }

    public void getTestFromId(String test_id) throws IOException {
        ArrayList<String> sendToServer = new ArrayList<>();
        sendToServer.add("getTest");
        sendToServer.add(test_id);
        sendToServer.add("SELECT questions FROM projecton.tests WHERE (id = '" + sendToServer.get(1) + "')");
        client.passToServer((Object) sendToServer);

    }

    public void DeleteQuestion(String originalId) {
        ArrayList<String> listToSend = new ArrayList<String>();
        listToSend.add("DeleteQuestion");
        listToSend.add("DELETE FROM `projecton`.`questions` WHERE (`id` = '" + originalId + "');");
        client.passToServer((Object) listToSend);
    }

    public void getCoursesSameDepartment() {
        ArrayList<String> list = new ArrayList<String>();
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses  WHERE subjectname ='%s';",
                ClientHandler.user.getDepartment()); // TODO may couse problems

        list.addAll(Arrays.asList("getCoursesSameDepartment", query));
        client.passToServer((Object) list);
    }

    public void getCoursesExams(String courseID) {
        ArrayList<String> list = new ArrayList<String>();
        String query = String.format(
                "SELECT * FROM projecton.completed_tests WHERE test_id LIKE '%s%%' AND status='completed' AND tested='true';",
                courseID);
        list.addAll(Arrays.asList("getCoursesExams", query));
        client.passToServer((Object) list);
    }

}
