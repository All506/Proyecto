/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Career;
import Objects.Enrollment;
import Objects.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class ShowStudentController implements Initializable {

    @FXML
    private TableColumn<List<String>, String> colId;
    @FXML
    private TableColumn<List<String>, String> colCareerId;
    @FXML
    private TableColumn<List<String>, String> colFirstName;
    @FXML
    private TableColumn<List<String>, String> colLastName;
    @FXML
    private TableColumn<List<String>, String> colPhoneNumber;
    @FXML
    private TableColumn<List<String>, String> colEmail;
    @FXML
    private TableColumn<List<String>, String> colAddress;
    @FXML
    private TableColumn<List<String>, String> colBirthday;
    @FXML
    private TableColumn<List<String>, String> colStudentID;

    SinglyLinkList students = new SinglyLinkList();
    @FXML
    private TableView<List<String>> tableStudents;

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
                loadTable();
//            try {
//                //Student std = new Student((Student) students.getNode(1).getData());
//                for (int i = 1; i <= students.size(); i++) {
//                    Student std = new Student((Student) students.getNode(i).getData());
//                    tableStudents.getItems().add(std);
//                }
//                    this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
////                    Career career = Util.Utility.getCarrerByID(String.valueOf(std.getCareerID()));
//                    this.colCareerId.setCellValueFactory(new PropertyValueFactory<>("careerID"));
//                    this.colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
//                    this.colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
//                    this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
//                    this.colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//                    this.colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//                    this.colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
////                    String date = Util.Utility.dateFormat(std.getBirthday());
//                    this.colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
////                }
//            } catch (ListException ex) {
//                Logger.getLogger(ShowStudentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
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
    
    //****************************************************************
        
    public void loadTable() throws ListException{
    
    colId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(0));
        }
    });
    
    colStudentID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(1));
        }
    });
    
    colCareerId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(2));
        }
    });
    
    colFirstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(3));
        }
    });
    colLastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(4));
        }
    });
    colPhoneNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(5));
        }
    });
    colEmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(6));
        }
    });
    colAddress.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(7));
        }
    });
    colBirthday.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<List<String>, String> data) {
        return new ReadOnlyStringWrapper(data.getValue().get(8));
        }
    });
        
       
    tableStudents.setItems(getData());
    };
    
    public ObservableList<List<String>> getData() throws ListException{
   
    SinglyLinkList list = Util.Utility.getListStudents();
        
    final ObservableList<List<String>> data = FXCollections.observableArrayList();
    if(!list.isEmpty()){
        
        for (int i = 1; i <= list.size(); i++) {
            Student s = (Student)list.getNode(i).data;
            List<String> arrayList = new ArrayList<>();
            
            arrayList.add(""+s.getId());
            arrayList.add(s.getStudentID());
            arrayList.add(Util.Utility.getCarrerByID(""+s.getCareerID()).getDescription());
            arrayList.add(s.getFirstname());
            arrayList.add(s.getLastname());
            arrayList.add(s.getPhoneNumber());
            arrayList.add(s.getEmail());
            arrayList.add(s.getAddress());
            arrayList.add(Util.Utility.dateFormat(s.getBirthday2()));
            
            
            data.add(arrayList);
            
        }
    
    }
       
        
    return data;
    }    
        
     //****************************************************************
        
        
        
}
