/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class ShowCareersController implements Initializable {

    @FXML
    private TableView<Career> tblCareers;
    @FXML
    private TableColumn<Career, Integer> colCareerdId;
    @FXML
    private TableColumn<Career, String> colDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DoublyLinkList lCareer = new DoublyLinkList();
        //Reinicia los valores de la tabla
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Error", "There are no careers registered");
        } else {
            for (int i = 0; i <= tblCareers.getItems().size(); i++) {
                tblCareers.getItems().clear();
            }

            try {
                //Se cargan los datos
                lCareer = Util.Utility.getListCareer();
                Career car = (Career) lCareer.getNode(1).getData();

                for (int i = 1; i <= lCareer.size(); i++) {
                    car =(Career) lCareer.getNode(i).getData();
                    tblCareers.getItems().add(car);
                    this.colCareerdId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    this.colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                }
            } catch (ListException ex) {
                Logger.getLogger(ShowCareersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
