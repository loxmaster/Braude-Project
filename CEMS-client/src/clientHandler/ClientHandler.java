package clientHandlers;

import logic.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.LoginScreenController;
import clientOcsf.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ClientHandler extends AbstractClient {

  /**
   * The interface type variable. It allows the implementation of
   * the display method in the client.
   */

  ChatIF client;
  public static User user = new User();

  // Constructors ****************************************************

  public ClientHandler(String host, int port, ChatIF client) throws IOException {
    super(host, port);
    this.client = client;
    openConnection();
  }

  // Methods ****************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {
    System.out.println("got message: " + msg);
    if (msg.toString().equals("Not Found")) {
      System.out.println("Not Found in handler");
      user.setIsFound(false);
    } else {
      String[] res = ((String) msg).toString().split("\\s");
      user.setUsername(res[0]);
      user.setPassword(res[1]);
      user.setType(res[2]);
      // rest of the shit in the table
      user.setIsFound(true);
    }
    // notify the client
    // LoginScreenController.notify();
    System.out.println("--> messageFromServerHandled");
  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(Object message) {
    try {
      sendToServer(message);
    } catch (IOException e) {
      client.display("Could not send message to server.  Terminating client.");
      quit();
    }
  } 

  /**
   * Handles the message received from the client login user interface
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromLoginUI(Object email, Object password) {

    ArrayList<String> credentials = new ArrayList<String>();
    credentials.addAll(Arrays.asList((String) email, (String) password));
    try {
      sendToServer((Object) credentials);
    } catch (IOException e) {
      client.display("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit() {
    try {
      closeConnection();
    } catch (IOException e) {
    }
    System.exit(0);
  }
}
