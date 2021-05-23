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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class DeleteCourseController implements Initializable {

    @FXML
    private ComboBox<String> cmbCareer;
    @FXML
    private Text txtCareer;
    @FXML
    private ComboBox<?> cmbAvailableCourses;
    @FXML
    private Text txtCourse;
    @FXML
    private Text txtConfirmation;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnConfirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cmbAvailableCourses(MouseEvent event) {
    }

    @FXML
    private void btnDelete(ActionEvent event) {
    }

    @FXML
    private void btnConfirm(ActionEvent event) {
    }
    
    public void clean(){
        this.txtCareer.setVisible(false);
        this.txtConfirmation.setVisible(false);
        this.txtCourse.setVisible(false);
        this.cmbAvailableCourses.setVisible(false);
        this.cmbCareer.setVisible(false);
        this.btnConfirm.setVisible(false);
        this.btnDelete.setVisible(false);
    }
    
    public void loadComboBoxCareers(){
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career)tempCareers.getNode(i).getData(); 
                temporal = c.getId()+"-"+c.getDescription();
                this.cmbCareer.getItems().add(temporal);
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCareer.setValue(temporal);
        this.cmbCareer.setValue(null);
    }
    
    public void loadComboBoxCourses(String career){
        //Para cargar un combobox
        CircularLinkList tempCourses = new CircularLinkList();
        tempCourses = Util.Utility.getListCourse();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCourses.size(); i++) {
                Career c = (Career)tempCourses.getNode(i).getData(); 
                temporal = c.getId()+"-"+c.getDescription();
                this.cmbCareer.getItems().add(temporal);
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCareer.setValue(temporal);
        this.cmbCareer.setValue(null);
    }
}
