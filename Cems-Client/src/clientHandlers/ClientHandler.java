package clientHandlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import clientControllers.CheckTestController;
import clientControllers.CreateQuestionController;
import clientControllers.CreateTestController;
import clientControllers.DBTestController;
import clientControllers.EvaluateTestController;
import clientControllers.HODController;
import clientControllers.HODStatisticOnCourseController;
import clientControllers.HODStatisticOnLecturerController;
import clientControllers.HODStatisticOnStudentController;
import clientControllers.HODViewGradesController;
import clientControllers.IdAndCodeScreen;
import clientControllers.LecturerController;
import clientControllers.LecturerStatisticalController;
import clientControllers.StudentExamController;
import clientControllers.ViewGradesController;
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
		Test testToAdd;
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

				testToAdd = new Test(testFromServer.getId(), testFromServer.getSubject(),
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

				if (((ArrayList<?>) serverMessage).get(0) instanceof TestInServer) {
					ArrayList<TestInServer> testsFromServer = (ArrayList<TestInServer>) serverMessage;
					ArrayList<Test> testsToReturn = new ArrayList<>();
					if (testsFromServer.get(0).getTimeToAdd() != null) { // if the test have timeToAdd, we know that its
																			// test from the time extension permission
																			// tests

						for (TestInServer test : testsFromServer) {
							testToAdd = new Test(test.getId(), test.getSubject(), test.getAuthor(), test.getDuration(),
									test.getTestComments(), test.getTestCode(), test.getDateString(), test.getTime(),
									null);
							testToAdd.setTimeToAdd(test.getTimeToAdd());
							testToAdd.setReasonForTimeExtension(test.getReasonForTimeExtension());
							testToAdd.setSubject(test.getSubject());

							testsToReturn.add(testToAdd);
						}
						HODController.setOngoingTests_permissions(testsToReturn);
						calculateTest_timeLeft(testsToReturn);// send the tests to calculate and update the timeLeft
					}
					for (TestInServer test : testsFromServer) {
						testToAdd = new Test(test.getId(), test.getSubject(),
								test.getAuthor(), test.getDuration(),
								test.getTestComments(), test.getTestCode(), test.getDateString(),
								test.getTime(),
								null);
						testsToReturn.add(testToAdd);
					}

					calculateTest_timeLeft(testsToReturn);// send the tests to calculate and update the timeLeft
					LecturerController.setOngoingTests(testsToReturn);

				} else {
					list = (ArrayList<String>) serverMessage;
					switch (list.get(0)) {

						case "lecturersubjects":
							subjectArray = list.get(1).split(",");
							for (String s : subjectArray)
								LecturerController.getSubjectsList().add(s);
							break;

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

	///////////////////////////////////////////////////
	////////////////// LOGIC METHODS /////////////////
	/////////////////////////////////////////////////


	/**
	 * calculate the test left time for the ongoing tests 
	 */
	private void calculateTest_timeLeft(ArrayList<Test> tests) {
		for(Test test : tests) {
           LocalTime testTime = LocalTime.parse(test.getTime());
           LocalDate testDate = LocalDate.parse(test.getDateString());
           ZonedDateTime now = ZonedDateTime.now();
           ZonedDateTime testZonedDateTime = ZonedDateTime.of(testDate, testTime, now.getZone());
		}
	}
	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * questions for the lecturer.
	 * 
	 * @param username lecturers username
	 */
	public void passToServer(Object listToSend) {
		try {
			sendToServer(listToSend);
		} catch (IOException e) {
			e.printStackTrace();
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
		} catch (IOException e) {}
	}

	/**
	 * 
	 * Resets the client data.
	 */
	public static void resetClientData() {
		// Reset the user object
		user = new User();
		// Reset the subjectsList and questions lists in the LecturerController
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

}