/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Domain.DoublyLinkList;
import Domain.ListException;
import Objects.Career;
import Objects.Course;
import Objects.Student;
import XML.FileXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ComboBox<String> cmbAvailableCourses;
    @FXML
    private Text txtCourse;
    @FXML
    private Text txtConfirmation;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnShowCourses;

    String sendablecourseID = "";
    @FXML
    private Button btnCancelThat;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadComboBoxCareers();
        
    }    

    @FXML
    private void cmbAvailableCourses(MouseEvent event) {
        this.btnShowCourses.setDisable(false);
    }

    
    
    public void clean(){
        this.txtCareer.setVisible(false);
        this.txtConfirmation.setVisible(false);
        this.txtCourse.setVisible(false);
        this.cmbAvailableCourses.setVisible(false);
        this.cmbCareer.setVisible(false);
        this.btnConfirm.setVisible(false);
        this.btnDelete.setVisible(false);
        this.btnShowCourses.setVisible(false);
        this.btnCancelThat.setVisible(false);
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
        this.cmbCareer.setValue(null);
    }
    
    public void loadComboBoxCourses(int careerID){
        CircularDoublyLinkList tempCourses = new CircularDoublyLinkList();
        tempCourses = Util.Utility.getListCourse();
        String temporal = "";
        
        if(!Util.Utility.getListCourse().isEmpty()){
        try {
            for (int i = 1; i <= tempCourses.size(); i++) {
                Course c = (Course)tempCourses.getNode(i).getData();
                if(c.getCareerId()==careerID){
                    temporal = c.getId()+"-"+c.getName();
                    this.cmbAvailableCourses.getItems().add(temporal);
                }
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
             callAlert("alert", "Error 404", "There are no courses registered");
        }
    }

    @FXML
    private void btnShowCourses(ActionEvent event) {
        int x = Integer.parseInt(Util.Utility.getIDofString(this.cmbCareer.getValue()));
        this.cmbAvailableCourses.getItems().clear();
        loadComboBoxCourses(x);
        this.cmbAvailableCourses.setVisible(true);
        this.txtCourse.setVisible(true); 
        this.btnDelete.setDisable(true);
        this.btnDelete.setVisible(true);
        this.btnShowCourses.setDisable(true);
    }
    
    @FXML
    private void btnDelete(ActionEvent event) {
        try{
        clean();
        this.txtConfirmation.setVisible(true);
        this.txtConfirmation.setText("Do you want to delete the course '" + this.cmbAvailableCourses.getValue() + "'?");
        this.btnConfirm.setVisible(true);
        this.btnCancelThat.setVisible(true);
        }catch(NullPointerException npe){
            callAlert("alert", "No courses selected", "Choose a course to delete!");
        }
    }
    
     @FXML
    private void btnConfirm(ActionEvent event) {
         CircularDoublyLinkList listTodelete = Util.Utility.getListCourse();
         CircularDoublyLinkList listToSend = new CircularDoublyLinkList();
        try {
            for (int i = listTodelete.indexOf(listTodelete.getFirst()); i <= listTodelete.size(); i++) {
                Course f = (Course)listTodelete.getNode(i).data;
                String sample = f.getId();
                if(!Util.Utility.getIDofString(this.cmbAvailableCourses.getValue()).equals(sample)){
                    listToSend.add(f);
                }
            }
            Util.Utility.replaceListCourse(listToSend);
        } catch (ListException ex) {
            callAlert("alert", "Error", "We couldn't delete the course");
        }
         callAlert("alert", "The course has been deleted", "The course selected \n has been deleted from the files");
         clean();
         this.txtCareer.setVisible(true);
         this.cmbCareer.setValue(null);
         this.cmbAvailableCourses.setValue(null);
         this.btnShowCourses.setVisible(true);
         this.cmbCareer.setVisible(true);          
    }
    
    private void callAlert(String fxmlName, String title, String text) {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + fxmlName + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            AlertController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Alerta");
            //Se le asigna la información a la controladora
            controller.setText(title, text);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    @FXML
    private void btnCancelThat(ActionEvent event) {
        clean();
        this.txtCareer.setVisible(true);
        this.cmbCareer.setValue(null);
        this.cmbCareer.setVisible(true);
        this.btnShowCourses.setVisible(true);
    }

    @FXML
    private void AvailableCoursesOnMouseClicked(MouseEvent event) {
        this.btnDelete.setDisable(false);
    }
}
