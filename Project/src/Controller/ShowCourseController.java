/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.Node;
import Objects.Career;
import Objects.Course;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sebastián Navarro Martínez
 */
public class ShowCourseController implements Initializable {

    @FXML
    private ComboBox<String> cmbCareerList;
    @FXML
    private TableView<Course> tblCoursesDisplay;
    @FXML
    private TableColumn<Course, String> colCourseId;
    @FXML
    private TableColumn<Course, String> colCourseName;
    @FXML
    private TableColumn<Course, Integer> colCourseCredits;

    private ObservableList<Course> cursos;
    @FXML
    private Button btnSearch;
    @FXML
    private Text txtIdCareer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadComboBoxCourse();
    }

    public void loadComboBoxCourse() {
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career) tempCareers.getNode(i).getData();
                temporal = c.getId()+"-"+c.getDescription();
                this.cmbCareerList.getItems().add(temporal);
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCareerList.setValue(temporal);
    }

    @FXML
    private void cmbCareerList(ActionEvent event) {

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
    private void btnSearch(ActionEvent event) {

        cursos = FXCollections.observableArrayList();
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        this.colCourseId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colCourseName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colCourseCredits.setCellValueFactory(new PropertyValueFactory("credits"));
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        try {
            if (Util.Utility.getListCourse().size() == 0) {
                callAlert("alert", "Error 404", "No courses found in any career");
                this.btnSearch.setVisible(false);
                this.tblCoursesDisplay.setVisible(false);
            } else {
                for (int i = 1; i <= Util.Utility.getListCourse().size(); i++) {
                    Course c = (Course) Util.Utility.getListCourse().getNode(i).data;
                    if (c.getCareerId() == Integer.parseInt(Util.Utility.getIDofString(this.cmbCareerList.getValue()))) {
                        cursos.add(c);
                    }
                }
                if(cursos.isEmpty()){
                    this.tblCoursesDisplay.setItems(null);
                    callAlert("alert", "Not found", "No courses to show");
                }else{
                    this.tblCoursesDisplay.setItems(cursos);
                }
            }
        } catch (ListException ex) {
            System.out.println("Error in show course controller, LINE 120");
        }
    }
}
