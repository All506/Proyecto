/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Objects.Security;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class NewUserController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClean;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtRePassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAdd(ActionEvent event) throws ListException {
        if (validations()){
            Security sec = new Security(txtUser.getText(),txtPassword.getText());
            Util.Utility.setListSecurity(sec);
            callAlert("notification","New user has been added","New User has been registered");
            System.out.println(Util.Utility.getListSecurity().toString());
        }
    }

    @FXML
    private void btnClean(ActionEvent event) {
        txtPassword.setText("");
        txtRePassword.setText("");
        txtUser.setText("");
    }
    
    private boolean validations(){
        boolean flag = true; //True if user is gonna be added to list
        //EmptyValidations
        if (txtPassword.getText().equals("")||txtRePassword.getText().equals("")||txtUser.getText().equals("")){
            flag = false;
        } else { //if they r not empty
            if (!txtPassword.getText().equalsIgnoreCase(txtRePassword.getText())){ //Password are not the same
                callAlert("alert","Error","Password and Password Check \n are not the same. Try again");
                flag = false;
            }
        }
        return flag;
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
