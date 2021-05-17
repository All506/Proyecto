//LogInController
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
public class LogInController implements Initializable {

    @FXML
    private Button btnLogIn;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnLogIn(ActionEvent event) throws IOException {
        if (txtPassword.getText().equals("") || txtUser.getText().equals("")) { //Cotnraseña y usuario vacios o invalidos
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/alert.fxml"));
                Parent root1;
                root1 = (Parent) loader.load();
                //Se llama al controller de la nueva ventana abierta
                AlertController controller = loader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Alerta");
                //Se le asigna la información a la controladora
                controller.setText("Alerta", "Ingrese un usuario y contraseña válidos");
                stage.setScene(new Scene(root1));
                stage.show();
                } catch (IOException ex) {
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //!!!!VERIFICAR SI USUARIO EXISTE ETC
            //Se obtiene el stage actual y se cierra
            Stage stage = (Stage) this.txtUser.getScene().getWindow();
            stage.close();
            //Se abre el nuevo stage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/menu.fxml"));
                Parent root1;
                root1 = (Parent) loader.load();
                //Se llama al controller de la nueva ventana abierta
                Parent root = FXMLLoader.load(getClass().getResource("/UI/menu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Main Menu");
                stage.setResizable(false);
                stage.show();
        }
    }

}
