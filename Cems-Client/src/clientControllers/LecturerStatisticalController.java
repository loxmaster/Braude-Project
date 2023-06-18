package clientControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.util.Callback;
import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Statistics;
import logic.Test;
import javafx.scene.control.TableCell;

/**
 * This controller handles the statistics display for the lecturer.
 * It extends from the BasicController class.
 */
public class LecturerStatisticalController extends BasicController {
	// Define columns for the statistics table
	@FXML
	private TableColumn<Statistics, String> Average_Score;

	@FXML
	private TableColumn<Statistics, String> Course;

	@FXML
	private TableColumn<Statistics, String> Date;

	@FXML
	private TableColumn<Statistics, Void> Distribution;

	@FXML
	private TableColumn<Statistics, String> TestID;

	@FXML
	private TableColumn<Statistics, String> Total_Number_of_Students;
	// Define other UI elements
	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TableView<Statistics> table;

	@FXML
	private ComboBox<String> testid_combo;

	@FXML
	private TextField course_fillter;

	@FXML
	private Label live_time;

	@FXML
	void initialize() {
		// Start the clock
		Timenow(live_time);
	}

	// Store the list of completed tests and exam statistics
	private static ArrayList<Test> completedTestsList;
	private static ArrayList<Statistics> diffExamsStats;
	private static ArrayList<String> SubjectCourseLec;

	/**
	 * This function loads the statistics from the server and populates the table.
	 */
	public void load() {
		// Request completed tests for the lecturer
		ClientUI.chat.getcompletedTestsForLecturerList();
		// Wait for the response
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Set the cell value factories for each column
		// This tells JavaFX how to populate each cell in the column
		// ... Cell value factories ...

		// Initialize some variables for calculations
		TestID.setCellValueFactory(new PropertyValueFactory<>("TestID"));
		Course.setCellValueFactory(new PropertyValueFactory<>("Course"));
		Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
		Total_Number_of_Students.setCellValueFactory(new PropertyValueFactory<>("Total_Number_of_Students"));
		Average_Score.setCellValueFactory(new PropertyValueFactory<>("Average"));

		int Total_Number_of_Students = 0, Grade_Total_Sum = 0, middle, int_for_medians = 0;
		boolean flag = false;
		diffExamsStats = new ArrayList<>();
		// Check if there are any completed tests

		if (completedTestsList != null) {
			// Create a new Statistics object for the first test
			Statistics stat = new Statistics(completedTestsList.get(0).getId());
			Grade_Total_Sum += Integer.parseInt(completedTestsList.get(0).getGrade());
			Total_Number_of_Students++;
			stat.setHighes(Integer.parseInt(completedTestsList.get(0).getGrade()));
			stat.setLowest(Integer.parseInt(completedTestsList.get(0).getGrade()));
			stat.setDate(completedTestsList.get(0).getDateString());
			ClientUI.chat.getCourseForTestLec(stat.getTestID());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stat.setCourse(SubjectCourseLec.get(2));
			// Iterate over each test
			for (Test test : completedTestsList) {
				if (flag) {
					if (stat.getTestID().equals(test.getId())) {
						Grade_Total_Sum += Integer.parseInt(test.getGrade());
						if (stat.getHighes() < Integer.parseInt(test.getGrade())) {
							stat.setHighes(Integer.parseInt(test.getGrade()));
						}
						if (stat.getLowest() > Integer.parseInt(test.getGrade())) {
							stat.setLowest(Integer.parseInt(test.getGrade()));
						}
						Total_Number_of_Students++;
					} else {
						stat.setAverage(Grade_Total_Sum / Total_Number_of_Students);
						middle = (Total_Number_of_Students / 2) + int_for_medians;
						int_for_medians += Total_Number_of_Students;
						if (Total_Number_of_Students % 2 == 0) {
							stat.setMedian(
									String.valueOf((Integer.parseInt(completedTestsList.get(middle - 1).getGrade())
											+ Integer.parseInt(completedTestsList.get(middle).getGrade())) / 2.0));
						} else {
							stat.setMedian(completedTestsList.get(middle).getGrade());
						}

						stat.setTotal_Number_of_Students(Total_Number_of_Students);
						diffExamsStats.add(stat);
						stat = new Statistics(test.getId());
						Grade_Total_Sum = Integer.parseInt(test.getGrade());
						stat.setHighes(Integer.parseInt(test.getGrade()));
						stat.setLowest(Integer.parseInt(test.getGrade()));
						Total_Number_of_Students = 1;
						ClientUI.chat.getCourseForTestLec(stat.getTestID());
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						stat.setCourse(SubjectCourseLec.get(2));
						stat.setDate(test.getDateString());
					}
				} else
					flag = true;
			}
			// After all tests are processed, update the final Statistics object and add it
			// to the list

			stat.setAverage(Grade_Total_Sum / Total_Number_of_Students);
			middle = (Total_Number_of_Students / 2) + int_for_medians;
			int_for_medians += Total_Number_of_Students;
			if (Total_Number_of_Students % 2 == 0) {
				stat.setMedian(
						String.valueOf((Integer.parseInt(completedTestsList.get(middle - 1).getGrade())
								+ Integer.parseInt(completedTestsList.get(middle).getGrade())) / 2.0));
			} else {
				stat.setMedian(completedTestsList.get(middle).getGrade());
			}
			stat.setTotal_Number_of_Students(Total_Number_of_Students);
			diffExamsStats.add(stat);

		}

		// Extract all test IDs for the combo box
		Set<String> testIds = new HashSet<>();
		for (Statistics stat : diffExamsStats) {
			testIds.add(stat.getTestID());
		}
		testid_combo.getItems().addAll(testIds);

		// Set the cell factory for the Distribution column
		// This tells JavaFX how to create each cell in the column
		Distribution.setCellFactory(new Callback<TableColumn<Statistics, Void>, TableCell<Statistics, Void>>() {
			@Override
			public TableCell<Statistics, Void> call(final TableColumn<Statistics, Void> param) {
				final TableCell<Statistics, Void> cell = new TableCell<Statistics, Void>() {

					private final Button btn = new Button("Show Distribution");

					{
						btn.setOnAction((ActionEvent event) -> {
							// Add the data to the table
							Statistics data = getTableView().getItems().get(getIndex());
							try {
								FXMLLoader loader = new FXMLLoader(
										getClass().getResource("/clientFXMLS/LecturerStatistical_GraphView.fxml"));
								Parent root = loader.load();

								GraphController graphController = loader.getController();
								graphController.setData(data, completedTestsList);

								Stage stage = (Stage) btn.getScene().getWindow();
								stage.setScene(new Scene(root));
								stage.show();

							} catch (IOException e) {
								e.printStackTrace();
							}
							System.out.println("selectedData: " + data);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		});

		ObservableList<Statistics> listToAdd = FXCollections.observableArrayList(diffExamsStats);
		table.setItems(listToAdd);
		// Do not forget to add Distribution column to your table
		if (!table.getColumns().contains(Distribution)) {
			table.getColumns().add(Distribution);

		}

		// Create a FilteredList wrapping the ObservableList
		FilteredList<Statistics> filteredData = new FilteredList<>(listToAdd, p -> true);

		// Set the filter Predicate whenever the filter changes.
		testid_combo.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(stat -> {
				// If filter text is empty, display all statistics.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare TestID of every statistics with filter.
				return stat.getTestID().equals(newValue);
			});
		});

		course_fillter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(stat -> {
				// If filter text is empty, display all statistics.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare course name of every statistics with filter text.
				return stat.getCourse().toLowerCase().contains(newValue.toLowerCase());
			});
		});

		// Wrap the FilteredList in a SortedList.
		SortedList<Statistics> sortedData = new SortedList<>(filteredData);

		// Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		// Add sorted and filtered data to the table
		table.setItems(sortedData);
	}

	/**
	 * This function handles the action of clicking the "Back to Lecturer Main"
	 * button.
	 */
	@FXML
	void backtoLecturerMain(ActionEvent event) {
		backToLecturer(event);
	}

	// Getter and setter methods for the completed tests and the subjects/courses
	// list

	public static ArrayList<Test> getcompletedTestsForLecturerList() {
		return completedTestsList;
	}

	public static void setcompletedTestsForLecturerList(ArrayList<Test> completedTests) {
		completedTestsList = completedTests;
	}

	public static void setSubjectsCoursesListLec(ArrayList<String> listToAdd) {
		SubjectCourseLec = listToAdd;
	}

}
