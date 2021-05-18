/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import XML.FileXML;
import XML.LogicCareer;
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
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class EditAndDeleteCareerController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnClean;
    @FXML
    private ComboBox<String> cmbCareers;
    @FXML
    private TextArea txtDescription;
    
    private LogicCareer logCareer = new LogicCareer();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadComboBoxCareers();
        this.btnDelete.setVisible(false);
        this.btnEdit.setVisible(false);
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        String[] valueSelected = cmbCareers.getValue().split("-");
        Career c = new Career(Integer.parseInt(valueSelected[0]), valueSelected[1]);
        Util.Utility.deleteNodeLCareer(c);
        try {
            logCareer.deleteCareer(c); //Se elimina del txt !!HACERLO EN EL CIERRE DE SESION
            callAlert("notification","Career Deleted","Career has been deleted");
            loadComboBoxCareers();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnEdit(ActionEvent event) throws ListException {
        try {
            String[] valueSelected = cmbCareers.getValue().split("-");
            Career careerToDelete = new Career(Integer.parseInt(valueSelected[0]), valueSelected[1]);
            Career careerToAdd = new Career(Integer.parseInt(valueSelected[0]),txtDescription.getText());
            Util.Utility.deleteNodeLCareer(careerToDelete);
            logCareer.deleteCareer(careerToDelete); //Se elimina del txt
            logCareer.writeCareer(careerToAdd); //!! CERRAR SESION
            Util.Utility.setListCareer(careerToAdd);
            callAlert("notification","Career Edited","Career has been edited sucessfully");
            loadComboBoxCareers();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(EditAndDeleteCareerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClean(ActionEvent event) {
        loadComboBoxCareers();
        this.btnDelete.setVisible(false);
        this.btnEdit.setVisible(false);
    }

    public void loadComboBoxCareers() {
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        FileXML fXML = new FileXML();
        tempCareers = fXML.readXMLtoCareertList(); //TOMAR LA LISTA DE UTIL PARA MOSTRARLO
        
        //tempCareers = Util.Utility.getListCareer();
        
        String temporal = "";
        //LIMPIA EL COMBOBOX
        for (int i = 1; i <= this.cmbCareers.getItems().size(); i++) {
                cmbCareers.getItems().clear();
            }
        
        //LO RELLENA
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career) tempCareers.getNode(i).getData();
                temporal = c.getId() + "-" + c.getDescription();
                this.cmbCareers.getItems().add(temporal);
                this.txtDescription.setText(c.getDescription());
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbCareers.setValue(temporal);
    }

    @FXML
    private void cmbCareers(ActionEvent event) throws ListException {
        String[] valueSelected = cmbCareers.getValue().split("-");
        System.out.println("0 " + valueSelected[0] + "1 " + valueSelected[1]);
        Career c = new Career(Integer.parseInt(valueSelected[0]), valueSelected[1]);
        int temp = Util.Utility.getListCareer().indexOf(c); //Se obtiene el nodo de la carrera en la lista
        c = (Career) Util.Utility.getListCareer().getNode(temp).data;
        this.txtDescription.setText(c.getDescription());
        this.btnDelete.setVisible(true);
        this.btnEdit.setVisible(true);
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

    public void clean() {
        loadComboBoxCareers();
        this.btnDelete.setVisible(false);
        this.btnEdit.setVisible(false);
    }
}
