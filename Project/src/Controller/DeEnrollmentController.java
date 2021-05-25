/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Objects.Course;
import Objects.Student;
import Objects.TimeTable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class DeEnrollmentController implements Initializable {

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
    private TableView<?> tblCourses;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colSchedule;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Student s= Util.Utility.getUserStudent();
            
            txfStudentID.setText(s.getStudentID());
            txfPersID.setText(""+s.getId());
            txfFirstName.setText(s.getFirstname());
            txfLastName.setText(s.getLastname());
            txfAddress.setText(s.getAddress());
            txfBirthday.setText(Util.Utility.dateFormat(s.getBirthday2()));
            txfEmail.setText(s.getEmail());
            txfPhoneNumber.setText(s.getPhoneNumber());
            txfCarrer.setText(Util.Utility.getCarrerByID(""+s.getCareerID()).getDescription());   
        } catch (ListException ex) {
            Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
      
    
 }    
    

