/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Controller.AlertController;
import Controller.LogInController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Alán
 */
public class mainFx extends Application {

    public static void main(String[] args) {
        launch(args);
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

    @Override
    public void start(Stage stage) throws Exception {
        
        stage.initStyle(StageStyle.UTILITY);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                event.consume();
                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                dialog.setTitle("Are you sure?");
                dialog.setHeaderText(null);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.setContentText("After closing, all information not saved will be deleted.  \n Remenber to Log Out in order to save all data");
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();
                }

            }
        });

        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/Resources/eMatriculaLogo.png"));
        stage.setTitle("LogIn");
        stage.setResizable(false);
        stage.show();

    }

}
