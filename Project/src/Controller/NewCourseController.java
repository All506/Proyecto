/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class NewCourseController implements Initializable {

    @FXML
    private ComboBox<?> cmbCarrers;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnClean;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCredits;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnBack(ActionEvent event) {
    }

    @FXML
    private void btnClean(ActionEvent event) {
    }

    @FXML
    private void btnAdd(ActionEvent event) {
    }
    
}
