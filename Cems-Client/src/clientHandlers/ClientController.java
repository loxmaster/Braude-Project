package clientHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @param email    user email
     * @param password user password
     */
    public void loginVarification(Object email, Object password) {
        try {
            // pass email and password to the client for authentication
            client.handleMessageFromLoginUI(email, password);
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from UI!");
            ex.printStackTrace();
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
    public void CreateQuestion(String Id, String subject, String Body, String QNumber) {
        try {
            client.CreateQuestion(Id, subject, Body, QNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sends query to create answers for question
    public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer,
            String subjectID) {
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
    public void EditQuestion(String newBody, String newQNumber, String originalId) {
        try {
            client.EditQuestion(newBody, newQNumber, originalId);
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

    public void getTestWithCodeForStudent(String testName) {
        client.getTestWithCodeForStudent(testName);
    }

    public void sendToCompletedTest(Test localTest) {
        // Creating the quesitons Id list and selected question list.
        ArrayList<String> questionIdList = new ArrayList<>();
        ArrayList<String> SelectedQuestions = new ArrayList<>(); // This list represents the student selected questions

        for (QuestionModel question : localTest.getQuesitonsInTest()) {
            questionIdList.add(question.getId());
            SelectedQuestions.add(question.getSelected());
        }
        String query = "INSERT INTO `projecton`.`completed_tests` (`test_id`, `student_id`, `grade`, `authorsname`, `code`, `date`, `time`," +
        " `duration`, `questions`,  `selected`) VALUES ('" + localTest.getId() + "', '" + "332" + "', '" + "grade" + 
        "', '" + localTest.getAuthor() + "', '" + localTest.getTestCode() + "', '" + "localDate" + "', '" + localTest.getTime() + "', '" + localTest.getDuration() + 
        "', '" + questionIdList + "', '" + SelectedQuestions + "');";

        ArrayList<String> listToSend = new ArrayList<>();
        listToSend.addAll(Arrays.asList("sendtocompletedtest" , query));
        client.sendToCompletedTest((Object) listToSend);
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

}
