package clientHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.CreateQuestionController;
import clientControllers.LecturerController;
import logic.Question;
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
		ArrayList<Question> questionList;

		if (severMessage instanceof Integer) {
			if ((Integer) severMessage == 1)
				System.out.println("Update was successful");	
			else
				System.out.println("Update wasnt so successful");
		} 
		
		else if (severMessage instanceof ArrayList) {
			
			if (((ArrayList<?>) severMessage).get(0) instanceof Question) {
				questionList = (ArrayList<Question>) severMessage;
				ArrayList<QuestionModel> listToAdd = new ArrayList<>();

				for (int i = 0; i < questionList.size(); i++) {
					listToAdd.add(new QuestionModel(questionList.get(i).getId(), 
							questionList.get(i).getSubject(),
							questionList.get(i).getCoursename(),
							questionList.get(i).getQuestiontext(), 
							questionList.get(i).getQuestionnumber(),
							questionList.get(i).getLecturer(),
							questionList.get(i).getOptionA(),
							questionList.get(i).getOptionB(),
							questionList.get(i).getOptionC(),
							questionList.get(i).getOptionD(),
							questionList.get(i).getAnswer()));
				}

				LecturerController.setQuestions(listToAdd);
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

		// Handles the error of user not found.
		else if (severMessage.toString().equals("User Not Found")) {
			System.out.println("Not Found in handler");
			user.setIsFound(false);
		}

		// Handles the error the the question already exist with that id.
		else if (severMessage.toString().contains("Question Exists")) {


		}

		// Handles the error of Not Found
		else if (severMessage.toString().contains("Not Found")) {

		}

		// Handles the error of Id Exists
		else if (severMessage.toString().contains("Id Exists")) {

		}

		else {
			// Here we recieve the confirmation of the client login
			subjectArray = ((String) severMessage).toString().split("\\s");
			user.setUsername(subjectArray[0]);
			user.setPassword(subjectArray[1]);
			user.setType(subjectArray[2]);
			// rest of the stuff in the table
			user.setIsFound(true);
		}

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

	/**
	 * Method to create query to edit existing question
	 * @param newBody
	 * @param newQNumber
	 * @param originalId
	 */
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

	/**
	 * Method that creates query for creating a question and 
	 * Passes it to server.
	 * @param Id
	 * @param subject
	 * @param Body
	 * @param QNumber
	 */
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

	/**
	 * Method that creates query for insering answers to database.
	 * @param optionA
	 * @param optionB
	 * @param optionC
	 * @param optionD
	 * @param correctAnswer
	 * @param subjectID
	 */
	public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer,
			String subjectID) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("createanswers");

		// Construct the INSERT query to create a new answer
		String query = "INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
				+ optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer + "', '"
				+ subjectID + "');";
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for sending the test to the data base.
	 * @param query 
	 */
	public void sendTestToDatabase(String query) {
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("Addtesttodata");
		listToSend.add(query);
		try {
			sendToServer((Object) listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a new arraylist subject, add an identifier "getSubjectID" so the
	 * Echoserver idenrtifies it,
	 * the second cell should contain 'subjectname' for the server to parse
	 * @param subjectname the subject to whom the search is for.
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
