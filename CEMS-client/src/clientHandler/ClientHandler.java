package clientHandlers;

import logic.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.LecturerController;
import clientOcsf.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ClientHandler extends AbstractClient {

  /**
   * @param client       interface type variable. It allows the implementation of
   *                     the display method in the client.
   * @param User         static variable to remember localy the data about user.
   * @param subjectsList static arrayList of all the subjects available.
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

  public static void resetClientData() {
    user = new User();
    LecturerController.subjectsList = new ArrayList<String>();
    LecturerController.questions = new ArrayList<String>();
  }

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {
    System.out.println("got message: " + msg);
    String[] res;
    ArrayList<String> list;
    
    if (msg instanceof ArrayList) {
      list = ((ArrayList<String>) msg);

      if (list.get(0).equals("lecturersubjects")) {
        res = list.get(1).split(",");
        for (String s : res) {
          LecturerController.subjectsList.add(s.toUpperCase());
        }
      }

      else if (list.get(0).equals("lecturerquestions")) {
        for (int i = 1 ; i < list.size() ; i++) {
          LecturerController.questions.add(list.get(i));
        }
      }

    }

    else if (msg.toString().equals("Not Found")) {
      System.out.println("Not Found in handler");
      user.setIsFound(false);
    }

    else if (msg.toString().contains("allsubjects")) {
      res = ((String) msg).toString().split(",");
      for (int i = 1; i < res.length; i++)
        LecturerController.subjectsList.add(res[i].toUpperCase());
    }

    else {
      // ["str","asd"]
      res = ((String) msg).toString().split("\\s");
      user.setUsername(res[0]);
      user.setPassword(res[1]);
      user.setType(res[2]);
      // rest of the shit in the table
      user.setIsFound(true);
    }

    // notify the client to wake him up to tell him that the handler is done
    // client.notify();
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
   * @param username username entered.
   * @param password password entered.
   */
  public void handleMessageFromLoginUI(Object username, Object password) {

    ArrayList<String> credentials = new ArrayList<String>();
    String query = "SELECT * FROM users  WHERE (`username` = '" + username + "');";
    credentials.addAll(Arrays.asList(query, (String) username, (String) password));
    try {
      sendToServer((Object) credentials);
    } catch (IOException e) {
      client.display("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  /**
   * Handles the message received from the lecturer user interface
   * gets all the subjects.
   */
  public void handleMessageFromLecturerUI() {
    String query = "SELECT DISTINCT subjectname FROM subjectcourses;";
    // SELECT courses FROM projecton.lecturer WHERE (`username` = 'noah');
    try {
      sendToServer((Object) query);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles the message received from the lecturer user interface
   * gets all the subjects for the lecturer.
   */
  public void handleMessageFromLecturerUI(Object username) {
    String query = "SELECT courses FROM projecton.lecturer WHERE (`username` = '" + (String) username + "');";
    ArrayList<String> list = new ArrayList<String>();
    list.addAll(Arrays.asList("lecturersubjects", query));
    try {
      sendToServer((Object) list);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles the message received from the lecturer user interface
   * gets all the questions for the lecturer.
   */
  public void GetLeturersQuestions(String username) {
    
    ArrayList<String> list = new ArrayList<String>();
    list.add("lecturerquestions");
    list.add("SELECT questiontext FROM projecton.questions WHERE ( `lecturer` = '" + username + "' );");
    try {
      sendToServer((Object) list);
    } catch (IOException e) {
      e.printStackTrace();
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
