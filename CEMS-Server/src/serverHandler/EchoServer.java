package serverHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.QuestionModel;
import ocsf.server.*;

@SuppressWarnings("unchecked")
/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	private static Connection conn = null;
	private Statement stmt;

	/**
	 * The default port to listen on.
	 */

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}
	// Methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client);
		ResultSet result;
		String notFound = "Not Found", res;
		if (msg instanceof ArrayList) {
			ArrayList<String> list = (ArrayList<String>) msg;

			if (list.get(0).equals("editquestion")) {
				try {
					int isReturned = editQuestion(list.get(1), "editquestion");
					if (isReturned == 0)
						client.sendToClient((Object) notFound);
					client.sendToClient((Object) isReturned);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}

			else if (list.get(0).equals("lecturersubjects")) {
				// gets lecturer subjects
				try {
					ArrayList<String> resList = getSubjectsFromDBForLecturer(list.get(1), "lecturersubjects");
					if (resList == null)
						client.sendToClient((Object) notFound);
					client.sendToClient((Object) resList);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} else if (list.get(0).equals("lecturerquestions")) {
				// gets all lecturer questions from db
				try {
					ArrayList<QuestionModel> resList = getQuestionsFromDBForLecturer(list.get(1));
					if (resList == null)
						client.sendToClient((Object) notFound);
					System.out.println("Server: " + resList.toArray());
					client.sendToClient((Object) resList);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException eq) {
					eq.printStackTrace();
				}
			}

			else {
				try {
					// user login authentication
					stmt = conn.createStatement();
					result = stmt.executeQuery(list.get(0));
					if (result.next()) {
						// check if the password in the DB is the same as user input
						if (result.getString(2).equals(list.get(2))) {
							res = result.getString(1) + " " + result.getString(2) + " " + result.getString(3);
							System.out.println("Message sent back: " + res);
							client.sendToClient((Object) res);
						}
					} else {
						// The user is not in the database (not registered)
						// or incorrect password
						System.out.println("user has not been found!");
						client.sendToClient((Object) notFound);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} else {
			if (msg instanceof String) {
				String query = (String) msg;
				res = "allsubjects,";
				boolean hasFound = false;
				try {
					stmt = conn.createStatement();
					result = stmt.executeQuery(query);
					while (result.next()) {
						hasFound = true;
						res += result.getString(1) + ",";
					}
					if (!hasFound) {
						client.sendToClient((Object) notFound);
					}
					System.out.println("Sent back: " + res);
					client.sendToClient((Object) res);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					client.sendToClient(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int editQuestion(String query, String out) throws SQLException {
		stmt = conn.createStatement();
		int res = stmt.executeUpdate(query);
		System.out.println(res);
		return res;
	}
	// gets questions from db

	private ArrayList<QuestionModel> getQuestionsFromDBForLecturer(String query) throws SQLException {
		stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);
		ArrayList<QuestionModel> res = new ArrayList<QuestionModel>();
		while (result.next()) {
			// while threres questions in result , adding them into result array
			QuestionModel q = new QuestionModel(result.getString(1), result.getString(2), result.getString(3),
					result.getString(4), result.getString(5), result.getString(6));
			res.add(q);
		}
		System.out.println("Message sent back: " + res);
		if (res.size() == 0)
			return null;
		return res;
	}

	// gets subjects from db
	private ArrayList<String> getSubjectsFromDBForLecturer(String query, String out) throws SQLException {
		stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);
		ArrayList<String> res = new ArrayList<String>();
		res.add(out);
		if (result.next()) {
			// check if the password in the DB is the same as user input
			res.add(result.getString(1));
			System.out.println("Message sent back: " + res);
			return res;
		} else {
			return null;
		}
	}

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
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {

		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
