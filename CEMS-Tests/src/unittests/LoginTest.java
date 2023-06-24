package unittests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import clientControllers.LoginScreenController;
import clientHandlers.ClientController;
import clientHandlers.ClientUI;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import javafx.event.ActionEvent;


public class LoginTest {
	private LoginScreenController mockLoginScreenController;
	private ActionEvent event;
	private ClientController mockChat;


	@Before
	public void setUp() throws NoSuchFieldException {

		mockLoginScreenController = spy(new LoginScreenController());
		mockChat = mock(ClientController.class);
		ClientUI.chat = mockChat;
	}
	
	

	/**
	 * Test for checking the login of with null Credentials. Input : null as
	 * credentials. Result : Fail with "Credentials are empty!" message.
	 * 
	 * @throws IOException
	 */
	@Test
	public void TestloginNullCrerdentials() throws IOException {
		doReturn(null).when(mockLoginScreenController).getLoginCredentials();
		doNothing().when(mockLoginScreenController).updateStyles(anyInt());
		String expected = "Credentials are empty!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);

	}

	/**
	 * Test for checking the login of with wrong type. Input : "Amir_Mishayev" as
	 * username, "123456" as password, null as type. Result : Fail with "Role not
	 * selected!" message.
	 * 
	 * @throws IOException
	 */
	@Test
	public void TestloginNullType() throws IOException {
		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("Amir_Mishayev");
		credentials.add("123456");
		credentials.add(null);
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doNothing().when(mockLoginScreenController).updateStyles(anyInt());
		doNothing().when(mockLoginScreenController).showAlert(anyString(), anyString());

		String expected = "Role not selected!";
		String result = mockLoginScreenController.pressedLogin(event);
		// Verify that showAlert was called with the correct parameters
		assertEquals(expected, result);
	}

	/**
	 * Test for checking the login of with wrong username.
	 * Input : "WrongUsername" as username, "123456" as password, "lecturer" as type.
	 * Result : Fail with "User not found!" message.
	 */
	@Test
	public void TestloginWrongUsername() throws IOException {
	

		ArrayList<String> credentials = new ArrayList<>();
		credentials.add("WrongUsername");
		credentials.add("123456");
		credentials.add("lecturer");
		doReturn(credentials).when(mockLoginScreenController).getLoginCredentials();
		doReturn(false).when(mockLoginScreenController).varifyCredentials(anyString(), anyString(), anyString());
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
		doReturn(false).when(mockLoginScreenController).varifyCredentials(anyString(), anyString(), anyString());
		String expected = "user not found!";
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
		doReturn(false).when(mockLoginScreenController).varifyCredentials(anyString(), anyString(), anyString());
		String expected = "user not found!";
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
		doReturn(true).when(mockLoginScreenController).varifyCredentials(anyString(), anyString(), anyString());
		doNothing().when(mockLoginScreenController).lodingfxml(any(ActionEvent.class),anyString());
		String expected = "Success User Found!";
		String result = mockLoginScreenController.pressedLogin(event);
		assertEquals(expected, result);
	}
	
	
}

