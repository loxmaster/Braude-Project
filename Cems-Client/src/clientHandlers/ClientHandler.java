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

	////////////////////////////////////////////////////////////
	/////////////////////// CLIENT NATIVE /////////////////////
	//////////////////////////////////////////////////////////

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

	// clear client data
	public static void resetClientData() {
		user = new User();
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

	////////////////////////////////////////////////////////////
	/////////////// HANDLE MESSAGHE FROM SERVER ///////////////
	//////////////////////////////////////////////////////////

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

		// TODO add a comment here
		if (severMessage instanceof Integer) {
			if ((Integer) severMessage == 1)
				System.out.println("Update was successful");
			else
				System.out.println("Update wasnt so successful");

		} else if (severMessage instanceof ArrayList) {
			if (((ArrayList<?>) severMessage).get(0) instanceof QuestionModel) {
				questionList = (ArrayList<QuestionModel>) severMessage;
				// for (int i = 0 ; i<questionList.size() ; i++) {

				// }
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
			System.err.println("Not Found in handler");
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
		// SELECT courses FROM projecton.lecturer WHERE (`username` = 'noah');
		try {
			sendToServer((Object) "SELECT DISTINCT subjectname FROM subjectcourses;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects for the lecturer.
	 */
	public void handleMessageFromLecturerUI(Object username) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("lecturersubjects",
				"SELECT courses FROM projecton.lecturer WHERE (`username` = '" + (String) username + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////
	////////////////// LOGIC METHODS /////////////////
	/////////////////////////////////////////////////

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * questions for the lecturer.
	 */
	public void GetLeturersQuestions(String username) {
		/*
		 * try { openConnection(); } catch (IOException e1) { System.out.println(1); }
		 */
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("lecturerquestions",
				"SELECT * FROM projecton.questions where ( `lecturer` = '" + username + "' );"));
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * construct a query to edit question
	 * UPDATE `projecton`.`questions` SET `questiontext` = 'sas', `questionnumber`
	 * ='ass' WHERE (`id` = '01001');
	 * 
	 * @param newBody    question body
	 * @param newQNumber question number
	 * @param originalId question original ID
	 */
	public void EditQuestion(String newBody, String newQNumber, String originalId) {
		ArrayList<String> list = new ArrayList<String>();

		// ugly will stay ugly <3
		list.addAll(Arrays.asList("editquestion",
				"UPDATE `projecton`.`questions` SET `id` = '" + originalId.substring(0, 2) + newQNumber
						+ "', `questiontext` = '" + newBody
						+ "', `questionnumber` = '" + newQNumber + "' WHERE (`id` = '" + originalId + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// return a list of grades where [testGrades, passed grades, failed grades]
	// query that selects PASSED and FAILED grades --> echoserver to parse
	// parse: [testGrades(echoServer identifier), passed(query), failed(query)]
	public void GetTestGrades_StatisticalInformation(String testID) {
		ArrayList<String> list = new ArrayList<String>();

		// FIXME fix this query
		String query_passed = "SELECT grade from projecton.testResults WHERE grade>=55";
		String query_failed = "SELECT grade from projecton.testResults WHERE grade<55";
		list.addAll(Arrays.asList("testGrades", query_passed, query_failed));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateQuestion(String Id, String subject, String Body, String QNumber) {
		ArrayList<String> list = new ArrayList<String>();

		// Construct the INSERT query to create a new question
		list.addAll(Arrays.asList("createquestion",
				"INSERT INTO `projecton`.`questions` (id, subject, questiontext, questionnumber, lecturer) VALUES ('"
						+ Id + "', '" + subject + "', '" + Body + "', '" + QNumber + "', '" + user.getUsername()
						+ "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer,
			String subjectID) {
		ArrayList<String> list = new ArrayList<String>();
		// Construct the INSERT query to create a new answer
		list.addAll(Arrays.asList("createanswers",
				"INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
						+ optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer
						+ "', '"
						+ subjectID + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTestToDatabase(String query) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("Addtesttodata", query));
		try {
			sendToServer((Object) list);
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
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("getSubjectID",
				"SELECT subjectid FROM projecton.subjectcourses where ( `subjectname` = '" + subjectname + "' );"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
