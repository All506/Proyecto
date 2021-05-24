/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Objects.Student;
import java.net.URL;
import java.util.ResourceBundle;
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
    private ComboBox<?> cmbPeriod;
    @FXML
    private ComboBox<?> cmbCourse;
    @FXML
    private ComboBox<?> cmbSchedule;
    @FXML
    private Button btnEnroll;
    @FXML
    private Button btnCancel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Student s= Util.Utility.getUserStudent();

    txfStudentID.setText(s.getStudentID());
    txfPersID.setText(""+s.getId());
    txfFirstName.setText(s.getFirstname());
    txfLastName.setText(s.getLastname());
    txfAddress.setText(s.getAddress());
    txfBirthday.setText(Util.Utility.dateFormat(s.getBirthday2()));
    txfEmail.setText(s.getEmail());
    txfPhoneNumber.setText(s.getPhoneNumber());
  
    }

    @FXML
    private void btnEnroll(ActionEvent event) {
    }

    @FXML
    private void btnCancel(ActionEvent event) {
    }

    
    
 }    
    

