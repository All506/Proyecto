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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class ModifyCourseController implements Initializable {

    @FXML
    private ComboBox<String> cmbCarrers;
    @FXML
    private Button btnClean;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCredits;
    @FXML
    private Button btnModify;
    private ComboBox<String> cmbCourseId;
    @FXML
    private TextField txtID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxCareer();
        loadComboBoxCarriers();
        this.btnModify.setDisable(true);
    }

    public void loadComboBoxCarriers() {
        //Para cargar un combobox
        CircularLinkList tempLCourse = new CircularLinkList();
        tempLCourse = Util.Utility.getListCourse();
        String temporal = "";

        try {
            for (int i = 1; i <= tempLCourse.size(); i++) {
                Course c = (Course) tempLCourse.getNode(i).getData();
                temporal = c.getId() + "-" + c.getName();
                this.cmbCourseId.getItems().add(temporal);
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCourseId.setValue(temporal);
    }

    public void loadComboBoxCareer() {
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";

        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career) tempCareers.getNode(i).getData();
                temporal = c.getId() + "-" + c.getDescription();
                this.cmbCarrers.getItems().add(temporal);
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCarrers.setValue(temporal);
    }

    @FXML
    private void btnClean(ActionEvent event) {
        txtName.setText("");
        txtCredits.setText("");
    }

    @FXML
    private void btnModify(ActionEvent event) {
        try {
            String[] courseSelected = cmbCourseId.getValue().split("-");
            Course tempCourse = new Course(courseSelected[0], courseSelected[1], 0, 0);
            Util.Utility.getListCourse().remove(tempCourse);
            String[] careerSelected = cmbCarrers.getValue().split("-");
            tempCourse = new Course(courseSelected[0], courseSelected[1], Integer.valueOf(txtCredits.getText()), Integer.valueOf(careerSelected[0]));
            Util.Utility.getListCourse().add(tempCourse);
            callAlert("notification", "Succesfully modified", "Course has been modified");
            btnClean(event);
            loadComboBoxCareer();
            loadComboBoxCarriers();
            this.btnModify.setDisable(true);
        } catch (ListException ex) {
            Logger.getLogger(ModifyCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cmbCourseId(ActionEvent event) {
        try {
            String[] temporal = this.cmbCourseId.getValue().split("-");
            Course tempCourse = new Course(temporal[0], temporal[1], 0, 0);
            if (Util.Utility.getListCourse().contains(tempCourse)) {
                tempCourse = (Course) Util.Utility.getListCourse().getNode(Util.Utility.getListCourse().indexOf(tempCourse)).data;
                this.txtName.setText(tempCourse.getName());
                this.txtCredits.setText(String.valueOf(tempCourse.getCredits()));
            }
        } catch (ListException ex) {
            Logger.getLogger(ModifyCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.btnModify.setDisable(false);
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
