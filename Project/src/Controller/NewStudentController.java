/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
    private ComboBox<Integer> cmbCareerID;
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
        //Para cargar un combobox
        cmbCareerID.setItems(FXCollections.observableArrayList(
                1));
        cmbCareerID.setValue(1);

    }

    @FXML
    private void btnAdd(ActionEvent event) {
        LogicStudent logStudent = new LogicStudent();
        try {
            id = Integer.parseInt(txtID.getText());
            phoneNumber = parseDouble(txtPhoneNumber.getText());
            //VERIFICAR SI HAY ESPACIOS VACIOS

            java.util.Date d = java.sql.Date.valueOf(dpBirthday.getValue());

            Student std = new Student(id, (Integer) cmbCareerID.getValue(), txtStudentID.getText(), txtLastname.getText(), txtFirstname.getText(),
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
        cmbCareerID.setValue(0);
        txtStudentID.setText("");
        txtLastname.setText("");
        txtFirstname.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
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

}
