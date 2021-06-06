/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Career;
import Objects.Student;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class ShowStudentController implements Initializable {

    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colCareerId;
    @FXML
    private TableColumn<Student, String> colFirstName;
    @FXML
    private TableColumn<Student, String> colLastName;
    @FXML
    private TableColumn<Student, String> colPhoneNumber;
    @FXML
    private TableColumn<Student, String> colEmail;
    @FXML
    private TableColumn<Student, String> colAddress;
    @FXML
    private TableColumn<Student, String> colBirthday;
    @FXML
    private TableColumn<Student, String> colStudentID;

    SinglyLinkList students = new SinglyLinkList();
    @FXML
    private TableView<Student> tableStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carga la lista de estudiantes
        students = Util.Utility.getListStudents();

        //Reinicia valores de la tabla
        for (int i = 0; i <= this.tableStudents.getItems().size(); i++) {
            this.tableStudents.getItems().clear();
        }

        if (students.isEmpty()) {
            callAlert("alert", "Error", "There are no registered students");
        } else {
            try {
                Student std = new Student((Student) students.getNode(1).getData());

                for (int i = 1; i <= students.size(); i++) {
                    std = new Student((Student) students.getNode(i).getData());
                    tableStudents.getItems().add(std);
                    this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    Career career = Util.Utility.getCarrerByID(String.valueOf(std.getCareerID()));
                    this.colCareerId.setCellValueFactory(c -> new SimpleStringProperty(career.getDescription()));
                    this.colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                    this.colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
                    this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
                    this.colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                    this.colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                    this.colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                    String date = Util.Utility.dateFormat(std.getBirthday2());
                    this.colBirthday.setCellValueFactory(c -> new SimpleStringProperty(date));
                }
            } catch (ListException ex) {
                Logger.getLogger(ShowStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
