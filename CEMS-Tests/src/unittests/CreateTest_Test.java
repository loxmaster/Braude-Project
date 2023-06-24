package unittests;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import clientControllers.CreateTestController;
import clientHandlers.ClientController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.io.IOException;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;


public class CreateTest_Test {

	// private variables we goona use in the tests
	private CreateTestController mockCreateTestController;   
	private ActionEvent event;

	
	@Before
	public void setUp() throws Exception {
		mockCreateTestController = spy(new CreateTestController()); 

	} 

	
	@Test
	public void testSavePressedWithEmptyQuestions() { 
		doNothing().when(mockCreateTestController).callTheTestFromUI();
		doNothing().when(mockCreateTestController).showAlert(anyString(), anyString());		
		String expected = "Please Add Questions!";
		String result = mockCreateTestController.savePressed(event);
		assertEquals(expected, result);
	}

	@Test
	public void testSavePressedWithInvalidPoints() { 
		doNothing().when(mockCreateTestController).callTheTestFromUI();
		Test test = new Test();
		
	}
	
	
//	
//	@Test
//	public void testSavePressedWithInvalidDate() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithoutCourse() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithoutSubject() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithInvalidStartTimeFormat() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithInvalidDurationFormatOrZeroDuration() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithInvalidTestCodeLength() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithValidData() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}
//	
//	@Test
//	public void testSavePressedWithDatabaseErrors() { 
//		doNothing().when(mockCreateTestController).callTheTestFromUI();
//	}

}
