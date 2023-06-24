package unittests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import clientControllers.LoginScreenController;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import javafx.event.ActionEvent;


public class LoginTest {
	private LoginScreenController mockLoginScreenController;
	private ActionEvent event;
//	private TextField emailTextbox, passTextbox;
//	private ComboBox<String> combo_Role;

	@Before
	public void setUp() throws  IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		
		mockLoginScreenController = spy(new LoginScreenController()); // Misha : see if this spy crap works


	}

	/**
	 * Test for checking the login of with null Credentials.
	 * Input : null as credentials.
	 * Result : Fail with "Credentials are empty!" message.
	 * @throws IOException 
	 */
	@Test
	public void TestloginAllNullCrerdentials() throws IOException {
		
		// Misha : Changed the code in login controller a bit to fit this shit .
		// So "this shit" defines that when getLoginCredentials() of the controller is called
		// we return null with doReturn and the "if" in line 71 in loginScreenController
		// should return the "Credentials are empty!" message.

		doReturn(null).when(mockLoginScreenController).getLoginCredentials();
		String expected = "Credentials are empty!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
		
	}


	/**
	 * Test for checking the login of with wrong type.
	 * Input : "Amir_Mishayev" as username, "123456" as password, null as type.
	 * Result : Fail with "Role not selected!" message.
	 * @throws IOException 
	 */
	@Test
	public void TestloginNullType() throws IOException {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add(null);
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		String expected = "Role not selected!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test for checking the login of with wrong username.
	 * Input : "WrongUsername" as username, "123456" as password, "lecturer" as type.
	 * Result : Fail with "User not found!" message.
	 */
	@Test
	public void TestloginWrongUsername() throws IOException {
		
		// Misha : changed the code also for this shit. added method to varify the credentials ,
		// returns true or false based if the user was found.  

		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("WrongUsername");
		credentials.add("123456");
		credentials.add("lecturer");
		when(mockLoginScreenController.getLoginCredentials()).thenReturn(credentials);
		when(mockLoginScreenController.varifyCredentials(anyString(), anyString(), anyString())).thenReturn(false);
		String expected = "user not found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}



	/**
	 * Test for checking the login of with wrong password.
	 * Input : "Amir_Mishayev" as username, "WrongPassword" as password, "lecturer" as type.
	 * Result : Fail with "User not found!" message.
	 * @throws IOException 
	 */
	@Test
	public void TestloginWrongPassword() throws IOException {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("WrongPassword");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		when(mockLoginScreenController.varifyCredentials(anyString(), anyString(), anyString())).thenReturn(false);
		String expected = "User not found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test for checking the login of with wrong type.(Amir_Mishayev not from type student)
	 * Input : "Amir_Mishayev" as username, "123456" as password, "student" as type.
	 * Result : Fail with "User not found!" message.
	 * @throws IOException 
	 */
	@Test
	public void TestloginWrongType() throws IOException { // Not really sure what happans here when the type doesnt correspond
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add("student");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		when(mockLoginScreenController.varifyCredentials(anyString(), anyString(), anyString())).thenReturn(false);
		String expected = "User not found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test method to test correct input of the user credentials.
	 * Input : "Amir_Mishayev" as username , "123456" as password, "lecturer" as type.
	 * Result : Success with "Success User Found!" message.
	 * @throws IOException 
	 */
	@Test
  	public void testLoginSuccessful() throws IOException {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		when(mockLoginScreenController.varifyCredentials(anyString(), anyString(), anyString())).thenReturn(false);
		String expected = "Success User Found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}

}





















/*
 * @Test
 * passToServer_Test_user_Exists(){
 * String name = "noah_soskha";
 * String pass = "123456";
 * String role = "lecturer";
 * credentials.addAll(Arrays.asList(query, name, pass, role));
 * client.passToServer((Object) credentials);
 * user.
 * }
 */

/**
 * Functional testing:
 * input: credentials
 * expected result: userNotFound
 */
/*
 * @Test
 * passToServer_Test_user_DoesntExist(){
 * String name = "Efraim";
 * String pass = "123456";
 * String role = "lecturer";
 * credentials.addAll(Arrays.asList(query, name, pass, role));
 * client.passToServer((Object) credentials);
 * 
 * }
 */

/**
 * Functional testing:
 * input: credentials
 * expected result:
 */
/*
 * @Test
 * passToServer_Test_user_RoleInvalid(){
 * String name = "noah_soskha";
 * String pass = "123456";
 * String role = "lecturer";
 * credentials.addAll(Arrays.asList(query, name, pass, role));
 * client.passToServer((Object) credentials);
 * }
 */

/**
 * Functional testing:
 * input: credentials
 * expected result:
 */
/*
 * @Test
 * passToServer_Test_user_PasswordIncorrect(){
 * String name = "noah_soskha";
 * String pass = "123456";
 * String role = "lecturer";
 * credentials.addAll(Arrays.asList(query, name, pass, role));
 * client.passToServer((Object) credentials);
 * }
 * 
 * @Test
 * public void test() {
 * fail("Not yet implemented");
 * }
 * 
 * public void loginVarification_Test(Object username, Object password, Object
 * type) {
 * 
 * 
 * }
 */

// @Before
// public void setUp() throws Exception {
// ClientHandler client;
// ArrayList<String> credentials = new ArrayList<String>();

// int port = 5555;
// // set up a new client new client
// try {
// client = new ClientHandler(host, port, this);
// } catch (IOException exception) {
// System.out.println("Error: Can't setup connection!" + " Terminating
// client.");
// System.exit(1);
// }

// // create a query to grab username requested
// String name = "noah_soskha";
// String pass = "123456";
// String role = "lecturer";
// String query = String.format("SELECT * FROM users WHERE username = '%s' AND
// password = '%s' AND type = '%s';",
// name, pass, role);

// // pass email and password to the client for authentication

// }
/* maybe add later */// @After
// public void tearDown() throws Exception {
// }

/**
 * Functional testing: server returns a String res in this order: username
 * password lecturer userID Email input: credentials expected
 * result:true"Noah_Soskha 123456 lecturer 456 Noah.Soskha@e.braude.ac.il
 * Software"
 */