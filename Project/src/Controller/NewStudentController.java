/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Domain.Student;
import XML.LogicStudent;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class NewStudentController implements Initializable {

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtStudentID;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private ComboBox<String> cmbCareerID;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private Button btnAdd;

    //Lista necesaria para almacenar estudiantes
    private SinglyLinkList students;

    //Variables para almacenar el estudiante
    int id = 0;
    double phoneNumber = 0;
    Date d = null;
    @FXML
    private Button btnClean;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        students = new SinglyLinkList();
        String temporal = "";
        //Para cargar un combobox
        this.loadComboBoxCareers();

    }

    @FXML
    private void btnAdd(ActionEvent event) {
        LogicStudent logStudent = new LogicStudent();
        try {
            id = Integer.parseInt(txtID.getText());
            phoneNumber = parseDouble(txtPhoneNumber.getText());
            //VERIFICAR SI HAY ESPACIOS VACIOS

            java.util.Date d = java.sql.Date.valueOf(dpBirthday.getValue());

            String[] temporal = cmbCareerID.getValue().split("-"); //Hace split del combobox y pasa el resultado del id de carrera seleccionado
            Student std = new Student(id, Integer.parseInt(temporal[0]), txtStudentID.getText(), txtLastname.getText(), txtFirstname.getText(),
                     txtPhoneNumber.getText(), txtEmail.getText(), txtAddress.getText(), d);
            try {
                if (Util.Utility.setListStudent(std)) {
                    callAlert("notification", "Notification", "User has been registered");
                    logStudent.writeStudent(std);
                    btnClean(event);
                } else {
                    callAlert("alert", "Error", "User already exist");
                }
            } catch (ListException ex) {
                Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            callAlert("alert", "Error", "Only numerical numbers can be written. \n Please check the spaces");
        }
    }

    @FXML
    private void btnClean(ActionEvent event) {
        txtID.setText("");
        cmbCareerID.setValue("");
        txtStudentID.setText("");
        txtLastname.setText("");
        txtFirstname.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        loadComboBoxCareers();
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
    
    public void loadComboBoxCareers(){
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career)tempCareers.getNode(i).getData(); 
                temporal = c.getId()+"-"+c.getDescription();
                this.cmbCareerID.getItems().add(temporal);
                        }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbCareerID.setValue(temporal);
    }

}
