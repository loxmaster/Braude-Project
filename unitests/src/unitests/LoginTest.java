package unitests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import clientControllers.LoginScreenController;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import javafx.event.ActionEvent;

//import gui.UserScreens.LogInScreenController;
import javafx.event.ActionEvent;

public class LoginTest {
	private LoginScreenController mockLoginScreenController;
	private ActionEvent event;
	//private TextField emailTextbox, passTextbox;
	//private ComboBox<String> combo_Role;

	@Before
	public void setUp() {

		//mockLoginScreenController = new LoginScreenController();
		
		mockLoginScreenController = spy(new LogInScreenController()); // Misha : see if this spy crap works

		/*emailTextbox = mock(TextField.class);
		passTextbox = mock(TextField.class);
		combo_Role = mock(ComboBox.class);

		// Use reflection to access and modify private fields
		Field emailTextboxField = LoginScreenController.class.getDeclaredField("emailTextbox");
		emailTextboxField.setAccessible(true);
		emailTextboxField.set(mokLoginScreenController, emailTextbox);

		Field passTextboxField = LoginScreenController.class.getDeclaredField("passTextbox");
		passTextboxField.setAccessible(true);
		passTextboxField.set(mokLoginScreenController, passTextbox);

		Field combo_RoleField = LoginScreenController.class.getDeclaredField("combo_Role");
		combo_RoleField.setAccessible(true);
		combo_RoleField.set(mokLoginScreenController, combo_Role);*/

	}

	/**
	 * Test for checking the login of with null Credentials.
	 * Input : null as credentials.
	 * Result : Fail with "Credentials are empty!" message.
	 */
	@Test
	public void TestloginAllNullCrerdentials() {
		
		// Misha : Changed the code in login controller a bit to fit this shit .
		// So "this shit" defines that when getLoginCredentials() of the controller is called
		// we return null with doReturn and the "if" in line 71 in loginScreenController
		// should return the "Credentials are empty!" message.

		doReturn(null).when(mockLoginScreenController).getLoginCredentials();
		String expected = "Credentials are empty!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
		
		// IDK whats all that bellow decided to not delete and just to comment it.
		
		/*when(emailTextbox.getText()).thenReturn(null);
		when(passTextbox.getText()).thenReturn(null);
		when(combo_Role.getValue()).thenReturn(null);

		mokLoginScreenController.pressedLogin(mock(ActionEvent.class));

		verify(emailTextbox.setStyle().equals("-fx-background-color: rgb(255, 74, 74);"));
		verify(passTextbox.setStyle().equals("-fx-background-color: rgb(255, 74, 74);"));
		verify(combo_Role.setStyle().equals("-fx-background-color: rgb(255, 74, 74);"));

		assertTrue(emailTextbox.getStyle().equals("-fx-background-color: rgb(255, 74, 74);"));
		assertTrue(passTextbox.getStyle().equals("-fx-background-color: rgb(255, 74, 74);"));
		assertTrue(combo_Role.getStyle().equals("-fx-background-color: rgb(255, 74, 74);"));*/
		
		//?
		//verify(mokLoginScreenController, times(1)).showErrorMessage("your username or password are incorrect!");
	}


	/**
	 * Test for checking the login of with wrong type.
	 * Input : "Amir_Mishayev" as username, "123456" as password, null as type.
	 * Result : Fail with "Role not selected!" message.
	 */
	@Test
	public void TestloginNullType() {
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
	public void TestloginWrongUsername() {
		
		// Misha : changed the code also for this shit. added method to varify the credentials ,
		// returns true or false based if the user was found.  

		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("WrongUsername");
		credentials.add("123456");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doReturn(false).when(mockLoginScreenController).varifyCredentials(); // maybe should send parameters where
		String expected = "user not found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test for checking the login of with wrong password.
	 * Input : "Amir_Mishayev" as username, "WrongPassword" as password, "lecturer" as type.
	 * Result : Fail with "User not found!" message.
	 */
	@Test
	public void TestloginWrongPassword() {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("WrongPassword");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doReturn(false).when(mockLoginScreenController).varifyCredentials(); // maybe should send parameters where
		String expected = "User not found!";
		String result = mockloginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test for checking the login of with wrong type.
	 * Input : "Amir_Mishayev" as username, "123456" as password, "student" as type.
	 * Result : Fail with "User not found!" message.
	 */
	@Test
	public void TestloginWrongType() { // Not really sure what happans here when the type doesnt correspond
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add("student");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doReturn(false).when(mockLoginScreenController).varifyCredentials(); // maybe should send parameters where
		String expected = "User not found!";
		String result = mockloginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}


	/**
	 * Test method to test correct input of the user credentials.
	 * Input : "Amir_Mishayev" as username , "123456" as password, "lecturer" as type.
	 * Result : Success with "Success User Found!" message.
	 */
	@Test
  	public void testLoginSuccessful() {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doReturn(true).when(mockLoginScreenController).varifyCredentials(); // maybe should send parameters where
		String expected = "Success User Found!";
		String result = mockloginScreenController.pressedLogin(event);
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