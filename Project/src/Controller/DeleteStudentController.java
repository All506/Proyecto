/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularDoublyLinkList;
import Domain.ListException;
import Domain.Node;
import Domain.SinglyLinkList;
import Objects.Course;
import Objects.Enrollment;
import Objects.Student;
import XML.FileXML;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class DeleteStudentController implements Initializable {
    
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ComboBox<Integer> cmbCareerID;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtLastName;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private TextField txtFirstName;
    private Button btnModify;
    @FXML
    private Button btnClean;
    @FXML
    private ComboBox<String> cmbId;
    
    SinglyLinkList students = new SinglyLinkList();
    @FXML
    private Button btnDelete;
    @FXML
    private BorderPane bpRoot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.btnDelete.setVisible(false);
            students = Util.Utility.getListStudents();
            
            for (int i = 1; i <= students.size(); i++) {
                Student s = (Student) students.getNode(i).getData();
                cmbId.getItems().add(s.getStudentID());
            }
            cmbCareerID.setValue(1);
            
        } catch (ListException ex) {
            Logger.getLogger(EditStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnClean(ActionEvent event) throws ListException {
        reloadCombo();
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtId.setText("");
        txtAddress.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        btnDelete.setVisible(false);
    }
    
    @FXML
    private void cmbId(ActionEvent event) throws ListException {
        
        if(cmbId.getValue()!=null){
       
            btnDelete.setVisible(true);
            Student temp = new Student(0, 0, cmbId.getValue().toString(), "", "", "", "", "", null);
            if (students.contains(temp)) {
                temp = look4student(temp);
                this.txtAddress.setText(temp.getAddress());
                this.txtEmail.setText(temp.getEmail());
                this.txtFirstName.setText(temp.getFirstname());
                this.txtLastName.setText(temp.getLastname());
                this.txtPhoneNumber.setText(temp.getPhoneNumber());
                this.txtId.setText(String.valueOf(temp.getId()));
                //Convertir Date a LocalDate
                LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(temp.getBirthday()));
                this.dpBirthday.setValue(localDate);
            }
        }
    }
    
    @FXML
    private void btnDelete(ActionEvent event) throws ListException, IOException {
        
        //Convertir Date a LocalDate
        java.util.Date d = java.sql.Date.valueOf(dpBirthday.getValue());
        //Se elmina y luego se anhade

        Student std = new Student(Integer.parseInt(txtId.getText()), this.cmbCareerID.getValue(),
                String.valueOf(this.cmbId.getValue()), this.txtLastName.getText(), this.txtFirstName.getText(),
                this.txtPhoneNumber.getText(), this.txtEmail.getText(), this.txtAddress.getText(), d);
        
        if (lookEnrollment(std.getStudentID())) {
            Util.Utility.getListStudents().remove(std);
            if(Util.Utility.getListStudents().isEmpty()){
                bpRoot.setCenter(null);
            }
            callAlert("notification", "Notification", "Student has been deleted");
            students = Util.Utility.getListStudents();
        } else {
            callAlert("alert", "Student Not Deleted", "Student cannot been deleted");
        }
        
        btnClean(event);
        if(!Util.Utility.getListStudents().isEmpty()){
            reloadCombo();
        }
        
    }
    
    private boolean lookEnrollment(String studentID) throws ListException {
        boolean condition = true;
        
        CircularDoublyLinkList list = Util.Utility.getListEnrollment();
        if(list.isEmpty()){
            return condition;
        }
        
        for (int i = 1; i <= list.size(); i++) {
            Enrollment enrollment = (Enrollment) list.getNode(i).data;
            if (enrollment.getStudentID().equalsIgnoreCase(studentID)) {
                condition = false;
            }
        }
        return condition;
    }
    
    private Student look4student(Student temp) throws ListException {
        //Busca la información de un estudiante en la lista y devuelve al estudiante
        if (!students.isEmpty()) { //Si la lista esta vacia
            Node aux = students.getNode(1);
            while (aux != null) { //Va a analizar incluso el primer elemento para ver si es igual al objeto
                Student s = (Student) aux.data;
                if (s.getStudentID().equalsIgnoreCase(temp.getStudentID())) {
                    return (Student) aux.data;
                } else {
                    aux = aux.next;
                }
                
            }
            
        }
        return null;
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
    
    private void reloadCombo() throws ListException {
        //Reinicia valores del combo box
        for (int i = 0; i <= cmbId.getItems().size(); i++) {
            this.cmbId.getItems().clear();
        }
        
        students = Util.Utility.getListStudents(); //Se recarga la lista de estudiantes
        if(!students.isEmpty())
        for (int i = 1; i <= students.size(); i++) {
            Student s = (Student) students.getNode(i).getData();
            cmbId.getItems().add(s.getStudentID());
        }
    }
    
}
