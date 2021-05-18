/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class MenuController implements Initializable {

    //Se definen las listas
    private SinglyLinkList lStudents;
    private DoublyLinkList lCareers;

    @FXML
    private BorderPane bpMenu;
    @FXML
    private MenuItem mnNewStudent;
    @FXML
    private MenuItem btnModifyStudent;
    @FXML
    private MenuItem btnShowStudents;
    @FXML
    private MenuItem btnDeleteStudent;
    @FXML
    private MenuItem newCareer;
    @FXML
    private MenuItem btnShowCareers;
    @FXML
    private MenuItem btnDeleteOrEditCareer;
    @FXML
    private MenuItem mnNewCourse;
    @FXML
    private MenuItem btnModifyCourse;
    @FXML
    private MenuItem btnDeleteCourse;
    @FXML
    private MenuItem btnShowCourse;

    /**
     * Initializes the controller class.
     */
    private void loadPage(String pageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(pageName + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bpMenu.setCenter(root);
    } // Fin método Load Page

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SinglyLinkList students = new SinglyLinkList();
        //Se llama al metodo que carga a las listas desde el xml
        loadLists();
    }

    @FXML
    private void mnNewStudent(ActionEvent event) {
        loadPage("/UI/newStudent");
    }

    @FXML
    private void btnModifyStudent(ActionEvent event) {
        loadPage("/UI/modifyStudent");
    }

    @FXML
    private void btnShowStudents(ActionEvent event) {
        this.bpMenu.setCenter(null);
        loadPage("/UI/showStudent");
    }

    @FXML
    private void btnDeleteStudent(ActionEvent event) {
        loadPage("/UI/deleteStudent");
    }

    @FXML
    private void newCareer(ActionEvent event) {
        loadPage("/UI/NewCareer");
    }

    @FXML
    private void btnShowCareers(ActionEvent event) {
        loadPage("/UI/showCareers");
    }

    @FXML
    private void btnDeleteOrEditCareer(ActionEvent event) {
        loadPage("/UI/EditAndDeleteCareer");
    }

    //Se podría llamar al inicio y final
    public void loadLists() {
        SinglyLinkList temp = new SinglyLinkList();
        DoublyLinkList tempCareer = new DoublyLinkList();

        //Se encarga de rellenar las listas desde los XML
        //Se rellena la lista de estudiantes segun el xml Students.xml
        FileXML fXML = new FileXML();
        if (fXML.exist("Students.xml")) {
            lStudents = fXML.readXMLtoStudentList("Students");
            try {
                for (int i = 1; i <= lStudents.size(); i++) {
                    Util.Utility.setListStudent((Student) lStudents.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Se carga la lista de carreras
        if (fXML.exist("Careers.xml")) {
            lCareers = fXML.readXMLtoCareertList();
            try {
                for (int i = 1; i <= lCareers.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListCareer((Career) lCareers.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListCareer().toString());
        }
    }
    
    public void SaveData(){
        lStudents = Util.Utility.getListStudents(); //Se carga la lista de estudiantes al XML
        FileXML fXML = new FileXML();
        if (!fXML.exist("Students.xml")){ //Si el archivo no existe
            fXML.createXML("StudentsXML", "Students");
            
        }
    }

    @FXML
    private void mnNewCourse(ActionEvent event) {
         loadPage("/UI/newCourse");
    }

    @FXML
    private void btnModifyCourse(ActionEvent event) {
         loadPage("/UI/modifyCourse");
    }

    @FXML
    private void btnDeleteCourse(ActionEvent event) {
    }

    @FXML
    private void btnShowCourse(ActionEvent event) {
    }
}
