package clientHandlers;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        /*try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> Connected To Server ");
            Object message = fromConsole.readLine();
            client.handleMessageFromClientUI(message);
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
            ex.printStackTrace();
        }*/
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

    // gets all subject available
    public void getAllSubjects() {
        client.handleMessageFromLecturerUI();
    }

    // gets subject for specific lecturer
    public void getSubjectsForLecturer(Object username) {
        client.handleMessageFromLecturerUI(username);
    }

    // gets all of specific lecturer questions
    public void GetLecturersQuestions(String username) {
        client.GetLeturersQuestions(username);
    }

    // Sends question to data base and updating existing one
    public void EditQuestion(String newBody, String newQNumber, String originalId) {
        client.EditQuestion(newBody, newQNumber, originalId);
    }

    /**
     * Method to send question information to DataBase
     * 	// INSERT INTO `projecton`.`tests` (`id`, `duration`, `testcomments`, `authorsname`, `code`, `date`, `time`, `questions`) VALUES ('sd', 'sd', 'sd', 'sd', 'sd', 'sd', 'sd', 'sd');
     */
    public void sendTestToDatabase(Test test) {

        String query = "INSERT INTO `projecton`.`tests` (`id`, `duration`, `testcomments`, `authorsname`, `code`, `date`, `time`, `questions`) VALUES ('" + test.getId() + "','" + test.getDuration() + "', '" + test.getTestComments() + "', '" + test.getAuthor() + "', '" + test.getTestCode() + "', '" + test.getDate().getValue().toString() + "','" + test.getTime() + "', '" + test.getQuesitonsInTest() + "');";
        client.sendTestToDatabase(query);
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
