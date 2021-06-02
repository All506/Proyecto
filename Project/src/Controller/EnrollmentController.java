/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Objects.Course;
import Objects.Enrollment;
import Objects.Student;
import Objects.TimeTable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class EnrollmentController implements Initializable {

    @FXML
    private Text txt;
    @FXML
    private TextField txfStudentID;
    @FXML
    private TextField txfLastName;
    @FXML
    private TextField txfFirstName;

    @FXML
    private TextField txfPersID;
    @FXML
    private TextField txfBirthday;
    @FXML
    private TextField txfPhoneNumber;
    @FXML
    private TextField txfEmail;
    @FXML
    private TextField txfAddress;
    @FXML
    private TextField txfCarrer;
    @FXML
    private ComboBox<String> cmbPeriod;
    @FXML
    private ComboBox<String> cmbCourse;
    @FXML
    private ComboBox<String> cmbSchedule;
    @FXML
    private Button btnEnroll;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Student s = Util.Utility.getUserStudent();

            txfStudentID.setText(s.getStudentID());
            txfPersID.setText("" + s.getId());
            txfFirstName.setText(s.getFirstname());
            txfLastName.setText(s.getLastname());
            txfAddress.setText(s.getAddress());
            txfBirthday.setText(Util.Utility.dateFormat(s.getBirthday2()));
            txfEmail.setText(s.getEmail());
            txfPhoneNumber.setText(s.getPhoneNumber());
            loadComboBoxCourses("" + s.getCareerID());
            txfCarrer.setText(Util.Utility.getCarrerByID("" + s.getCareerID()).getDescription());
            loadComboBoxPeriod();
//            loadComboBoxSchedule(Util.Utility.getIDofString(cmbCourse.getValue()), cmbPeriod.getValue());

        } catch (ListException ex) {
            Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadComboBoxCourses(String id) throws ListException {
        //Para cargar un combobox
        CircularLinkList tempCourses = Util.Utility.getCoursesByCarrerID(id);

        if (!tempCourses.isEmpty()) {
            String temporal = "";

            try {
                for (int i = 1; i <= tempCourses.size(); i++) {
                    Course c = (Course) tempCourses.getNode(i).getData();
                    temporal = c.getId() + "-" + c.getName();
                    this.cmbCourse.getItems().add(temporal);
                }
            } catch (ListException ex) {
                Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            cmbCourse.setValue(temporal);
            cmbCourse.getSelectionModel().select("Courses");

        }
    }

    public void loadComboBoxSchedule(String id, String period) throws ListException {
        //Para cargar un combobox
        TimeTable t = Util.Utility.getScheduleByCourseID(id, period);
        cmbSchedule.getItems().clear();
        if (t != null) {
            cmbSchedule.getItems().add(t.getSchedule1());
            cmbSchedule.getItems().add(t.getSchedule2());

//        cmbPeriod.setValue("1-2020");
            cmbSchedule.getSelectionModel().select("Schedule");
        } else {
            cmbSchedule.getItems().add("Not defined");
            cmbSchedule.getSelectionModel().select("Schedule");
        }
    }

    public void loadComboBoxPeriod() {
        java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
        //Para cargar un combobox
        String period = "";
        
        switch((int)d.getMonth()){
            case 1: case 2: case 3: case 0:
                period+=1;
                break;
            case 4: case 5: case 6: case 7:
                period+=2;
                break;
            case 8: case 9: case 10: case 11:
                period+=3;
                break;
        }
        period+="-"+(d.getYear()+1900);
        this.cmbPeriod.getItems().add(period);
        cmbPeriod.getSelectionModel().select("Period");
    }

    @FXML
    private void btnEnroll(ActionEvent event) {
        try {
            java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
            int temp = Util.Utility.getLastEnroll();
            String[] courseId = cmbCourse.getValue().split("-");
            Enrollment newEnroll = new Enrollment(temp+1, d, this.txfStudentID.getText(), courseId[0], cmbSchedule.getValue());
            System.out.println("El enroll a registrar es: " + newEnroll.toString());
            Util.Utility.setListEnrollment(newEnroll);
            System.out.println(Util.Utility.getListEnrollment().toString());
        } catch (ListException ex) {
            Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCancel(ActionEvent event) {
    }

    @FXML
    private void loadComboboxSchedules(ActionEvent event) throws ListException {
        if (!cmbCourse.getValue().equals("Courses") && !cmbPeriod.getValue().equals("Period")) {
//            
            loadComboBoxSchedule(Util.Utility.getIDofString(cmbCourse.getValue()), cmbPeriod.getValue());
        }

    }

}
