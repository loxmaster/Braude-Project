package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;

import clientControllers.CreateQuestionController;
import clientControllers.CreateTestController;
import clientControllers.LecturerController;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import logic.QuestionModel;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CreateTest_Test {

	// private variables we goona use in the tests
	private CreateTestController mockCreateTestController;
	// private CreateQuestionController mockCreateQuestionController;
	private ActionEvent event;
	private DatePicker date;
	private ArrayList<QuestionModel> qtest;

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
		doReturn(false).when(mockCreateTestController).areQuestionsInTest();
		boolean result = mockCreateTestController.qInTestsAreHundred(101);

		assertFalse(result);
	}

	@Test
	public void testSavePressedWithValidPoints() {
		doNothing().when(mockCreateTestController).callTheTestFromUI();
		doReturn(false).when(mockCreateTestController).areQuestionsInTest();
		boolean result = mockCreateTestController.qInTestsAreHundred(100);

		assertTrue(result);
	}

	@Test
	public void testSavePressedWithInvalidDate() {
		doNothing().when(mockCreateTestController).callTheTestFromUI();
		doReturn(false).when(mockCreateTestController).areQuestionsInTest();
		doReturn(true).when(mockCreateTestController).qInTestsAreHundred(anyInt());
		doNothing().when(mockCreateTestController).updateStyles(anyInt());
		doReturn(false).when(mockCreateTestController).isDateValid();
		doNothing().when(mockCreateTestController).updateStyles(anyInt());
		doNothing().when(mockCreateTestController).showAlert(anyString(),anyString());
		String expected = "Date Not Valid!";
		String result = mockCreateTestController.savePressed(event);
		assertEquals(expected, result);

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
