package clientHandlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;


import clientControllers.CreateQuestionController;
import clientControllers.CreateTestController;
import clientControllers.EnterIDForTestController;
import clientControllers.LecturerController;
import javafx.stage.FileChooser;
import logic.FileDownloadMessage;
import logic.Question;
import logic.QuestionModel;
import logic.User;
import ocsf.client.AbstractClient;

@SuppressWarnings("unchecked")

/**
 * This class overrides some of the methods defined in the abstract superclass
 * AbstractClient in order to give more functionality to the client .
 */
public class ClientHandler extends AbstractClient {

	/**
	 * @param client       interface type variable. It allows the implementation of
	 *                     the comunication with the client .
	 * @param User         static variable to remember localy the data about the
	 *                     user .
	 * @param subjectsList static arrayList of all the subjects available .
	 */
	ChatIF client;
	public static User user = new User();

	/**
	 * Contructor for this instance of ClientHandler. Also, opens a connection
	 * to the server (If connection already open does nothing) .
	 * 
	 * @param host
	 * @param port
	 * @param client
	 * @throws IOException
	 */
	public ClientHandler(String host, int port, ChatIF client) throws IOException {
		super(host, port);
		this.client = client;
		openConnection();
	}

	/**
	 * This method handles all data that comes in from the server .
	 * 
	 * @param serverMessage The message from the server .
	 */
	public void handleMessageFromServer(Object serverMessage) {

		// Local parameters.
		String[] subjectArray;
		ArrayList<String> list;
		ArrayList<Question> questionList;

		// Notifice about recieving the message to console .
		System.out.println("got message: " + serverMessage);

		if (serverMessage instanceof FileDownloadMessage) {
			FileDownloadMessage downloadMessage = (FileDownloadMessage) serverMessage;
			EnterIDForTestController.setDownloadMessage(downloadMessage);
			// Pass the downloaded file message to the client controller

		}
		// Switch function on the name of the class that message was the sent .
		switch (serverMessage.getClass().getSimpleName()) {

			// If got Integer then its a result of the UpdateQuery. If 1 then successful .
			case "Integer":
				if ((Integer) serverMessage == 1)
					System.out.println("Update was successful");
				else
					System.out.println("Update wasnt so successful");
				break;

			// If message type ArrayList it means that data comes in , where first index (0)
			// is reserved for destination , and second (1) index is the data itself.
			case "ArrayList":

				// If first instance is Question than its a question list.
				if (((ArrayList<?>) serverMessage).get(0) instanceof Question) {
					questionList = (ArrayList<Question>) serverMessage;
					ArrayList<QuestionModel> listToAdd = new ArrayList<>();

					for (int i = 0; i < questionList.size(); i++) {
						listToAdd.add(new QuestionModel(
								questionList.get(i).getId(),
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
					list = (ArrayList<String>) serverMessage;
					switch (list.get(0)) {

						case "lecturersubjects":
							subjectArray = list.get(1).split(",");
							for (String s : subjectArray)
								LecturerController.getSubjectsList().add(s);
							break;

						case "isStudentTakingCourse":
							EnterIDForTestController.setTest_code(list.get(1));

							break;

						case "lecturercourses":
							list.remove(0);
							LecturerController.getCoursesList().addAll(list);
							break;

						case "getSubjectID":
							System.out.println("Client Handler: " + list.get(1));
							CreateQuestionController.setSubjectID(list.get(1));
							break;

						case "getCourseID":
							System.out.println("Client Handler: got course ID " + list.get(1) + "from the database");
							CreateQuestionController.setCourseID(list.get(1));
							break;

						case "testNumber":
							// if we got null, we start a new test number starting from 01
							// for example: 0101 doesn't exist; add 010100
							if (list.get(1) == null) {
								CreateTestController.setNextTestNumber("01");
								System.out.println("Client Handler: got test number, " + list.get(1)
										+ " from the database: making a new test");
							} else {
								int format = Integer.parseInt(list.get(1));
								format++;
								String format2 = "0" + format;
								// CreateQuestionController.testcount = format2.substring(2, 4);
								CreateTestController.setNextTestNumber(format2);
							}

							System.out.println("Client Handler: " + list.get(1));
							break;

					}
				}
				break;

			case "String":
				switch ((String) serverMessage) {
					case "User Not Found":
						System.err.println("Not Found in handler");
						user.setIsFound(false);
						break;

					case "File Uploaded Successfully!":
					case "Error! File Upload Failed!":
					case "File not Found!":
						JOptionPane.showMessageDialog(null, (String) serverMessage, (String) serverMessage,
								JOptionPane.INFORMATION_MESSAGE);
						break;

					case "Question Exists":
					case "Not Found":
					case "Id Exists":
						ClientUI.updatestatus = 0;
						break;
					default:
						// Here we recieve the confirmation of the client login
						subjectArray = ((String) serverMessage).toString().split("\\s");
						user.setUsername(subjectArray[0]);
						user.setPassword(subjectArray[1]);
						user.setType(subjectArray[2]);
						// rest of the stuff in the table
						user.setIsFound(true);
						break;
				}

				break;
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
		try {
			sendToServer((Object) "SELECT DISTINCT subjectname FROM subjectcourses;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects for the lecturer.
	 * 
	 * @return
	 */
	public void handleMessageFromLecturerUI(Object username) {
		ArrayList<String> subjectList = new ArrayList<String>();

		subjectList.addAll(Arrays.asList("lecturersubjects",
				"SELECT courses FROM projecton.lecturer WHERE (`username` = '" + (String) username + "');"));

		try {
			sendToServer((Object) subjectList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handle_test_MessageFromLecturerUI(Object username) {
		ArrayList<String> courseList = new ArrayList<String>();

		courseList.addAll(Arrays.asList("lecturercourses",
				"SELECT sc.coursename FROM lecturer l JOIN subjectcourses sc ON find_in_set( sc.subjectname, l.courses) WHERE (l.username =  '"
						+ (String) username + "' );"));
		try {
			sendToServer((Object) courseList);
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
	 * 
	 * @param username lecturers username
	 */
	public void GetLecturersQuestions_Handler(String username) {
		/*
		 * try { openConnection(); } catch (IOException e1) { System.out.println(1); }
		 */
		ArrayList<String> list = new ArrayList<String>();

		// '*' returns every question, it's used in CreateTestController
		if (username == "*")
			list.addAll(Arrays.asList("lecturerquestions", "SELECT * FROM projecton.questions;"));
		else
			list.addAll(Arrays.asList("lecturerquestions",
					"SELECT * FROM projecton.questions where ( `lecturer` = '" + username + "' );"));
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to create query to edit existing question
	 * 
	 * @param newBody
	 * @param newQNumber
	 * @param originalId
	 */
	public void EditQuestion(String NewID, String subject,String course, String qBody, String qnumber , String originalId) {
		ArrayList<String> list = new ArrayList<String>();

		// ugly will stay ugly <3
		list.addAll(Arrays.asList("editquestion",
				"UPDATE `projecton`.`questions` SET `id` = '" + NewID
						+ "', `lecturer` = '" + user.getUsername()
						+ "', `subject` = '" + subject
						+ "', `coursename` = '" + course
						+ "', `questiontext` = '" + qBody
						+ "', `questionnumber` = '" + qnumber + "' WHERE (`id` = '" + originalId + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		public void EditAnswers(String subjectid, String qA,String qB, String qC, String qD , String correctAnswer) {
		ArrayList<String> list = new ArrayList<String>();

		// ugly will stay ugly <3
		list.addAll(Arrays.asList("editquestion",
				"UPDATE `projecton`.`answers` SET `optionA` = '" + qA
						+ "', `optionB` = '" + qB
						+ "', `optionC` = '" + qC
						+ "', `optionD` = '" + qD
						+ "', `correctAnswer` = '" + correctAnswer + "' WHERE (`questionid` = '" + subjectid + "');"));

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

	/**
	 * Method that creates query for creating a question and
	 * Passes it to server.
	 * 
	 * @param Id
	 * @param subject
	 * @param Body
	 * @param QNumber
	 */
	public void CreateQuestion(String Id, String subject,String course, String Body, String QNumber) {
		ArrayList<String> list = new ArrayList<String>();

		// Construct the INSERT query to create a new question
		list.addAll(Arrays.asList("createquestion",
				"INSERT INTO `projecton`.`questions` (`id`, `lecturer`, `subject`, `coursename`, `questiontext`, `questionnumber`) VALUES ('"+ Id + "','" + user.getUsername() + "', '" + subject + "', '" + course + "', '" + Body + "', '" + QNumber + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates query for insering answers to database.
	 * 
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
		// Construct the INSERT query to create a new answer
		String query = "INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
				+ optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer + "', '"
				+ subjectID + "');";
		list.add("createanswers");
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for sending the test to the data base.
	 * 
	 * @param query
	 */
	public void sendTestToDatabase(Object query) {
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("Addtesttodata");
		listToSend.add((String) query);
		try {
			this.openConnection();
			sendToServer((Object) listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new arraylist subject, add an identifier "getSubjectID" so the
	 * Echoserver idenrtifies it,
	 * the second cell should contain 'subjectname' for the server to parse
	 * 
	 * @param subjectname the subject to whom the search is for.
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

	public void GetCourseIDfromSubjectCourses(Object coursename) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("getCourseID",
				"SELECT courseid FROM projecton.subjectcourses where ( `coursename` = '" + (String) coursename
						+ "' );"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// test id format: 01 02 03 subjectANDcourse format: 01 02 returned value should
	// be: 01 02 03
	public void getNextFreeTestNumber(Object subjectANDcourse) {
		ArrayList<String> list = new ArrayList<String>();
		// String subject = (String)subjectANDcourse.substring(0,2);

		list.addAll(Arrays.asList("testNumber",
				"SELECT MAX(CAST(SUBSTRING(id, 5, 2) AS UNSIGNED)) AS max_test_number FROM tests WHERE SUBSTRING(id, 1, 4) = '"
						+ (String) subjectANDcourse + "';"));
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleFileDownload(FileDownloadMessage downloadMessage) {
		byte[] fileContent = downloadMessage.getFileContent();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try (FileOutputStream fos = new FileOutputStream(file)) {
				if (fileContent != null) {
					fos.write(fileContent);
					fos.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

	// clear client data
	public static void resetClientData() {
		user = new User();
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

	public void isStudentTakingCourse(ArrayList<String> sendtoServer) throws IOException {
		sendtoServer.add(
				"SELECT code FROM student s JOIN subjectcourses sc ON FIND_IN_SET(sc.coursename, s.courses) > 0 JOIN tests t ON SUBSTRING(t.id, 3, 2) = sc.courseid WHERE s.username = '"
						+ user.getUsername() + "' AND SUBSTRING(t.id, 1, 2) = sc.subjectid;");
		sendToServer(sendtoServer);
	}

    public void DeleteQuestion(String originalId) {
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("DeleteQuestion");
		listToSend.add("DELETE FROM `projecton`.`questions` WHERE (`id` = '" + originalId + "');");
		try {
			sendToServer((Object) listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}