/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Domain.SinglyLinkList;
import Domain.Student;
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
        loadLists();
        loadPage("/UI/newStudent");
    }

    @FXML
    private void btnModifyStudent(ActionEvent event) {
        loadLists();
        loadPage("/UI/modifyStudent");
    }

    public void loadLists() {
        SinglyLinkList temp = new SinglyLinkList();
        //Se encarga de rellenar las listas desde los XML
        FileXML fXML = new FileXML();
        if (fXML.exist("Students.xml")) {
            students = fXML.readXMLtoStudentList("Students");
            try {
                for (int i = 1; i <= students.size(); i++) {
                    Util.Utility.setListStudent((Student)students.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListStudents().toString());
        }
    }

    @FXML
    private void btnShowStudents(ActionEvent event) {
        loadLists();
        this.bpMenu.setCenter(null);
        loadPage("/UI/showStudent");
    }

    @FXML
    private void btnDeleteStudent(ActionEvent event) {
        loadLists();
        loadPage("/UI/deleteStudent");
    }
}
