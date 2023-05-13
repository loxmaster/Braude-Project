package serverHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import serverOcsf.*;

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
	private Connection conn = null;
	private Statement stmt;

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

			if (list.get(0).equals("lecturersubjects")) {
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
					ArrayList<String> resList = getQuestionsFromDBForLecturer(list.get(1), "lecturerquestions");
					if (resList == null)
						client.sendToClient((Object) notFound);
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
					//String resultQuery = AuthenticateUser(list);
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
 
	// gets questions from db
	// SELECT questiontext FROM projecton.questions WHERE ( `lecturer` = 'noah' );
	private ArrayList<String> getQuestionsFromDBForLecturer(String query, String out) throws SQLException {
		stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);
		ArrayList<String> res = new ArrayList<String>();
		res.add(out);
		while (result.next()) {
			// check if the password in the DB is the same as user input
			res.add(result.getString(1));
			return res;
		} 
		System.out.println("Message sent back: " + res);
		if (res.size() == 1) 
			return null;
		return res;
		// [lecturerquestions , why? , ...]
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

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projecton?serverTimezone=IST", "root",
					"02587595mM!");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
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
