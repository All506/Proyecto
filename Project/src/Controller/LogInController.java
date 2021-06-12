//LogInController
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Security;
import Objects.Student;
import XML.FileXML;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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

    //Se incializa la lista
    private CircularLinkList lSecurity = new CircularLinkList();
    private SinglyLinkList lStudents = new SinglyLinkList();
    private CircularLinkList lStudentsPass = new CircularLinkList();
    @FXML
    private Button btnExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FileXML fXML = new FileXML();

        //Se carga la lista desde el xml
        if (fXML.exist("Security.xml")) {
            lSecurity = fXML.readXMLtoSecurityList();
            
            if (!lSecurity.isEmpty()) {
                try {
                    for (int i = 1; i <= lSecurity.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListSecurity((Security) lSecurity.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lSecurity.add(new Security("admin", "prueba")); //Usuario de pruebas en caso de que xml no exista

        }

        //Se carga la lista desde el xml
        if (fXML.exist("Students.xml")) {
            lStudents = fXML.readXMLtoStudentList("Students");
            if (!lStudents.isEmpty()) {
                try {
                    for (int i = 1; i <= lStudents.size(); i++) { //Se añaden los objetos del xml a util
                        Student std = (Student) lStudents.getNode(i).data;
                        Security sec = new Security(String.valueOf(std.getId()), std.getStudentID());
                        Util.Utility.setListStudent(std);
                        this.lStudentsPass.add(sec);
                        System.out.println(sec.toString());
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    @FXML
    private void btnLogIn(ActionEvent event) throws IOException, ListException {
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
            Security logUser = new Security(txtUser.getText(), txtPassword.getText());
            if (!lSecurity.isEmpty() && lSecurity.contains(logUser)) { //La persona registrada es un Usuario
                Util.Utility.setKindUser(true);
                callMenu("menu");
            } else {
                if (!lStudentsPass.isEmpty()&&this.lStudentsPass.contains(logUser)) {
                    Util.Utility.setKindUser(false, logUser.getUser());
                    callMenu("menu");
                } else {
                    callAlert("alert", "Error", "User and Password is not registered. \n Try with something else");
                }

            }

        }
    }

    private void callMenu(String fxmlName) throws IOException {
        Stage stage = (Stage) this.txtUser.getScene().getWindow();
        stage.close();
        //Se abre el nuevo stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + fxmlName + ".fxml"));
        Parent root1;
        root1 = (Parent) loader.load();
        //Se llama al controller de la nueva ventana abierta
        Parent root = FXMLLoader.load(getClass().getResource("/UI/" + fxmlName + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.show();
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

    @FXML
    private void btnExit(ActionEvent event) {
        Stage stage = (Stage) this.btnExit.getScene().getWindow();
        stage.close();
    }
}
