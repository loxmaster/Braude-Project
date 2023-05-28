package clientHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.CreateQuestionController;
import clientControllers.LecturerController;
import logic.QuestionModel;
import logic.User;
import ocsf.client.AbstractClient;

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
				//for (int i = 0 ; i<questionList.size() ; i++) {

				//}
				// sd
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

				// assign subjectID that we've got from the server
				else if (list.get(0).equals("getSubjectID")) {
					// subjectArray = list.get(1).split(",");
					System.out.println("Client Handler: " + list.get(1));
					CreateQuestionController.setSubjectID(list.get(1));
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

		// Construct the UPDATE query to edit the question
		String query = "UPDATE `projecton`.`questions` SET `id` = '" + s + "', `questiontext` = '" + newBody
				+ "', `questionnumber` = '" + newQNumber + "' WHERE (`id` = '" + originalId + "');";
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateQuestion(String Id, String subject, String Body, String QNumber) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("createquestion");
		// Construct the INSERT query to create a new question
		String query = "INSERT INTO `projecton`.`questions` (id, subject, questiontext, questionnumber, lecturer) VALUES ('"
				+ Id + "', '" + subject + "', '" + Body + "', '" + QNumber + "', '" + user.getUsername() + "');";
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateAnswers(String optionA, String optionB, String optionC, String optionD,String correctAnswer,String subjectID) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("createanswers");

		// Construct the INSERT query to create a new answer
		String query = "INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
				+ optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer + "', '" + subjectID + "');";
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
    public void sendTestToDatabase(String query) {
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("Addtesttodata");
		listToSend.add(query);
		try {
			sendToServer((Object)listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 
	 * create a new arraylist subject, add an identifier "getSubjectID" so the
	 * Echoserver idenrtifies it,
	 * the second cell should contain 'subjectname' for the server to parse
	 */
	public void GetSubjectIDfromSubjectCourses(Object subjectname) {
		ArrayList<String> subject = new ArrayList<String>();
		subject.add("getSubjectID");

		subject.add("SELECT subjectid FROM projecton.subjectcourses where ( `subjectname` = '" + subjectname + "' );");
		try {
			sendToServer((Object) subject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method overrites super method that handles what happans when connection
	 * is closed
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
		} catch (IOException e) {
		}
		// System.exit(0);
	}

	/**
	 * This method is called by garbage collection.
	 */
	protected void finalize() {
		quit();
	}
}
