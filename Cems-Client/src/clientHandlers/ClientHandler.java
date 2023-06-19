package clientHandlers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import clientControllers.CreateQuestionController;
import clientControllers.HODController;
import clientControllers.LecturerController;
import clientControllers.OngoingTestController;
import logic.Question;
import logic.QuestionModel;
import logic.Test;
import logic.User;
import ocsf.client.AbstractClient;

@SuppressWarnings("unchecked")
/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 */
public class ClientHandler extends AbstractClient {

	/**
	 * @param client       interface type variable. It allows the implementation of
	 *                     the display method in the client.
	 * @param User         static variable to remember localy the data about user.
	 * @param subjectsList static arrayList of all the subjects available.
	 */

	ChatIF client;
	public static User user = new User();

	// Constructors ****************************************************

	public ClientHandler(String host, int port, ChatIF client) throws IOException {
		super(host, port);
		this.client = client;
		openConnection();
	}

	////////////////////////////////////////////////////////////
	/////////////// HANDLE MESSAGHE FROM SERVER ///////////////
	//////////////////////////////////////////////////////////

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param severMessage The message from the server.
	 */
	public void handleMessageFromServer(Object severMessage) {
		System.out.println("got message: " + severMessage);
		String[] subjectArray;
		ArrayList<String> list;
		ArrayList<Question> questionList;
		ArrayList<Test> TestsList;


		// TODO add a comment here
		if (severMessage instanceof Integer) {
			if ((Integer) severMessage == 1)
				System.out.println("Update was successful");	
			else
				System.out.println("Update wasnt so successful");
		} 
		
		else if (severMessage instanceof ArrayList) {
			
			if (((ArrayList<?>) severMessage).get(0) instanceof Question) {
				questionList = (ArrayList<Question>) severMessage;
				ArrayList<QuestionModel> listToAdd = new ArrayList<>();

				for (int i = 0; i < questionList.size(); i++) {
					listToAdd.add( new QuestionModel(
							questionList.get(i).getId(), 
							questionList.get(i).getSubject(),
							questionList.get(i).getCoursename(),
							questionList.get(i).getQuestiontext(), 
							questionList.get(i).getQuestionnumber(),
							questionList.get(i).getLecturer(),
							questionList.get(i).getOptionA(),
							questionList.get(i).getOptionB(),
							questionList.get(i).getOptionC(),
							questionList.get(i).getOptionD(),
							questionList.get(i).getAnswer()));
				}

				LecturerController.setQuestions(listToAdd);
			}
			else if (((ArrayList<?>) severMessage).get(0) instanceof Test) {
				System.out.println("we catched instance of Test");
				TestsList = (ArrayList<Test>) severMessage;
				
				if(TestsList.get(0).getTimeToAdd()!=null) { //if the test have timeToAdd, we know that its test from the time extension permission tests
					HODController.setOngoingTests_permissions(TestsList);
					calculateTest_timeLeft(TestsList);//send the tests to calculate and update the timeLeft

				}		
				else {
					calculateTest_timeLeft(TestsList);//send the tests to calculate and update the timeLeft
					
		            LecturerController.setOngoingTests(TestsList);
				}
		     }

			else {
				list = ((ArrayList<String>) severMessage);

				if (list.get(0).equals("lecturersubjects")) {
					subjectArray = list.get(1).split(",");
					for (String s : subjectArray) {
						LecturerController.getSubjectsList().add(s.toUpperCase());
					}
				}

				// assign subjectID that we've got from the server
				else if (list.get(0).equals("getSubjectID")) {
					// subjectArray = list.get(1).split(",");
					System.out.println("Client Handler: " + list.get(1));
					CreateQuestionController.setSubjectID(list.get(1));
				}
				else if (list.get(0).equals("getSubjectsCourseForTest")) {
                    System.out.println("Client Handler: " + list.get(0));
                    ArrayList<String> listToAdd = new ArrayList<>();
                    int i = 1;
                    while (i < list.size()) {
                        listToAdd.add(list.get(i));
                        i++;
                    }
                    OngoingTestController.setSubjectsCoursesList(listToAdd);
				}
				else if (list.get(0).equals("getSubjectNameFromCode")) {
					
				}
			}
		}

		// Handles the error of user not found.
		else if (severMessage.toString().equals("User Not Found")) {
			System.err.println("Not Found in handler");
			user.setIsFound(false);
		}

		// Handles the error the the question already exist with that id.
		else if (severMessage.toString().contains("Question Exists")) {


		}

		// Handles the error of Not Found
		else if (severMessage.toString().contains("Not Found")) {

		}

		// Handles the error of Id Exists
		else if (severMessage.toString().contains("Id Exists")) {

		}

		else {
			// Here we recieve the confirmation of the client login
			subjectArray = ((String) severMessage).toString().split("\\s");
			user.setUsername(subjectArray[0]);
			user.setPassword(subjectArray[1]);
			user.setType(subjectArray[2]);
			// rest of the stuff in the table
			user.setIsFound(true);
		}

		System.out.println("--> messageFromServerHandled");
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			client.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/**
	 * Handles the message received from the client login user interface
	 *
	 * @param username username entered.
	 * @param password password entered.
	 */
	public void handleMessageFromLoginUI(Object username, Object password) {
		ArrayList<String> credentials = new ArrayList<String>();

		String query = "SELECT * FROM users  WHERE (`username` = '" + username + "');";
		credentials.addAll(Arrays.asList(query, (String) username, (String) password));

		try {
			sendToServer((Object) credentials);
		} catch (IOException e) {
			client.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects.
	 */
	public void handleMessageFromLecturerUI() {
		// SELECT courses FROM projecton.lecturer WHERE (`username` = 'noah');
		try {
			sendToServer((Object) "SELECT DISTINCT subjectname FROM subjectcourses;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * subjects for the lecturer.
	 */
	public void handleMessageFromLecturerUI(Object username) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("lecturersubjects",
				"SELECT courses FROM projecton.lecturer WHERE (`username` = '" + (String) username + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////
	////////////////// LOGIC METHODS /////////////////
	/////////////////////////////////////////////////
	
	/**
	 * calculate the test left time for the ongoing tests 
	 */
	private void calculateTest_timeLeft(ArrayList<Test> tests) {

		for(Test test : tests) {
		// Calculate time left
           LocalTime testTime = LocalTime.parse(test.getTime());
           LocalDate testDate = LocalDate.parse(test.getDateString());
           ZonedDateTime now = ZonedDateTime.now();
           ZonedDateTime testZonedDateTime = ZonedDateTime.of(testDate, testTime, now.getZone());

           // Parse duration which is in HH:mm format
           String[] durationParts = test.getDuration().split(":");
           long hours = Long.parseLong(durationParts[0]);
           long minutes = Long.parseLong(durationParts[1]);
           Duration testDuration = Duration.ofHours(hours).plusMinutes(minutes);

           ZonedDateTime testEndTime = testZonedDateTime.plus(testDuration);

           // Calculate time left manually
           if (testEndTime.isBefore(now)) {
               test.setTimeLeft("00:00"); // Test has ended
           } else {
               Duration durationToEnd = Duration.between(now, testEndTime);
               long totalSeconds = durationToEnd.getSeconds();
               long hoursLeft = totalSeconds / 3600;
               long minutesLeft = (totalSeconds % 3600) / 60;
               String formattedTimeLeft = String.format("%02d:%02d", hoursLeft, minutesLeft);
               test.setTimeLeft(formattedTimeLeft);
           }
       }
	}
	/**
	 * handle the test recieved from the HOD user and deletes from the DB from table hod_timeextensionrequests test who was
	 *  approved/denied by the hod
	 */
	public void Update_HOD_permissionsTable_inDB(Test test) {
	    ArrayList<String> request = new ArrayList<>();
	    String query = "DELETE FROM hod_timeextensionrequests WHERE id = " + test.getId();
	    request.add("updateHODPermissionsTable");
	    request.add(query);

	    try {
	        sendToServer((Object)request);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	/**
	 *ask from the server to get course by the id , check both subjectid and courseid to find the correspond id from the DB
	 */
	public void getCourseForTest(String id) {

        ArrayList<String> subjectcoursenameofcompletedtest = new ArrayList<String>();
        String subjectid = id.substring(0, 2);
        String courseid = id.substring(2, 4);
        String query = String.format(
                "SELECT * FROM projecton.subjectcourses WHERE subjectid='%s' AND courseid='%s';",
                subjectid, courseid);
        subjectcoursenameofcompletedtest.addAll(Arrays.asList("getSubjectsCourseForTest", query));
        try {
            sendToServer((Object) subjectcoursenameofcompletedtest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 *ask the server to fetch the ongoing tests from the DB
	 */
	   public void fetchOngoingTests() {
	        ArrayList<String> request = new ArrayList<>();
	        request.add("fetchOngoingTests");
	        try {
	            sendToServer((Object)request);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	   
		/**
		 *send query to server to update the ongoing tests that wait for approve/deny for extra time in hod_timeextensionrequests
		 */
   public void updateHod_timeExtensionRequests_InDB(String id,String comments, String timeToAdd, String subject) {
		ArrayList<String> listToSend = new ArrayList<String>();
        String query = "INSERT INTO `projecton`.`hod_timeextensionrequests` (`id`,`Subject`, `TimeToAdd`, `Reason`) VALUES ('"
                + id + "','" + subject + "', '" + timeToAdd + "', '"
                + comments +"');";
		listToSend.add("Update_timeExtensionRequestsTable");
		listToSend.add(query);
		try {
			sendToServer((Object) listToSend);
			} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
	/**
	 *send query to server to get the ongoing tests that waiting for approve/deny for extra time in hod_timeextensionrequests
	 */
	public void fetch_ongoingTests_permissions_FromDB() {
		ArrayList<String> ongoingTests_permissions_list = new ArrayList<String>();
		String query = "SELECT * FROM hod_timeextensionrequests;";
		ongoingTests_permissions_list.add("fetch_ongoingTests_permissions_FromDB");
		ongoingTests_permissions_list.add(query);
		try {
			sendToServer((Object) ongoingTests_permissions_list);
		} catch (IOException e) {
			e.printStackTrace();
			}
	}   
	/**
	 *send query to server to update the Lock Button status FALSE/TRUE
	 */
	public void updateLockButton_DB(Test test,String value) {
		ArrayList<String> list = new ArrayList<String>();
	    String query = "UPDATE ongoing_tests SET locked = '" + value + "' WHERE test_id = " + test.getId();
		list.add("updateLockButton_DB");
		list.add(query);
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	   
	/**
	 *ask the server to get subject name from code of the test
	 */
   	   public void getSubjectNameFromCode(String code) {
	        ArrayList<Object> list = new ArrayList<>();
	        list.addAll(Arrays.asList("getSubjectNameFromCode", code));

	        try {
	            sendToServer(list);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	/**
	 * Handles the message received from the lecturer user interface gets all the
	 * questions for the lecturer.
	 */
	public void GetLeturersQuestions(String username) {
		/*
		 * try { openConnection(); } catch (IOException e1) { System.out.println(1); }
		 */
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("lecturerquestions",
				"SELECT * FROM projecton.questions where ( `lecturer` = '" + username + "' );"));
		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to create query to edit existing question
	 * @param newBody
	 * @param newQNumber
	 * @param originalId
	 */
	public void EditQuestion(String newBody, String newQNumber, String originalId) {
		ArrayList<String> list = new ArrayList<String>();

		// ugly will stay ugly <3
		list.addAll(Arrays.asList("editquestion",
				"UPDATE `projecton`.`questions` SET `id` = '" + originalId.substring(0, 2) + newQNumber
						+ "', `questiontext` = '" + newBody
						+ "', `questionnumber` = '" + newQNumber + "' WHERE (`id` = '" + originalId + "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// return a list of grades where [testGrades, passed grades, failed grades]
	// query that selects PASSED and FAILED grades --> echoserver to parse
	// parse: [testGrades(echoServer identifier), passed(query), failed(query)]
	public void GetTestGrades_StatisticalInformation(String testID) {
		ArrayList<String> list = new ArrayList<String>();

		// FIXME fix this query
		String query_passed = "SELECT grade from projecton.testResults WHERE grade>=55";
		String query_failed = "SELECT grade from projecton.testResults WHERE grade<55";
		list.addAll(Arrays.asList("testGrades", query_passed, query_failed));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates query for creating a question and 
	 * Passes it to server.
	 * @param Id
	 * @param subject
	 * @param Body
	 * @param QNumber
	 */
	public void CreateQuestion(String Id, String subject, String Body, String QNumber) {
		ArrayList<String> list = new ArrayList<String>();

		// Construct the INSERT query to create a new question
		list.addAll(Arrays.asList("createquestion", "INSERT INTO `projecton`.`questions` (id, subject, questiontext, questionnumber, lecturer) VALUES ('"
						+ Id + "', '" + subject + "', '" + Body + "', '" + QNumber + "', '" + user.getUsername()
						+ "');"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates query for insering answers to database.
	 * @param optionA
	 * @param optionB
	 * @param optionC
	 * @param optionD
	 * @param correctAnswer
	 * @param subjectID
	 */
	public void CreateAnswers(String optionA, String optionB, String optionC, String optionD, String correctAnswer,
			String subjectID) {
		ArrayList<String> list = new ArrayList<String>();
		// Construct the INSERT query to create a new answer
		String query = "INSERT INTO `projecton`.`answers` (optionA, optionB, optionC, optionD, correctAnswer,questionid) VALUES ('"
				+ optionA + "', '" + optionB + "', '" + optionC + "', '" + optionD + "', '" + correctAnswer + "', '"
				+ subjectID + "');";
		list.add("createanswers");
		list.add(query);

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for sending the test to the data base.
	 * @param query 
	 */
	public void sendTestToDatabase(String query) {
		ArrayList<String> listToSend = new ArrayList<String>();
		listToSend.add("Addtesttodata");
		listToSend.add(query);
		try {
			sendToServer((Object) listToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a new arraylist subject, add an identifier "getSubjectID" so the
	 * Echoserver idenrtifies it,
	 * the second cell should contain 'subjectname' for the server to parse
	 * @param subjectname the subject to whom the search is for.
	 */
	public void GetSubjectIDfromSubjectCourses(Object subjectname) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("getSubjectID",
				"SELECT subjectid FROM projecton.subjectcourses where ( `subjectname` = '" + subjectname + "' );"));

		try {
			sendToServer((Object) list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////////////////
	/////////////////////// CLIENT NATIVE /////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * This method overrites super method that handles what happans when connection
	 * is closed
	 * with the server.
	 */
	protected void connectionClosed() {
		System.out.println("Connection Lost, press login to regain connection.");
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			sendToServer((Object) this.getInetAddress());
			closeConnection();
		} catch (IOException e) {
		}
		// System.exit(0);
	}

	// clear client data
	public static void resetClientData() {
		user = new User();
		LecturerController.subjectsList = new ArrayList<String>();
		LecturerController.questions = new ArrayList<QuestionModel>();
	}

}