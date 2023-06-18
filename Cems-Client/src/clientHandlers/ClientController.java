package clientHandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import logic.FileDownloadMessage;
import logic.FileUploadMessage;
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

    // Instance methods ************************************************

    /**
     * Accepts user input from the console
     * and sends it to the server for processing.
     */
    public void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> Connected To Server ");
            Object message = fromConsole.readLine();
            client.handleMessageFromClientUI(message);
        } catch (Exception ex) {

            System.out.println("Unexpected error while reading from console!");
            ex.printStackTrace();
        }

    }

    /**
     * Accepts user input from the login screen.
     * 
     * @param username    user username
     * @param password user password
     */
    public void loginVarification(Object username, Object password,Object type) {
        try {
            // pass email and password to the client for authentication
            client.handleMessageFromLoginUI(username, password,type);
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from UI!");
            ex.printStackTrace();
        }
    }

    // gets all subject available for lecturer
    public void getcompletedTestsForStudentList() {
        try {
            client.getcompletedTestsForStudentList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getcompletedTestsForLecturerList() {
        try {
            client.getcompletedTestsForLecturerList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gets all subject available for lecturer
    public void getCourseForTest(Object id) {
        try {
            client.getCourseForTest((String) id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        // gets all subject available for lecturer
    public void getCourseForTestLec(Object id) {
        try {
            client.getCourseForTestLec((String) id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // gets the id of the subject given
    public void GetSubjectIDfromSubjectCourses(String subjectname) {
        try {
            client.GetSubjectIDfromSubjectCourses(subjectname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gets all subject available for lecturer
    public void getSubjectsForLecturer(Object username) {
        try {
            client.handleMessageFromLecturerUI(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gets all courses available for lecturer
    public void getCoursesForLecturer(Object username) {
        try {
            client.handle_test_MessageFromLecturerUI(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sends query to create qeustion with data
    public void CreateQuestion(String Id, String subject, String course, String Body, String QNumber) {
        try {

            client.CreateQuestion(Id, subject, course, Body, QNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // sends query to create answers for question
    public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer, String subjectID) {
        try {

            client.CreateAnswers(optionA, optionB, optionC, optionD, correctAnswer, subjectID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gets all of specific lecturer questions
    public void GetLecturersQuestions(Object object) {
        try {
            client.GetLecturersQuestions_Handler((String) object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sends question to data base and updating existing one
    public void EditQuestion(String NewID, String subject,String course, String qBody, String qnumber , String originalId) {
        try {
            client.EditQuestion(NewID, subject,course ,qBody,qnumber, originalId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void EditAnswers(String subjectid, String qA,String qB,String  qC ,String qD, String correctAnswer){
        try {
            
            client.EditAnswers(subjectid, qA, qB, qC, qD, correctAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to send question information to DataBase
     * 
     * @param test the test to add
     */
    public void sendTestToDatabase(Test test) {

        String query = "INSERT INTO `projecton`.`tests` (`id`, `duration`, `testcomments`, `authorsname`, `code`, `date`, `time`, `questions`) VALUES ('"
                + test.getId() + "','" + test.getDuration() + "', '" + test.getTestComments() + "', '"
                + test.getAuthor() + "', '" + test.getTestCode() + "', '" + test.getDate().getValue().toString() + "','"
                + test.getTime() + "', '" + test.getQuesitonsInTest() + "');";

        client.sendTestToDatabase((Object) query);
    }

    public void getNextFreeTestNumber(Object coursename) {

        try {
            client.getNextFreeTestNumber(coursename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetCourseIDfromSubjectCourses(Object coursename) {
        try {
            client.GetCourseIDfromSubjectCourses(coursename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCompletedTestToDatabase(Test test) {

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

    public void uploadFile(String fileId, byte[] fileContent, String filename) {
        try {
            //openConnection();
            client.sendToServer(new FileUploadMessage(fileId, fileContent,filename));
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
        client.isStudentTakingCourse(sendToServer);
    }
    
    public void isTestReady(String test_id) throws IOException {
        ArrayList<String> sendToServer = new ArrayList<>();
        sendToServer.add("isTestReady");
        sendToServer.add(test_id);
        client.isTestReady(sendToServer);

    }

    public void getTestFromId(String test_id) throws IOException {
        ArrayList<String> sendToServer = new ArrayList<>();
        sendToServer.add("getTest");
        sendToServer.add(test_id);
        client.getTestFromId(sendToServer);

    }

    public void DeleteQuestion(String originalId) {
        try {
            client.DeleteQuestion(originalId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
