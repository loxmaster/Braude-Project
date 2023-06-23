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
	private LoginScreenController mokLoginScreenController;
	private ActionEvent event;
	private TextField emailTextbox, passTextbox;
	private ComboBox<String> combo_Role;

	@Before
	public void setUp() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		mokLoginScreenController = new LoginScreenController();
		emailTextbox = mock(TextField.class);
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
		combo_RoleField.set(mokLoginScreenController, combo_Role);

	}

@Test
public void TestloginAllNullCredentials() throws Exception{
	when(emailTextbox.getText()).thenReturn(null);
	when(passTextbox.getText()).thenReturn(null);
	when(combo_Role.getValue()).thenReturn(null);

	mokLoginScreenController.pressedLogin(mock(ActionEvent.class));

	verify(emailTextbox.setStyle().equals("-fx-background-color: rgb(255, 74, 74);")));
	verify(passTextbox.setStyle().equals("-fx-background-color: rgb(255, 74, 74);")));
	verify(combo_Role.setStyle().equals("-fx-background-color: rgb(255, 74, 74);")));

	assertTrue(emailTextbox.getStyle().equals("-fx-background-color: rgb(255, 74, 74);")));
	assertTrue(passTextbox.getStyle().equals("-fx-background-color: rgb(255, 74, 74);")));
	assertTrue(combo_Role.getStyle().equals("-fx-background-color: rgb(255, 74, 74);")));
	
	//?
	//verify(mokLoginScreenController, times(1)).showErrorMessage("your username or password are incorrect!");
	}

}

@Test
  public void testLoginSuccessful() {
	  when(emailTextbox.getText()).thenReturn("noah_soskha");
	  when(emailTextbox.getText()).thenReturn("validUser");
	  when(emailTextbox.getText()).thenReturn("validUser");

       verify(mokLoginScreenController, times(1)).openScreen(anyString(), anyString(), any());

		
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