package unitests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerTest {

	// Server variables
	private EchoServer mockServer;
	private String host, username, password, database;
	private ConnectionToClient client;
	
	// Test variables
	ArrayList<String> credentials;
	private String studentUserName, lecturerUsername;


	@BeforeEach
	void setUp() throws Exception {
		mockServer = mock(EchoServer.class);;
        host = "localhost";
        username = "root";
        password = "123456";
        database = "projecton";
		mockServer.startServer(database, username, password);

		credentials = new ArrayList<>();
		studentUserName = "Paulina";
		lecturerUsername = "Noah_Soskha";
	}

	
	/**
	 * Test for login where the credentials are null;
	 * Input : Null as credentials.
	 * Output : Fail with message "User Not Found"
	 */
	@Test
	void LoginWithNullCredentials() {
		credentials.add(null);
		credentials.add(null);
		credentials.add(null);
		String expected = "User Not Found";
		String result = mockServer.handleMessageFromClient( (Object) credentials, null );
		assertEquals(expected, result);
	}


	/**
	 * Test for login where username is null;
	 * Input : Null as username, "123456" as password, "lecturer" as type.
	 * Output : Fail with message "User Not Found"
	 */
	@Test
	void LoginWithNullUsername() {
		credentials.add(null);
		credentials.add("123456");
		credentials.add("lecturer");
		String expected = "User Not Found";
		String result = mockServer.handleMessageFromClient( (Object) credentials, null );
		assertEquals(expected, result);
	}


	/**
	 * Test for login where password null;
	 * Input : Null as credentials.
	 * Output : Fail with message "Wrong password"
	 */
	@Test
	void LoginWithNullPassword() {
		credentials.add(studentUserName);
		credentials.add(null);
		credentials.add("student");
		String expected = "Wrong password";
		String result = mockServer.handleMessageFromClient( (Object) credentials, null );
		assertEquals(expected, result);
	}


	/**
	 * Test for login where password entered is wrong;
	 * Input : studentUserName as username, "WrongPassword"as password , "student" as type .
	 * Output : Fail with message "Wrong password"
	 */
	@Test
	void LoginWithWrongPassword() {
		credentials.add(studentUserName);
		credentials.add("WrongPassword");
		credentials.add("student");
		String expected = "Wrong password";
		String result = mockServer.handleMessageFromClient( (Object) credentials, null );
		assertEquals(expected, result);
	}


	/**
	 * Test for login where the credentials are correct;
	 * Input : Null as credentials.
	 * Output : Success with message "Login varified"
	 */
	@Test
	void LoginSuccessful() {
		credentials.add(studentUserName);
		credentials.add("123456");
		credentials.add("student");
		String expected = "Login varified";
		String result = mockServer.handleMessageFromClient( (Object) credentials, null );
		assertEquals(expected, result);
	}



}
