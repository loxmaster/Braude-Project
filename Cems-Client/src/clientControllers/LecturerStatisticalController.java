package clientControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class LecturerStatisticalController extends BasicController {

	@FXML
	private TableView<?> table;

	@FXML
	private Button backToLecturer;

	@FXML
	void backtoLecturerMain(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		LecturerOptionsController lecturerOptionsController = (LecturerOptionsController) openScreen(
				"/clientFXMLS/Lecturer1.fxml", "CEMS System - Lecturer", event);
		lecturerOptionsController.openScreen(null, null, event);
	}

	@FXML
	void backtoStatistical(ActionEvent event) {
		// return to table view from 'Edit Question'
		// ((Node) event.getSource()).getScene().getWindow().hide();
		LecturerStatisticalController lecturerStatisticalController = (LecturerStatisticalController) openScreen(
				"/clientFXMLS/LecturerStatistical.fxml", "CEMS System - Lecturer - Statistical View", event);
		lecturerStatisticalController.openScreen("/clientFXMLS/LecturerStatistical.fxml","CEMS System - Lecturer - Statistical View" , event);

		// FIXME fix this load table thingy
		// lecturerStatisticalController.loadTable();
	}

}
