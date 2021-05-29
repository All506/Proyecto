/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Enrollment;
import Objects.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
    private TableView<List<String>> tblCourses;
    @FXML
    private TableColumn<List<String>, String> colID;
    @FXML
    private TableColumn<List<String>, String> colDescription;
    @FXML
    private TableColumn<List<String>, String> colSchedule;
    @FXML
    private Button btnDeEnroll;
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
            loadTable();
        } catch (ListException ex) {
            Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        colID.setOnEditStart(data -> {
            
          btnDeEnroll.setVisible(true);
          btnDeEnroll.setText("De-enroll course: "+data.getOldValue());
            
        });
        
    
    }
      
    public void loadTable() throws ListException{
    
    colID.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(0));
        }
    });
    
    colDescription.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(1));
        }
    });
    
    colSchedule.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(2));
        }
    });
        
       
    tblCourses.setItems(getData());
    };
    
    public ObservableList<List<String>> getData() throws ListException{
   
    SinglyLinkList list = Util.Utility.getEnrollmentOfStudentId();
        
    final ObservableList<List<String>> data = FXCollections.observableArrayList();
    if(!list.isEmpty()){
        
        for (int i = 1; i <= list.size(); i++) {
            Enrollment e = (Enrollment)list.getNode(i).data;
            List<String> arrayList = new ArrayList<>();
            arrayList.add(e.getCourseID());
           
            arrayList.add(Util.Utility.getCourseByID(e.getCourseID()).getName());
            arrayList.add(e.getSchedule());
            data.add(arrayList);
            
        }
    
    }
       
        
    return data;
    }

    @FXML
    private void btnDeEnroll(ActionEvent event) {
    }
    
 }    
    

