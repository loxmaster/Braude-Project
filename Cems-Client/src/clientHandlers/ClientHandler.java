package clientHandlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import clientControllers.CheckTestController;
import clientControllers.CreateQuestionController;
import clientControllers.CreateTestController;
import clientControllers.DBTestController;
import clientControllers.HODStatisticOnCourseController;
import clientControllers.EvaluateTestController;
import clientControllers.HODStatisticOnCourseController;
import clientControllers.HODStatisticOnLecturerController;
import clientControllers.HODStatisticOnStudentController;
import clientControllers.HODViewGradesController;
import clientControllers.IdAndCodeScreen;
import clientControllers.LecturerController;
import clientControllers.LecturerStatisticalController;
import clientControllers.StudentExamController;
import clientControllers.ViewGradesController;
import javafx.stage.FileChooser;
import logic.FileDownloadMessage;
import logic.Question;
import logic.QuestionModel;
import logic.Test;
import logic.TestInServer;
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
			IdAndCodeScreen.setDownloadMessage(downloadMessage);
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

			// If typeof Test then we create a test for it and
			case "TestInServer":

				// Creates a list of QuestionModels and adds it to the testToAdd constructor.
				TestInServer testFromServer = (TestInServer) serverMessage;
				ArrayList<QuestionModel> listOfQuestionModels = new ArrayList<>();
				for (Question question : testFromServer.getQuesitonsInTest()) {
					QuestionModel questionModel = new QuestionModel(question.getId(),
							question.getSubject(),
							question.getCoursename(),
							question.getQuestiontext(),
							question.getQuestionnumber(),
							question.getLecturer(),
							question.getOptionA(),
							question.getOptionB(),
							question.getOptionC(),
							question.getOptionD(),
							question.getAnswer());
					questionModel.setPoints(question.getPoints());
					listOfQuestionModels.add(questionModel);
				}

				Test testToAdd = new Test(testFromServer.getId(), testFromServer.getSubject(),
						testFromServer.getAuthor(), testFromServer.getDuration(),
						testFromServer.getTestComments(), testFromServer.getTestCode(), testFromServer.getDateString(),
						testFromServer.getTime(),
						listOfQuestionModels);

				if (user.getType().equals("lecturer"))
					EvaluateTestController.setLocaltest(testToAdd);
				else
					StudentExamController.setTest(testToAdd);
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

						// case "completedTestsForLecturer":
						// ArrayList<Test> tests_temp = new ArrayList<Test>();
						// for (int i = 1; i < ((ArrayList<String>) serverMessage).size(); i+=12) {
						// tests_temp.add(new Test(list.get(i), list.get(i + 1), list.get(i + 2),
						// list.get(i + 3),
						// list.get(i + 4), list.get(i + 5), list.get(i + 6), list.get(i + 7),
						// list.get(i + 8), list.get(i + 9), list.get(i + 10), list.get(i + 11)));
						// }
						// LecturerStatisticalController.setCompletedTestsList(tests_temp);
						// break;

						case "getTest":
							if (!(list.get(1).isEmpty())) {
								list.remove(0);
								StudentExamController.setQuestionList(list);
							}

							break;
						case "isStudentTakingCourse":
							if (!(list.get(1).isEmpty())) {
								list.remove(0);
								IdAndCodeScreen.setTest_code(list);
							} else {
								list.remove(0);
								list.remove(0);
								list.remove(0);
								list.add("none");
								list.add("none");
								IdAndCodeScreen.setTest_code(list);
							}
							break;

						// get list size 2 "testid" index 1
						case "isTestReady":
							if (!(list.get(1).isEmpty())) {
								list.remove(0);
								IdAndCodeScreen.setTestRunning(true);
							} else {
								IdAndCodeScreen.setTestRunning(false);
							}
							break;
						case "lecturercourses":
							// list.remove(0);
							// LecturerController.getCoursesList().addAll(list);
							// break;
							subjectArray = list.get(1).split(",");
							for (String s : subjectArray)
								LecturerController.getCoursesList().add(s);
							break;

						case "completedTestsForStudent": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							// CompletedTestList = (ArrayList<Test>) severMessage;
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}
							ViewGradesController.setcompletedTestsForStudentList(listToAdd);
							break;
						}
						case "completedTestsForLecturer": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							// ArrayList<TestInServer> listToAdd_TestServer = new ArrayList<>();
							// CompletedTestList = (ArrayList<Test>) severMessage;
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}

							LecturerStatisticalController.setcompletedTestsForLecturerList(listToAdd);
							CheckTestController.setCompletedTestsList(listToAdd);
							break;
						}

						case "getCoursesExams": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							// CompletedTestList = (ArrayList<Test>) severMessage;
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}
							HODStatisticOnCourseController.setCoursesExams(listToAdd);
							break;
						}
						case "getSubjectsCourseForTest": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							ViewGradesController.setSubjectsCoursesList(listToAdd);
							break;
						}
						case "getSubjectsCourseForTestLec": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							LecturerStatisticalController.setSubjectsCoursesListLec(listToAdd);
							break;
						}

						case "getCoursesSameDepartment": {

							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							HODStatisticOnCourseController.setCoursesSameDepartment(listToAdd);
							break;
						}

						case "getFutureTests": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							ArrayList<TestInServer> listToAdd_TestServer = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}
							DBTestController.setTestList(listToAdd);
							break;
						}

						case "LecturerListUnderSameDepartment": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							HODStatisticOnLecturerController.setLecturerListUnderSameDepartment(listToAdd);
							break;
						}

						case "studentListUnderSameDepartment": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							HODStatisticOnStudentController.setStudentListUnderSameDepartment(listToAdd);
							break;
						}

						case "HodGETcompletedTestsForSpecificLecturerList": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							// CompletedTestList = (ArrayList<Test>) severMessage;
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}
							HODStatisticOnLecturerController.setcompletedTestsForSpecificLecturer(listToAdd);
							break;
						}
						case "HodGETcompletedTestsForSpecificStudentList": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<Test> listToAdd = new ArrayList<>();
							// CompletedTestList = (ArrayList<Test>) severMessage;
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(new Test(
										list.get(i),
										list.get(i + 1),
										list.get(i + 2),
										list.get(i + 3),
										list.get(i + 4),
										list.get(i + 5),
										list.get(i + 6),
										list.get(i + 7),
										list.get(i + 8),
										list.get(i + 9),
										list.get(i + 10),
										list.get(i + 11)));
								i += 12;
							}
							HODStatisticOnStudentController.setcompletedTestsForSpecificStudent(listToAdd);
							break;
						}
						case "getHodSubjectsCourseForTestSpecificLec": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							HODStatisticOnLecturerController.getHodSubjectsCourseForTestSpecificLec(listToAdd);
							break;
						}

						case "getHodCourseForTestSpecificStudent": {
							System.out.println("Client Handler: " + list.get(0));
							ArrayList<String> listToAdd = new ArrayList<>();
							int i = 1;
							while (i < list.size()) {
								listToAdd.add(list.get(i));
								i++;
							}
							HODViewGradesController.setHodSubjectsCourseForTestSpecificStudent(listToAdd);
							break;
						}
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
						System.out.println("Question Already Exists.");
						break;
					case "Not Found":
						System.out.println("Item not found.");
						break;
					case "Id Exists":
						ClientUI.updatestatus = 0;
						System.out.println("ID Already Exists.");
						break;
					default:
						// Here we recieve the confirmation of the client login
						subjectArray = ((String) serverMessage).toString().split("\\s");
						user.setUsername(subjectArray[0]);
						user.setPassword(subjectArray[1]);
						user.setType(subjectArray[2]);
						user.setUser_id(subjectArray[3]);
						user.setEmail(subjectArray[4]);
						user.setDepartment(subjectArray[5]);
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
	 * NOTE: this method does not have a unique ideintifier
	 * EchoServer is configured that the default of the switch case goes to this
	 * method
	 * 
	 * @param username username entered.
	 * 
	 * @param password password entered.
	 */
	public void passToServer(Object listToSend) {
		try {
			sendToServer((Object) credentials);
		} catch (IOException e) {
			client.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects.//TODO please check what this method do and if it's relevant
	 */
	public void handleMessageFromLecturerUI() {
		try {
			sendToServer((Object) "SELECT DISTINCT subjectname FROM subjectcourses;");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			sendToServer((Object) credentials);
		} catch (IOException e) {
			client.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/*
	 * public void GetTestGrades_StatisticalInformation(String testID) {
	 * ArrayList<String> list = new ArrayList<String>();
	 * 
	 * @param newBody
	 * 
	 * @param newQNumber
	 * 
	 * @param originalId
	 */
	public void EditQuestion(String NewID, String subject, String course, String qBody, String qnumber,
			String originalId) {
		ArrayList<String> list = new ArrayList<String>();

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

	public void EditAnswers(String subjectid, String qA, String qB, String qC, String qD, String correctAnswer) {
		// Create an ArrayList to store the method name and query.
		ArrayList<String> list = new ArrayList<String>();

		// Construct the SQL query to update the answers of a question.
		String query = "UPDATE `projecton`.`answers` SET " +
				"`optionA` = '" + qA + "', " +
				"`optionB` = '" + qB + "', " +
				"`optionC` = '" + qC + "', " +
				"`optionD` = '" + qD + "', " +
				"`correctAnswer` = '" + correctAnswer + "' " +
				"WHERE (`questionid` = '" + subjectid + "');";

		// Add the method name and query to the ArrayList.
		list.addAll(Arrays.asList("editquestion", query));

		try {
			// Send the ArrayList to the server.
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
	public void CreateQuestion(String Id, String subject, String course, String Body, String QNumber) {
		ArrayList<String> list = new ArrayList<String>();
		// Construct the INSERT query to create a new question
		list.addAll(Arrays.asList("createquestion",
				"INSERT INTO `projecton`.`questions` (`id`, `lecturer`, `subject`, `coursename`, `questiontext`, `questionnumber`) VALUES ('"
						+ Id + "','" + user.getUsername() + "', '" + subject + "', '" + course + "', '" + Body + "', '"
						+ QNumber + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates query for insering answers to database.
	 * 
	 * try {
	 * sendToServer((Object) list);
	 * } catch (IOException e) {
	 * e.printStackTrace();
	 * }
	 * }
	 */

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
	}

	// Clear client data
	public static void resetClientData() {
		// Reset the user object
		user = new User();
		// Reset the subjectsList and questions lists in the LecturerController
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

	/**
	 * Checks if a student is taking a specific course.
	 * 
	 * @param sendToServer The ArrayList to send to the server.
	 * @throws IOException If an I/O error occurs while sending the data to the
	 *                     server.
	 */
	public void isStudentTakingCourse(ArrayList<String> sendToServer) throws IOException {
		// Construct the SQL query to check if a student is taking a specific course
		sendToServer.add(
				"SELECT code,id FROM student s JOIN subjectcourses sc ON FIND_IN_SET(sc.coursename, s.courses) > 0 JOIN tests t ON SUBSTRING(t.id, 3, 2) = sc.courseid WHERE s.username = '"
						+ user.getUsername() + "' AND SUBSTRING(t.id, 1, 2) = sc.subjectid;");
		sendToServer(sendToServer);
	}

	/**
	 * Checks if a test is ready to be taken.
	 * 
	 * @param sendToServer The ArrayList to send to the server.
	 * @throws IOException If an I/O error occurs while sending the data to the
	 *                     server.
	 */
	public void isTestReady(ArrayList<String> sendToServer) throws IOException {
		// Construct the SQL query to check if a test is ready to be taken
		sendToServer.add("SELECT test_id FROM ongoing_tests WHERE((SELECT id FROM tests WHERE (id = '"
				+ sendToServer.get(1) + "') AND id = test_id))");
		sendToServer(sendToServer);
	}

	/**
	 * Retrieves the questions of a test based on the test ID.
	 * 
	 * @param sendToServer The ArrayList to send to the server.
	 * @throws IOException If an I/O error occurs while sending the data to the
	 *                     server.
	 */
	public void getTestFromId(ArrayList<String> sendToServer) throws IOException {
		// Construct the SQL query to retrieve the questions of a test
		sendToServer.add("SELECT questions FROM projecton.tests WHERE (id = '" + sendToServer.get(1) + "')");
		sendToServer(sendToServer);
	}

	/**
	 * Deletes a question from the database based on the original question ID.
	 * 
	 * @param originalId The original question ID.
	 */
	public void DeleteQuestion(String originalId) {
		// Create an ArrayList to send to the server for deleting the question
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("DeleteQuestion");
		listToSend.add("DELETE FROM `projecton`.`questions` WHERE (`id` = '" + originalId + "');");
		try {
			// Send the listToSend object to the server
			sendToServer((Object) listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the courses within the same department as the current user.
	 */
	public void getCoursesSameDepartment() {
		// Create an ArrayList to store the method name and query.
		ArrayList<String> list = new ArrayList<String>();

		// Create a SQL query to select the subject courses within the same department
		// as the current user.
		String query = String.format("SELECT * FROM projecton.subjectcourses WHERE subjectname ='%s';",
				user.getDepartment());

		// Add the method name and query to the ArrayList.
		list.addAll(Arrays.asList("getCoursesSameDepartment", query));

		try {
			// Send the ArrayList to the server.
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the exams for a specific course.
	 * 
	 * @param courseID The ID of the course.
	 */
	public void getCoursesExams(String courseID) {
		// Create an ArrayList to store the method name and query.
		ArrayList<String> list = new ArrayList<String>();

		// Create a SQL query to select the completed exams for a specific course.
		String query = String.format(
				"SELECT * FROM projecton.completed_tests WHERE test_id LIKE '%s%%' AND status='completed' AND tested='true';",
				courseID);

		// Add the method name and query to the ArrayList.
		list.addAll(Arrays.asList("getCoursesExams", query));

		try {
			// Send the ArrayList to the server.
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}