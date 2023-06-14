package serverHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.ClientModel;
import logic.Question;
import logic.Test;
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

		System.out.println("Message received: " + msg + " from " + client);

		try {
			switch (msg.getClass().getSimpleName()) {

				// if gets InetAddress then it means hes finished
				case "Inet4Address":
					clientDisconnected(client);
					break;
				case "ArrayList":
					ArrayList<String> list = (ArrayList<String>) msg;
					switch (list.get(0)) {
						case "getSubjectID":
							// send query to be executed along with the identifier
							ArrayList<String> resultList = getDataFromDB(list.get(1), "getSubjectID");
							// result list should have arraylist = {identifier, subjectId}
							// if we got no results: send notFound signal
							if (resultList == null)
								client.sendToClient((Object) notFound);
							// else return the subjectID result we got from the query
							else
								client.sendToClient(resultList);
							break;

						case "createquestion":
						case "createanswers":
						case "editquestion":
						case "Addtesttodata":
							// executeMyQuery will execute a basic UPDATE query
							int flag = executeMyQuery(list.get(1));
							client.sendToClient(flag == 0 ? idExists : flag);
							break;

						case "lecturersubjects":
							ArrayList<String> resStringList = getDataFromDB(list.get(1), "lecturersubjects");
							client.sendToClient(resStringList == null ? (Object) notFound : (Object) resStringList);
							break;

						case "completedtestsForStudentGrades":
							ArrayList<String> resCompletedTests = getCompletedTests_db(list.get(1), "completedtestsForStudentGrades");
							client.sendToClient(
									resCompletedTests == null ? (Object) notFound : (Object) resCompletedTests);
							break;

						case "lecturerquestions":
							ArrayList<Question> resQuestionList = getQuestionsFromDBForLecturer(list.get(1));
							client.sendToClient(resQuestionList == null ? (Object) notFound : (Object) resQuestionList);
							break;

						case "testGrades":
							ArrayList<String> resList = TestGrades_PassedGrades(list.get(1), 1);
							client.sendToClient(resList == null ? (Object) notFound : (Object) resList);
							System.out.println("Server: TestGrades_PassedGrades --> " + resList.toArray());
							break;

						// default is user login
						default:
							// user login authentication
							loginVarification(list, client);
							break;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// User Login Varification
	private void loginVarification(ArrayList<String> list, ConnectionToClient client) {
		try {

			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(list.get(0));
			if (result.next()) {
				// check if the password in the DB is the same as user input
				if (result.getString(2).equals(list.get(2))) {
					String res = result.getString(1) + " " + result.getString(2) + " " + result.getString(3);
					System.out.println("Message sent back: " + res);
					client.sendToClient((Object) res);
				}
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
			e.printStackTrace();
			return 0;
		}
	}

	// gets Questions from db
	private ArrayList<Question> getQuestionsFromDBForLecturer(String query) {
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
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
						"SELECT optionA,optionB,optionC,optionD,correctAnswer FROM `projecton`.`answers` WHERE (`questionid` = '"
								+ q.getId() + "');");
				while (answers.next()) {
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
			if (res.size() == 0)
				return null;
			return res;
		} catch (SQLException e) {
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
	private ArrayList<String> getDataFromDB(String query, String out) {
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

	private ArrayList<String> getCompletedTests_db(String query, String out) {
			ArrayList<String> res = new ArrayList<>();
			res.add(out); //add a new identifier
		try {
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				for (int index = 0; index < 12; index++) {
					res.add(result.getString(index));
				}
				// Test exams = new Test(
				// 		result.getString(1),
				// 		result.getString(2),
				// 		result.getString(3),
				// 		result.getString(4),
				// 		result.getString(5),
				// 		result.getString(6),
				// 		result.getString(7),
				// 		result.getString(8),
				// 		result.getString(9),
				// 		result.getString(10),
				// 		result.getString(11));
				// res.add(exams);
			}
			return res; //res size is 13 where in first index is indentifier
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
		} catch (SQLException ex) {// handle any errors
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
		ArrayList<ResultSet> res = new ArrayList<>();
		ResultSet queryResult = stmt.executeQuery(query_passed);

		// outputList.addAll((String)queryResult.getString(query_passed));
		while (queryResult.next()) {
			outputList.add(queryResult.getString(grade_index));
		}
		return outputList;
	}

	/*
	 * // query_failed: select failed students query
	 * // grade_index - position of the grade field
	 * private ArrayList<String> testGrades_failed_Query(String query_failed, int
	 * grade_index) throws SQLException {
	 * ArrayList<String> outputList = new ArrayList<String>();
	 * stmt = conn.createStatement();
	 * ArrayList<ResultSet> res = new ArrayList<>();
	 * ResultSet queryResult = stmt.executeQuery(query_failed);
	 * 
	 * // outputList.addAll((String)queryResult.getString(query_passed));
	 * while (queryResult.next()) {
	 * outputList.add(queryResult.getString(grade_index));
	 * }
	 * return outputList;
	 * }
	 */

}
