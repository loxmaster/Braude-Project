package clientHandlers;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

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
     * and continuously sends it to the server for processing.
     */
    public void accept() {
        while (true) {
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

    //gets all of specific lecturer questions
    public void GetLecturersQuestions(String username) {
        client.GetLeturersQuestions(username);
    }

    //TODO
	public void EditQuestion(String newBody, String newQNumber, String originalId) {
        client.EditQuestion(newBody, newQNumber, originalId);
	}

    /**
     * Method to send question information to DataBase
     */
    public void sendQuestion(ArrayList<String> list) {
        String query = "INSERT INTO `projecton`.`questions` (`id`, `subject`, `course name`, `question text`, `question number`, `lecturer`) VALUES ('00', 'Math', 'Calculus 1', 'how are you ?', '1', 'Misha');";
        client.handleMessageFromClientUI(query);
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

}
