/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class DeEnrollmentTableController implements Initializable {

    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<?, ?> colStudID;
    @FXML
    private TableColumn<?, ?> colLastName;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colPersID;
    private Text txtTitle;
    
    SinglyLinkList students = new SinglyLinkList();
    @FXML
    private BorderPane bpRoot;

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        
        
        students = Util.Utility.getListStudents();

        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tblStudents.getItems().size(); i++) {
            this.tblStudents.getItems().clear();
        }

        if (students.isEmpty()) {
            callAlert("alert", "Error", "There are no registered students");
        } else {
            try {
                Student std = new Student((Student) students.getNode(1).getData());

                for (int i = 1; i <= students.size(); i++) {
                    std = new Student((Student) students.getNode(i).getData());
                    tblStudents.getItems().add(std);
                    this.colPersID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    this.colStudID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                    this.colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
                    this.colName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
                }
            } catch (ListException ex) {
                Logger.getLogger(ShowStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        
        colStudID.setOnEditStart(data -> {
            try {
                Student aux=(Student)(data.getRowValue());
                Util.Utility.setUserStudent(aux);
                
                
                if(Util.Utility.getCoursesByCarrerID(""+aux.getCareerID()).isEmpty()){
                     callAlert("alert", "Attention!", "This student's career doesn't have\ncourses with defined schedules");
                }else  
                    loadPage("/UI/DeEnrollment");
            } catch (ListException ex) {
                Logger.getLogger(EnrollmentTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
    } 
   
     private void loadPage(String pageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(pageName + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(EnrollmentTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bpRoot.setCenter(root);
    } // Fin método Load Page
  
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
 }    
