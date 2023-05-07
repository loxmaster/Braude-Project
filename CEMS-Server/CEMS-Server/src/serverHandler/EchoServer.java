package serverHandler;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import serverOcsf.*;;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Connection conn = null;
		Statement stmt;

		if (msg instanceof String) {
			String tempQuery = (String) msg;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
				System.out.println("Driver definition succeed");
			} catch (Exception ex) {
				/* handle the error */
				System.out.println("Driver definition failed");
			}
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost/lab6schema?serverTimezone=IST", "root",
						"02587595mM!");
				System.out.println("SQL connection succeed");
			} catch (SQLException ex) {// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}

			if (tempQuery.contains("SELECT")) {
				// Search existing student
				try {
					stmt = conn.createStatement();
					ResultSet result = stmt.executeQuery(tempQuery);
					if (result.next()) {
						int tempID = result.getInt(1);
						String name = result.getString(2);
						System.out.println("Got from query: " + name);
						this.sendToAllClients("Query got: " + name + " ID - " + tempID);
					} else {
						System.out.println("Query didnt find anything.");
						this.sendToAllClients("Query didnt find anything.");
					}
				} catch (SQLException e) {
					System.out.println("Query Failed");
					e.printStackTrace();
				}
			}
			if (tempQuery.contains("UPDATE")) {
				// Update existing student
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate(tempQuery);
					System.out.println("Database Updated.");
					this.sendToAllClients("Database Updated.");
				} catch (SQLException e) {
					System.out.println("Update Failed");
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Message received: " + msg + " from " + client);
			this.sendToAllClients(msg);
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

	// Class methods ***************************************************

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
// End of EchoServer class
