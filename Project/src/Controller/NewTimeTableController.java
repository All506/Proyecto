/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.DoublyLinkList;
import Domain.ListException;
import Objects.Career;
import Objects.Course;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class NewTimeTableController implements Initializable {

    @FXML
    private ComboBox<String> cmbCourses;
    @FXML
    private Spinner<String> spnDay1;
    @FXML
    private Spinner<?> spnEnd2;
    @FXML
    private Spinner<Integer> spnStart2;
    @FXML
    private Spinner<String> spnDay2;
    @FXML
    private Spinner<Integer> spnStart1;
    @FXML
    private Spinner<?> spnEnd1;
    @FXML
    private Button btnClean;
    @FXML
    private Button btnValidSave;
    @FXML
    private ComboBox<String> cmbPeriod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadComboBoxCareers();
        this.loadSpinnerDays();
        this.loadComboBoxPeriod();
        this.loadSpinnerStart();
    }    

       public void loadComboBoxCareers(){
        //Para cargar un combobox
        CircularLinkList tempCourses = new CircularLinkList();
        tempCourses = Util.Utility.getListCourse();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCourses.size(); i++) {
                Course c = (Course)tempCourses.getNode(i).getData(); 
                temporal = c.getId()+"-"+c.getName();
                this.cmbCourses.getItems().add(temporal);
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbCourses.setValue(temporal);
    }

      public void loadSpinnerDays(){
      
      ObservableList<String> days = FXCollections.observableArrayList(
            "Saturday" ,"Friday" ,"Thursday" ,"Wednesday" ,"Tuesday" ,"Monday");
      
      // Value factory.
       SpinnerValueFactory<String> valueFactory = //
               new SpinnerValueFactory.ListSpinnerValueFactory<String>(days);
       SpinnerValueFactory<String> valueFactory2 = //
               new SpinnerValueFactory.ListSpinnerValueFactory<String>(days);
       
       valueFactory.setValue("Monday");
       valueFactory2.setValue("Monday");
    
 
       spnDay1.setValueFactory(valueFactory);
       spnDay2.setValueFactory(valueFactory2);
      
      } 
      
      public void loadSpinnerStart(){
      
      ObservableList<Integer> start = FXCollections.observableArrayList(
            7,8,9,10,11,12,13,14,15,16,17,18,19,20,21);
      
      // Value factory.
       SpinnerValueFactory<Integer> valueFactory = //
               new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(start);
       SpinnerValueFactory<Integer> valueFactory2 = //
               new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(start);
       
       valueFactory.setValue(7);
       valueFactory2.setValue(7);
    
 
       spnStart1.setValueFactory(valueFactory);
       spnStart2.setValueFactory(valueFactory2);
      
      }
      
      public void loadComboBoxPeriod(){
        //Para cargar un combobox
            for (int i = 2020; i <= 2040; i++) {
                this.cmbPeriod.getItems().add("1-"+i);
                this.cmbPeriod.getItems().add("2-"+i);
                this.cmbPeriod.getItems().add("3-"+i);
            }
        cmbPeriod.setValue("1-2020");
    }

    @FXML
    private void btnClean(ActionEvent event) {
    }

    @FXML
    private void btnValidSave(ActionEvent event) {
    }

    
}
