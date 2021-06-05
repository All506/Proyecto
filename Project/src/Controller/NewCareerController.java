/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Objects.Career;
import Domain.ListException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class NewCareerController implements Initializable {

    @FXML
    private TextField txtCareerId;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClean;
    @FXML
    private TextArea txtDescription;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCareerId.setEditable(false);
        txtCareerId.setText(String.valueOf(Objects.Career.consecutivo));
        
        //Mask for Description
        maskText(txtDescription);
        
    }    

    public void maskText(TextArea txtArea) {
        txtArea.setOnKeyTyped((KeyEvent event) -> {
            if (!"0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtArea.getText().length() == 6) {
                    txtArea.setText(txtArea.getText().substring(0, 5));
                    txtArea.positionCaret(txtArea.getText().length());
                }
            } else {

                if (txtArea.getText().length() == 4) {
                    txtArea.positionCaret(txtArea.getText().length());
                }

            }
        });
    }
    
    @FXML
    private void btnAdd(ActionEvent event) throws ListException {
        //Verificar si han sido ingresados valores numéricos
        Career car = new Career(Integer.parseInt(txtCareerId.getText()),txtDescription.getText());
        if (Util.Utility.setListCareer(car)){
            callAlert("notification","Notification","Career has been saved");
        } else {
            callAlert("alert","Error","Description is already in registered");
        }
        
        //Update the txtCareerID
        txtCareerId.setEditable(false);
        txtCareerId.setText(String.valueOf(Objects.Career.consecutivo));
        txtDescription.setText("");
    }

    @FXML
    private void btnClean(ActionEvent event) {
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
