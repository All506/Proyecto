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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class NewCourseController implements Initializable {

    @FXML
    private Button btnClean;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCredits;
    @FXML
    private ComboBox<String> cmbCarrerId;
    @FXML
    private TextField txtId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadComboBoxCourse();
        this.btnAdd.setDisable(true);
    }    


    @FXML
    private void btnClean(ActionEvent event) {
        this.txtName.setText("");
        this.txtId.setText("");
        this.txtCredits.setText("");
        this.cmbCarrerId.setValue(null);
        
    }
    
    public void loadComboBoxCourse(){
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career)tempCareers.getNode(i).getData(); 
                temporal = c.getId()+"-"+c.getDescription();
                this.cmbCarrerId.getItems().add(temporal);
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCarrerId.setValue(temporal);
    }

    @FXML
    private void btnAdd(ActionEvent event) throws ListException {      
            
            //Add the course
            int i = 0;
            String x = "";
            while(!("-").contains(""+this.cmbCarrerId.getValue().charAt(i))){
                x += this.cmbCarrerId.getValue().charAt(i);
                i++;
            }
            Course crse = new Course(this.txtId.getText(),this.txtName.getText(),Integer.parseInt(this.txtCredits.getText()),Integer.parseInt(x));
            Util.Utility.setListCourse(crse);
            System.out.println(Util.Utility.getListCourse());
    }   

    @FXML
    private void keyPressed(KeyEvent event) { //Es key Released pero por conveniencia el nombre es keyPressed
        if(this.txtName.getText().length()!=0 && this.txtId.getText().length()!=0 && this.txtCredits.getText().length()!=0){
                this.btnAdd.setDisable(false);
    }else{
          this.btnAdd.setDisable(true);
        }
}
}
