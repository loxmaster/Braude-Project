package clientHandlers;

import logic.QuestionModel;
import logic.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.LecturerController;
import ocsf.client.*;

@SuppressWarnings("unchecked")
/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
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

	// Logic Methods ****************************************************

	// clear client data
	public static void resetClientData() {
		user = new User();
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param severMessage The message from the server.
	 */
	public void handleMessageFromServer(Object severMessage) {
		System.out.println("got message: " + severMessage);
		String[] subjectArray;
		ArrayList<String> list;
		ArrayList<QuestionModel> questionList;

		if (severMessage instanceof Integer) {
			if ((Integer) severMessage == 1)
				System.out.println("Update was successful");
			else
				System.out.println("Update wasnt so successful");
		} else if (severMessage instanceof ArrayList) {

			if (((ArrayList<?>) severMessage).get(0) instanceof QuestionModel) {
				questionList = (ArrayList<QuestionModel>) severMessage;
				LecturerController.setQuestions(questionList);
			}

			else {
				list = ((ArrayList<String>) severMessage);
				if (list.get(0).equals("lecturersubjects")) {
					subjectArray = list.get(1).split(",");
					for (String s : subjectArray) {
						LecturerController.getSubjectsList().add(s.toUpperCase());
					}
				}
			}

		}

		else if (severMessage.toString().equals("Not Found")) {
			System.out.println("Not Found in handler");
			user.setIsFound(false);
		}

		else if (severMessage.toString().contains("allsubjects")) {
			subjectArray = ((String) severMessage).toString().split(",");
			for (int i = 1; i < subjectArray.length; i++)
				LecturerController.subjectsList.add(subjectArray[i].toUpperCase());
		}

		else {
			// ["str","asd"]
			subjectArray = ((String) severMessage).toString().split("\\s");
			user.setUsername(subjectArray[0]);
			user.setPassword(subjectArray[1]);
			user.setType(subjectArray[2]);
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
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects.
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
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects for the lecturer.
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
	 * Handles the message received from the lecturer user interface gets all the
	 * questions for the lecturer.
	 */
	public void GetLeturersQuestions(String username) {
		/*
		 * try { openConnection(); } catch (IOException e1) { System.out.println(1); }
		 */
		ArrayList<String> list = new ArrayList<String>();
		list.add("lecturerquestions");
		list.add("SELECT * FROM projecton.questions where ( `lecturer` = '" + username + "' );");
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// TODO
	// UPDATE `projecton`.`questions` SET `questiontext` = 'sas', `questionnumber`
	// ='ass' WHERE (`id` = '01001');

	public void EditQuestion(String newBody, String newQNumber, String originalId) {
		ArrayList<String> list = new ArrayList<String>();
		String s = originalId.substring(0, 2) + newQNumber;
		list.add("editquestion");
		list.add("UPDATE `projecton`.`questions` SET `id` = '" + s + "', `questiontext` = '" + newBody
				+ "', `questionnumber` = '" + newQNumber + "' WHERE (`id` = '" + originalId + "');");
		// UPDATE `projecton`.`questions` SET `id` = '13', `questiontext` = '13',
		// `questionnumber` = '13' WHERE (`id` = '01001');
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method overrites super method that handles what happans when connection is closed
	 * with the server.
	 */
	protected void connectionClosed() {
		System.out.println("Connection Lost, press login to regain connection.");
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			sendToServer((Object) this.getInetAddress());
			closeConnection();
		} catch (IOException e) {}
		//System.exit(0);
	}

	/**
	 * This method is called by garbage collection.
	 */
	protected void finalize() {
		quit();
	}
}
