package clientControllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import clientHandlers.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Statistics;
import logic.Test;

public class LecturerStatisticalController extends BasicController {

	@FXML
	private TableColumn<Statistics, String> Average_Score;

	@FXML
	private TableColumn<Statistics, String> Course;

	@FXML
	private TableColumn<Statistics, String> Date;

	@FXML
	private TableColumn<Statistics, String> Distribution;

	@FXML
	private TableColumn<Statistics, String> TestID;

	@FXML
	private TableColumn<Statistics, String> Total_Number_of_Students;

	@FXML
	private Button exitbutton;

	@FXML
	private Button logo;

	@FXML
	private TableView<Statistics> table;

	@FXML
	private ComboBox<?> testid_combo;

	@FXML
	private TextField course_fillter;

	@FXML
	private TextField date_filler;

	private static ArrayList<Test> completedTestsList;
	private static ArrayList<Statistics> diffExamsStats;
	private static ArrayList<String> SubjectCourseLec;

	// TODO need to find a way to populate the pie chart and get rid on the new
	// "PieChart.Data(Testing, 50)"
	// * need to add a Sleep option

	public void load() {
		ClientUI.chat.getcompletedTestsForLecturerList();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TestID.setCellValueFactory(new PropertyValueFactory<>("TestID"));
		Course.setCellValueFactory(new PropertyValueFactory<>("Course"));
		Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
		Total_Number_of_Students.setCellValueFactory(new PropertyValueFactory<>("Total_Number_of_Students"));
		Average_Score.setCellValueFactory(new PropertyValueFactory<>("Average"));
		Distribution.setCellValueFactory(new PropertyValueFactory<>("Distribution"));

		int Total_Number_of_Students = 0, Grade_Total_Sum = 0, middle, int_for_medians = 0;
		boolean flag = false;
		diffExamsStats= new ArrayList<>();
		if (completedTestsList != null) {
			Statistics stat = new Statistics(completedTestsList.get(0).getId());
			Grade_Total_Sum += Integer.parseInt(completedTestsList.get(0).getGrade());
			Total_Number_of_Students++;
			stat.setHighes(Integer.parseInt(completedTestsList.get(0).getGrade()));
			stat.setLowest(Integer.parseInt(completedTestsList.get(0).getGrade()));
			stat.setDate(completedTestsList.get(0).getDateString());
			ClientUI.chat.getCourseForTestLec(stat.getTestID());
			stat.setCourse(SubjectCourseLec.get(2));

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
						stat.setCourse(SubjectCourseLec.get(2));
						stat.setDate(test.getDateString());
					}
				} else
					flag = true;
			}
		}

		ObservableList<Statistics> listToAdd = FXCollections.observableArrayList(diffExamsStats);
		table.setItems(listToAdd);

	}

	@FXML
	void backtoLecturerMain(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		backToLecturer(event);
	}

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
