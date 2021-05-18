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
    private SinglyLinkList students;
    private DoublyLinkList careers;

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
            students = fXML.readXMLtoStudentList("Students");
            try {
                for (int i = 1; i <= students.size(); i++) {
                    Util.Utility.setListStudent((Student) students.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Se carga la lista de carreras
        if (fXML.exist("Careers.xml")) {
            careers = fXML.readXMLtoCareertList();
            try {
                for (int i = 1; i <= careers.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListCareer((Career) careers.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListCareer().toString());
        }
    }
}
