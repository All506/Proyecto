/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class AlertController implements Initializable {

    @FXML
    private Label txtHeader;
    @FXML
    private Label txtBody;
    @FXML
    private ImageView imgView;

    /**
     * Initializes the controller class.
     */
    
    public void setText(String header, String text){
       txtHeader.setText(header);
       txtBody.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
