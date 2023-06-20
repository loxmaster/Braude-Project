package serverHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.ClientModel;
import logic.FileDownloadMessage;
import logic.FileUploadMessage;
import logic.Question;
import logic.TestInServer;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverUI.ServerUI;

@SuppressWarnings("unchecked")

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 */
public class EchoServer extends AbstractServer {

	// Class variables *************************************************

	// Variables for handling the clients timout disconnect
	// private Map<ConnectionToClient, Long> lastMessageTimes;
	// private Timer timer;
	// private long timeoutDuration = 60000; // Timeout for disconnect in
	// Miliseconds

	// The default port to listen on.
	final public static int DEFAULT_PORT = 5555;

	// Errors Strings
	private String userNotFound = "User Not Found";
	private String idExists = "Id Exists";
	private String notFound = "Not Found";
	private String rowsAffected = "rows Affected";

	// Instance of the server
	private static EchoServer serverInstance;

	// Variables for the queries
	private static Connection conn = null;
	private Statement stmt;

	/////////////////////////////////////////////////////////////////
	////////////////// SERVER CONFIGURATION METHODS ////////////////
	///////////////////////////////////////////////////////////////

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	private EchoServer(int port) {
		super(port);

		// lastMessageTimes = new HashMap<>();
		// timer = new Timer();
	}

	public static synchronized EchoServer getServerInstance() {
		if (serverInstance == null)
			serverInstance = new EchoServer(DEFAULT_PORT);
		return serverInstance;
	}

	////////////// MAIN//////////////
	/**
	 * Responsible for the creation of the server instance
	 * 
	 * @param args[0] port to listen on. Default: 5555 if not given argument
	 */
	public static void main(String[] args) {

		serverInstance = getServerInstance();

		try {
			serverInstance.listen(); // Start listening for connections
		} catch (Exception e) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

	/**
	 * This method overrites the method from AbstractServer where this method is
	 * called when a client has disconnected. Removes the client from client list
	 * table in UI.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		// lastMessageTimes.put(client, System.currentTimeMillis());
		int rows_affected;
		String rowsAffectedString;

		System.out.println("Message received: " + msg + " from " + client);

		try {

			if (msg instanceof FileUploadMessage) {
				FileUploadMessage uploadMessage = (FileUploadMessage) msg;
				// Save the file in the database (e.g., using JDBC)
				if (saveFileToDatabase(uploadMessage.getFileId(), uploadMessage.getFileContent(),
						uploadMessage.getFilename()))
					client.sendToClient("File Uploaded Successfully!");
				else
					client.sendToClient("Error! File Upload Failed!");
			} else if (msg instanceof FileDownloadMessage) {
				FileDownloadMessage downloadMessage = (FileDownloadMessage) msg;
				FileDownloadMessage fileContent = getFileFromDatabase(downloadMessage.getFileId());
				// if(fileContent.getFileId()==null)
				// client.sendToClient("File not Found!");
				// else{
				downloadMessage.setFileContent(fileContent.getFileContent());
				downloadMessage.setFilename(fileContent.getFilename());
				client.sendToClient(downloadMessage);
				// }
			}

			switch (msg.getClass().getSimpleName()) {

				// if gets InetAddress then it means hes finished
				case "Inet4Address":
					clientDisconnected(client);
					break;
				case "ArrayList":

					ArrayList<Object> listSent = (ArrayList<Object>)msg;
					// Checks if the list is of Objects (If the student uploaded a test)
					if ((listSent.get(0)).equals("studentupload")) {
						FileUploadMessage uploadMessage = (FileUploadMessage) (listSent.get(1));
						if (saveFileToStudentUpload(uploadMessage.getFileId(), uploadMessage.getFileContent(), uploadMessage.getFilename(), (String)(listSent.get(2))))
							client.sendToClient("File Uploaded Successfully!");
						else
							client.sendToClient("Error! File Upload Failed!");
					}
					else {
						ArrayList<String> list = (ArrayList<String>) msg;
						switch (list.get(0)) {
							case "getSubjectID":
								// send query to be executed along with the identifier
								ArrayList<String> resultList = getData_db(list.get(1), "getSubjectID");
								// result list should have arraylist = {identifier, subjectId}
								// if we got no results: send notFound signal
								if (resultList == null)
									client.sendToClient((Object) notFound);
								// else return the subjectID result we got from the query
								else
									client.sendToClient(resultList);
								break;

							case "getCourseID":
								// send query to be executed along with the identifier
								ArrayList<String> resultCourseID = getData_db(list.get(1), "getCourseID");
								// result list should have arraylist = {identifier, subjectId}
								// if we got no results: send notFound signal
								if (resultCourseID == null)
									client.sendToClient((Object) notFound);
								// else return the subjectID result we got from the query
								else
									client.sendToClient(resultCourseID);
								break;

							case "createquestion":
							case "createanswers":
							case "editquestion":
							case "Addtesttodata":
							case "DeleteQuestion":
							case "testEval":
								// executeMyQuery will execute a basic UPDATE query
								int flag = executeMyQuery(list.get(1));
								// if (flag != 0) flag=1;
								client.sendToClient(flag == 0 ? idExists : flag);
								break;

							case "isStudentTakingCourse": // list size od 2 {tag, query}
								ArrayList<String> testcode = getTestCodeFromUsername(list.get(1));
								if (testcode == null)
									list.remove(1);
								client.sendToClient(testcode == null ? list : testcode);
								break;

							case "isTestReady": // list size of 3 {tag, test_id, query}
								ArrayList<String> test_id_ognoing = getOnGoingTest(list.get(2));
								if (test_id_ognoing == null) {
									list.remove(1);
									list.remove(2);
								}
								client.sendToClient(test_id_ognoing == null ? list : test_id_ognoing);
								break;

							case "getTest":// list size of 3 {tag, test_id, query}
								ArrayList<Object> TestQuestionList = getQuestionsFromTest(list.get(2));
								if (TestQuestionList == null) {
									list.remove(1);
									list.remove(1);
								} else
									TestQuestionList.add(0, "getTest");
								client.sendToClient(
										TestQuestionList == null ? (Object) "getTest" : (Object) TestQuestionList);
								break;
							case "testNumber":
								ArrayList<String> restestList = getData_db(list.get(1), "testNumber");
								// ArrayList<String> list2 = new ArrayList<>();
								// list2.add("testNumber");
								if (restestList == null)
									list.remove(1);
								client.sendToClient(restestList == null ? (Object) list : (Object) restestList);
								break;

							case "lecturersubjects":
								ArrayList<String> resSubjectsList = getData_db(list.get(1), "lecturersubjects");
								client.sendToClient(resSubjectsList == null ? (Object) notFound : (Object) resSubjectsList);
								break;

							case "lecturercourses":
								ArrayList<String> resCoursesList = getCourses_db(list.get(1), "lecturercourses");
								client.sendToClient(resCoursesList == null ? (Object) notFound : (Object) resCoursesList);
								break;

							case "completedTestsForStudent":
								ArrayList<String> resCompletedTestsForStudent = getCompletedTestsForStudent_db(list.get(1),
										"completedTestsForStudent");
								client.sendToClient(
										resCompletedTestsForStudent == null ? (Object) notFound
												: (Object) resCompletedTestsForStudent);
								break;

							case "completedTestsForLecturer":
								ArrayList<String> resCompletedTestsForLecturer = getCompletedTestsForLecturer_db(
										list.get(1),
										"completedTestsForLecturer");
								client.sendToClient(
										resCompletedTestsForLecturer == null ? (Object) notFound
												: (Object) resCompletedTestsForLecturer);
								break;

							case "getSubjectsCourseForTestLec":
								ArrayList<String> resSubjectsCoursesListLec = getSubjectsCoursesListLec(list.get(1),
										"getSubjectsCourseForTestLec");
								client.sendToClient(resSubjectsCoursesListLec == null ? (Object) notFound
										: (Object) resSubjectsCoursesListLec);
								break;

							case "getSubjectsCourseForTest":
								ArrayList<String> resSubjectsCoursesList = getSubjectsCoursesList(list.get(1),
										"getSubjectsCourseForTest");
								client.sendToClient(resSubjectsCoursesList == null ? (Object) notFound
										: (Object) resSubjectsCoursesList);
								break;

							case "LecturerListUnderSameDepartment":
								ArrayList<String> resLecturerListUnderSameDepartment = getLecturerListUnderSameDepartment(
										list.get(1),
										"LecturerListUnderSameDepartment");
								client.sendToClient(resLecturerListUnderSameDepartment == null ? (Object) notFound
										: (Object) resLecturerListUnderSameDepartment);
								break;

							case "studentListUnderSameDepartment":
								ArrayList<String> resstudentListUnderSameDepartment = getStudentListUnderSameDepartment(
										list.get(1),
										"studentListUnderSameDepartment");
								client.sendToClient(resstudentListUnderSameDepartment == null ? (Object) notFound
										: (Object) resstudentListUnderSameDepartment);
								break;

							case "HodGETcompletedTestsForSpecificLecturerList":
								ArrayList<String> resHodGETcompletedTestsForSpecificLecturerList = getHodGETcompletedTestsForSpecificLecturerList_db(
										list.get(1),
										"HodGETcompletedTestsForSpecificLecturerList");
								client.sendToClient(
										resHodGETcompletedTestsForSpecificLecturerList == null ? (Object) notFound
												: (Object) resHodGETcompletedTestsForSpecificLecturerList);
								break;

							case "HodGETcompletedTestsForSpecificStudentList":
								ArrayList<String> resHodGETcompletedTestsForSpecificStudentList = getHodGETcompletedTestsForSpecificStudentList_db(
										list.get(1),
										"HodGETcompletedTestsForSpecificStudentList");
								client.sendToClient(
										resHodGETcompletedTestsForSpecificStudentList == null ? (Object) notFound
												: (Object) resHodGETcompletedTestsForSpecificStudentList);
								break;

							case "getHodSubjectsCourseForTestSpecificLec":
								ArrayList<String> resHodSubjectsCourseForTestSpecificLec = getHodSubjectsCourseForTestSpecificLec_db(
										list.get(1),
										"getHodSubjectsCourseForTestSpecificLec");
								client.sendToClient(resHodSubjectsCourseForTestSpecificLec == null ? (Object) notFound
										: (Object) resHodSubjectsCourseForTestSpecificLec);
								break;

							case "getHodCourseForTestSpecificStudent":
								ArrayList<String> resHodCourseForTestSpecificStudent = getHodSubjectsCourseForTestSpecificStudent_db(
										list.get(1),
										"getHodCourseForTestSpecificStudent");
								client.sendToClient(resHodCourseForTestSpecificStudent == null ? (Object) notFound
										: (Object) resHodCourseForTestSpecificStudent);
								break;

							case "lecturerquestions":
								ArrayList<Question> resQuestionList = getQuestionsFromDatabase(list.get(1));
								client.sendToClient(resQuestionList == null ? (Object) notFound : (Object) resQuestionList);
								break;

							case "testGrades":
								ArrayList<String> resGradesList = TestGrades_PassedGrades(list.get(1), 1);
								client.sendToClient(
										resGradesList == null ? (Object) notFound : (Object) resGradesList);
								System.out.println("Server: TestGrades_PassedGrades --> " + resGradesList.toArray());
								break;

							// default is user login authentication
							case "gettestwithcode":
							case "check test":
								TestInServer test = getTestWithCode(list.get(1));
								client.sendToClient(
										test == null ? (Object) notFound : (Object) test);
								System.out.println("Server gettestwithcode --> " + test.getId());
								break;

							case "sendtocompletedtest":
								int returned = executeMyQuery(list.get(1));
								client.sendToClient(
										returned == 0 ? (Object) notFound : (Object) returned);
								break;
							case "getCoursesSameDepartment":
								ArrayList<String> resgetCoursesSameDepartment = getCoursesSameDepartment_db(
										list.get(1),
										"getCoursesSameDepartment");
								client.sendToClient(resgetCoursesSameDepartment == null ? (Object) notFound
										: (Object) resgetCoursesSameDepartment);
								break;

							case "getSelectedAnswers":
								ArrayList<String> selectedAnswers = getSelectedAnswers_db(list);
								client.sendToClient(selectedAnswers == null ? (Object) notFound : (Object) selectedAnswers);
								System.out.println("Server: sending back selected answers:" + selectedAnswers);
								break;
							case "getCoursesExams":
								ArrayList<String> resgetCoursesExams = getCoursesExams_db(list.get(1),
										"getCoursesExams");
								client.sendToClient(
										resgetCoursesExams == null ? (Object) notFound
												: (Object) resgetCoursesExams);
								break;
							case "Update_timeExtensionRequestsTable":
								rows_affected = executeMyQuery(list.get(1));
								rowsAffectedString = rowsAffected + " " + Integer.toString(rows_affected);
								System.out.println(rowsAffectedString);
								client.sendToClient(rowsAffected);
								break;

							case "updateLockButton_DB":
								rows_affected = executeMyQuery(list.get(1));
								rowsAffectedString = rowsAffected + " " + Integer.toString(rows_affected);
								System.out.println(rowsAffectedString);
								client.sendToClient(rows_affected);
								break;

							case "fetchOngoingTests":
								try {
									ArrayList<TestInServer> ongoingTests = fetchOngoingTestsFromDB();
									client.sendToClient((Object) ongoingTests);
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;

							case "fetch_ongoingTests_permissions_FromDB":
								try {
									ArrayList<TestInServer> ongoingTestsPermissions = fetch_ongoingTests_permissions_FromDB(
											list.get(1));
									client.sendToClient((Object) ongoingTestsPermissions);
									// client.sendToClient(ongoingTestsPermissions == null ? (Object) notFound :
									// (Object) ongoingTestsPermissions);
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;

							case "updateHODPermissionsTable":
								rows_affected = executeMyQuery(list.get(1));
								rowsAffectedString = rowsAffected + " " + Integer.toString(rows_affected);
								System.out.println(rowsAffectedString);
								client.sendToClient(rows_affected);
								break;

							default:
								loginVarification(list, client);
								break;
						}
					}
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private ArrayList<String> getSelectedAnswers_db(ArrayList<String> list) {
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(list.get(1));
			list.remove(1);// remove the query from list
			while (result.next()) {
				list.add(result.getString(1));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<TestInServer> fetch_ongoingTests_permissions_FromDB(String query) {
		ArrayList<TestInServer> tests = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				TestInServer test = new TestInServer();
				test.setId(resultSet.getString("id"));
				test.setSubject(resultSet.getString("Subject")); // Added this line
				test.setTimeToAdd(resultSet.getString("TimeToAdd"));
				test.setReasonForTimeExtension(resultSet.getString("Reason"));
				tests.add(test);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tests;
	}

	private ArrayList<TestInServer> fetchOngoingTestsFromDB() {
		ArrayList<TestInServer> tests = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();

			ResultSet resultSet = statement
					.executeQuery("SELECT tests.*, subjectcourses.subjectname, ongoing_tests.locked "
							+ "FROM tests "
							+ "LEFT JOIN subjectcourses ON CAST(tests.id AS UNSIGNED) = CAST(subjectcourses.subjectid AS UNSIGNED) "
							+ "LEFT JOIN ongoing_tests ON tests.id = ongoing_tests.test_id "
							+ "WHERE STR_TO_DATE(CONCAT(tests.date, ' ', tests.time), '%Y-%m-%d %H:%i') <= NOW() AND "
							+ "TIMESTAMPADD(MINUTE, TIME_TO_SEC(TIMEDIFF(tests.duration, '00:00'))/60, STR_TO_DATE(CONCAT(tests.date, ' ', tests.time), '%Y-%m-%d %H:%i')) >= NOW()"
							+ "GROUP BY tests.id");

			while (resultSet.next()) {
				TestInServer test = new TestInServer();
				test.setId(resultSet.getString("id"));
				test.setSubject(resultSet.getString("subjectname")); // Added this line
				test.setDuration(resultSet.getString("duration"));
				test.setTestComments(resultSet.getString("testcomments"));
				test.setAuthor(resultSet.getString("authorsname"));
				test.setTestCode(resultSet.getString("code"));
				test.setDateString(resultSet.getString("date"));
				test.setTime(resultSet.getString("time"));
				String lockBTN = resultSet.getString("locked");

				if (lockBTN.equals("FALSE"))
					test.setLockBtnPressed(false);
				else
					test.setLockBtnPressed(true);
				tests.add(test);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tests;
	}

	private ArrayList<Object> getQuestionsFromTest(String query) {
		ArrayList<Object> output = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				output.add((Object) result.getObject(1));
			}
			return output;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getOnGoingTest(String query) {
		ArrayList<String> output = new ArrayList<>();
		output.add("isTestReady");
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				output.add((String) result.getString(1));
				return output;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getTestCodeFromUsername(String query) {
		ArrayList<String> output = new ArrayList<>();
		output.add("isStudentTakingCourse");
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				output.add((String) result.getString("code"));
				output.add((String) result.getString("id"));
				return output;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FileDownloadMessage getFileFromDatabase(String fileId) {
		FileDownloadMessage fileContent = new FileDownloadMessage(fileId);
		fileContent.setFileContent(null);
		fileContent.setFilename(null);
		{
			String sql = "SELECT file_data,file_name FROM uploaded_tests WHERE test_id = ?";
			try (PreparedStatement statement = conn.prepareStatement(sql)) {
				statement.setString(1, fileId);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						fileContent.setFileContent(resultSet.getBytes("file_data"));
						fileContent.setFilename(resultSet.getString("file_name"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return fileContent;
		}
	}

	private boolean saveFileToDatabase(String fileId, byte[] fileContent, String filename) {

		String sql = "INSERT INTO uploaded_tests (test_id, file_data, file_name) VALUES (?, ?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, fileId);
			statement.setBytes(2, fileContent);
			statement.setString(3, filename);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveFileToStudentUpload( String fileId, byte[] fileContent, String filename , String studentId) {
		String sql = "INSERT INTO student_upload (test_id, student_id, file_data, file_name) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setString(1, fileId);
			statement.setString(2, studentId);
			statement.setBytes(3, fileContent);
			statement.setString(4, filename);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private TestInServer getTestWithCode(String query) {
		try {

			Statement testStatement = conn.createStatement();
			ResultSet res = testStatement.executeQuery(query);

			Statement questionStmt = null;
			ResultSet questionResult = null;

			if (res.next()) {

				// Gets the lists of question ID`s and creates a Question for them ,
				// also gets the question points array and assigns each quesiton its points.
				ArrayList<Question> listOfQuestions = new ArrayList<>();

				// Handles the questions ID`s array
				String listOfIdsFromDatabase = res.getString("questions");
				String listOfIdsTrimmed = listOfIdsFromDatabase.replace("[", "").replace("]", "").trim();
				String[] arrayIds = listOfIdsTrimmed.split(",");

				// handles the question points array
				String questionsPoints = res.getString("points");
				String listOfIdsTrimmedPoints = questionsPoints.replace("[", "").replace("]", "").trim();
				String[] arrayPoints = listOfIdsTrimmedPoints.split(",");

				// index for the current quesiton number
				int index = 0;
				for (String id : arrayIds) {
					String queryForGettingTheQuestions = "SELECT * FROM questions WHERE id = " + id + "";
					questionStmt = conn.createStatement();
					System.out.println(id);
					questionResult = questionStmt.executeQuery(queryForGettingTheQuestions);

					if (questionResult.next()) {
						Question q = new Question(
								questionResult.getString(1),
								questionResult.getString(2),
								questionResult.getString(3),
								questionResult.getString(4),
								questionResult.getString(5),
								questionResult.getString(6));

						String answerQuery = "SELECT optionA, optionB, optionC, optionD, correctAnswer FROM answers WHERE questionid = "
								+ q.getId() + "";
						Statement answerStmt = conn.createStatement();
						ResultSet answerResult = answerStmt.executeQuery(answerQuery);
						if (answerResult.next()) {
							System.out.println(answerResult.getString(1));
							q.setOptionA(answerResult.getString(1));
							q.setOptionB(answerResult.getString(2));
							q.setOptionC(answerResult.getString(3));
							q.setOptionD(answerResult.getString(4));
							q.setAnswer(answerResult.getString(5));
						}

						// Sets the question points
						q.setPoints(arrayPoints[index]);
						index++;
						listOfQuestions.add(q);
					}

				}

				TestInServer tempTest = new TestInServer(
						res.getString(1),
						"MATH", // FIXME: Add subject to SQL
						res.getString(4),
						res.getString(2),
						res.getString(3),
						res.getString(5),
						res.getString(6),
						res.getString(7),
						listOfQuestions);

				res.close();
				stmt.close();

				return tempTest;
			} else {
				res.close();
				stmt.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// User Login Varification
	private void loginVarification(ArrayList<String> list, ConnectionToClient client) {
		try {

			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(list.get(0));
			if (result.next()) {
				// check if the password in the DB is the same as user input
				String res = result.getString(1) + " " + result.getString(2) + " " + result.getString(3)
						+ " " + result.getString(4) + " " + result.getString(5) + " " + result.getString(6);
				System.out.println("Message sent back: " + res);
				client.sendToClient((Object) res);

			} else {
				// The user is not in the database (not registered)
				// or incorrect password
				System.out.println("user has not been found!");
				client.sendToClient((Object) userNotFound);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Method for executing Update queries .
	 * 
	 * @param query the query to execute.
	 * @return returns how many rows affected , 0 upon failure.
	 */
	private int executeMyQuery(String query) {
		try {
			stmt = conn.createStatement();
			int res = stmt.executeUpdate(query);
			return res;
		} catch (SQLException e) {
			// if(e=="java.sql.SQLIntegrityConstraintViolationException:")
			// return -1 ;
			e.printStackTrace();
			return 0;
		}
	}

	// gets Questions from db
	private ArrayList<Question> getQuestionsFromDatabase(String query) {
		try {
			Statement tempStatement = conn.createStatement();
			ResultSet result = tempStatement.executeQuery(query);
			ArrayList<Question> res = new ArrayList<Question>();

			while (result.next()) {
				// while threres Questions in result , adding them into result array
				Question q = new Question(
						result.getString(1),
						result.getString(2),
						result.getString(3),
						result.getString(4),
						result.getString(5),
						result.getString(6));

				Statement answerstmt = conn.createStatement();
				ResultSet answers = answerstmt.executeQuery(
						"SELECT optionA,optionB,optionC,optionD,correctAnswer FROM `answers` WHERE (`questionid` = '"
								+ q.getId() + "');");
				if (answers.next()) {
					q.setOptionA(answers.getString(1));
					q.setOptionB(answers.getString(2));
					q.setOptionC(answers.getString(3));
					q.setOptionD(answers.getString(4));
					q.setAnswer(answers.getString(5));
				}
				answers.close();
				res.add(q);
			}
			System.out.println("Message sent back: " + res);

			if (res.size() == 0) {
				return null;
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to get data from database.
	 * 
	 * @param query
	 * @param out   the out String , first in the list.
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<String> getData_db(String query, String out) {
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			ArrayList<String> res = new ArrayList<String>();
			res.add(out);
			if (result.next()) {
				res.add(result.getString(1));
				System.out.println("Message sent back: " + res);
				return res;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getCourses_db(String query, String out) {
		Boolean flag = false;
		ArrayList<String> res = new ArrayList<String>();

		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			res.add(out);
			while (result.next()) {
				res.add(result.getString(1));
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Message sent back: " + res);
		return flag ? res : null;

	}

	private ArrayList<String> getCompletedTestsForStudent_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 1; index <= 12; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 13 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getCoursesExams_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 1; index <= 12; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 13 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getCompletedTestsForLecturer_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 1; index <= 12; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 13 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getSubjectsCoursesList(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 4; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getSubjectsCoursesListLec(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 4; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getLecturerListUnderSameDepartment(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 6; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getStudentListUnderSameDepartment(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 6; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getHodGETcompletedTestsForSpecificLecturerList_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 1; index <= 12; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 13 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getHodGETcompletedTestsForSpecificStudentList_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 1; index <= 12; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 13 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getHodSubjectsCourseForTestSpecificLec_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 4; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getHodSubjectsCourseForTestSpecificStudent_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 4; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> getCoursesSameDepartment_db(String query, String out) {
		ArrayList<String> res = new ArrayList<String>();
		res.add(out); // add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) { // This will move the cursor to the next row
				for (int index = 1; index <= 4; index++) {
					res.add(result.getString(index));
				}
			}
			return res; // res size is 5 where in first index is indentifier
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to start the server connection with MySQL WorkBench.
	 * 
	 * @param DBname
	 * @param username
	 * @param Password
	 */
	public static void startServer(String DBname, String username, String Password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			System.out.println("Driver definition succeed");

		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DBname + "?serverTimezone=IST", username,
					Password);
			System.out.println("SQL connection succeed");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success!");
			alert.setHeaderText(null);
			alert.setContentText("Server Started!");
			alert.showAndWait();
		} catch (SQLException ex) {// handle any errors
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Wrong Credentials!");
			alert.showAndWait();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrites the method from AbstractServer where this method is
	 * called when a client has connected. Adds the client to client list table in
	 * UI.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		// lastMessageTimes.put(client, System.currentTimeMillis());

		ClientModel clientModel = new ClientModel(client.getInetAddress().getHostName(), client.getInetAddress(),
				client.isAlive());
		ServerUI.sController.loadToTable(clientModel);

		// Start the timer task to check for inactivity
		// startTimerTask();
	}

	/**
	 * This method overrites the method from AbstractServer where this method is
	 * called when a client has disconnected. Removes the client from client list
	 * table in UI.
	 */
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		ServerUI.sController.RemoveFromTable(client);
	}

	/*
	 * private void startTimerTask() {
	 * timer.schedule(new TimerTask() {
	 * 
	 * @Override
	 * public void run() {
	 * checkClientActivity();
	 * }
	 * }, timeoutDuration, timeoutDuration);
	 * }
	 * 
	 * private void checkClientActivity() {
	 * long currentTime = System.currentTimeMillis();
	 * 
	 * for (ConnectionToClient client : lastMessageTimes.keySet()) {
	 * 
	 * long lastMessageTime = lastMessageTimes.get(client);
	 * if (currentTime - lastMessageTime > timeoutDuration) {
	 * // Client has exceeded the timeout duration, consider it disconnected
	 * clientDisconnected(client);
	 * }
	 * }
	 * }
	 */

	// TODO see if piechart is needed
	// query_passed: select passed students
	// grade_index - position of the grade field
	private ArrayList<String> TestGrades_PassedGrades(String query_passed, int grade_index) throws SQLException {
		ArrayList<String> outputList = new ArrayList<String>();
		stmt = conn.createStatement();
		// ArrayList<ResultSet> res = new ArrayList<>();
		ResultSet queryResult = stmt.executeQuery(query_passed);

		// outputList.addAll((String)queryResult.getString(query_passed));
		while (queryResult.next()) {
			outputList.add(queryResult.getString(grade_index));
		}
		return outputList;
	}

}
