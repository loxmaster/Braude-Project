package unitests;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateTest_Test {

	// private variables we goona use in the tests
	private CreateTestController mockCreateTestController;
	private Test test;
	private QuestionModel question1, question2;

	@BeforeEach
	void setUp() throws Exception {
		//mockCreateTestController = new CreateTestController();

		mockCreateTestController = spy(new CreateTestController()); // not sure


		test = new Test ();

		question1 = new QuestionModel();
		question2 = new QuestionModel();

		
	}

	@Test
	void test1() { // Misha : i think we need to test only the save method in createtestcontroller
		// test.setQuesitonsInTest(Arrays.asList(null));
	}

	@Test
	void test2() {
		// test.setQuesitonsInTest(Arrays.asList(question1, question2));
	}

	@Test
	void test3() {
		// test.setDuration(null);
	}

}
