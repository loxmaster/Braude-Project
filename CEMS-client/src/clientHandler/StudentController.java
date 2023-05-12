package clientHandlers;

import java.io.*;
import java.sql.ResultSet;

public class StudentController implements ChatIF {
    // Class variables *************************************************

    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 5555;

    // Instance variables **********************************************

    /**
     * The instance of the client that created this ConsoleChat.
     */
    ClientHandler client;
 
    // Constructors ****************************************************

    /**
     * Constructs an instance of the ClientConsole UI.
     *
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    public StudentController(String host, int port) {
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
     * input from the login screen.
     * @param email user email
     * @param password user password
     */
    public void acceptLogin(Object email, Object password) {
        try {
            // pass email and password to the client for authentication
            client.handleMessageFromLoginUI(email, password);
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from UI!");
            ex.printStackTrace();
        }

    }

    /**
     * Method to send question information to DataBase
     * 
     */
    public void sendQuestion() {
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
    public void display(Object message) // this
    {
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
