/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Domain.DoublyLinkList;
import Domain.ListException;
import Objects.Career;
import Objects.Course;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class NewCourseController implements Initializable {

    @FXML
    private Button btnClean;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCredits;
    @FXML
    private ComboBox<String> cmbCarrerId;
    @FXML
    private TextField txtId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tooltip t1 = new Tooltip("Example: IF3001");
        this.txtId.setTooltip(t1);
        Tooltip t2 = new Tooltip("Enter the number of credits of the new course");
        this.txtCredits.setTooltip(t2);
        Tooltip t3 = new Tooltip("Enter the name of the new course");
        this.txtName.setTooltip(t3);
        
        
        // TODO
        loadComboBoxCourse();
        this.btnAdd.setDisable(true);

        //Mask ID
        maskID(txtId);

        //Mask Name
        maskText(txtName);

        //Mask Credits
        maskCredits(txtCredits);
    }

    public void maskID(TextField txtId) {
        txtId.setOnKeyTyped((KeyEvent event) -> {
            if (event.getCharacter().trim().length() == 0) {
                if (txtId.getText().length() == 6) {
                    txtId.setText(txtId.getText().substring(0, 5));
                    txtId.positionCaret(txtId.getText().length());
                }
            } else {
                if (txtId.getText().length() == 0 || txtId.getText().length() == 1) {
                    if (!"0123456789".contains(event.getCharacter()) == false) {
                        event.consume();
                    }
                }
                if (txtId.getText().length() >= 2) {
                    if ("0123456789".contains(event.getCharacter()) == false) {
                        event.consume();
                    }
                }
                if(txtId.getText().length() == 6){
                        event.consume();
                    txtId.positionCaret(txtId.getText().length());
                }
                
            }
        });
    }

    public void maskCredits(TextField txtCredits) {
        txtCredits.setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtCredits.getText().length() == 6) {
                    txtCredits.setText(txtCredits.getText().substring(0, 5));
                    txtCredits.positionCaret(txtCredits.getText().length());
                }
            } else {

                if (txtCredits.getText().length() == 2) {
                    event.consume();
                    txtCredits.positionCaret(txtCredits.getText().length());
                }

            }
        });
    }

    public void maskText(TextField txtField) {
        txtField.setOnKeyTyped((KeyEvent event) -> {
            if (!"0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtField.getText().length() == 6) {
                    txtField.setText(txtField.getText().substring(0, 5));
                    txtField.positionCaret(txtField.getText().length());
                }
            } else {

                if (txtField.getText().length() == 4) {
                    txtField.positionCaret(txtField.getText().length());
                }

            }
        });
    }

    @FXML
    private void btnClean(ActionEvent event) {
        this.txtName.setText("");
        this.txtId.setText("");
        this.txtCredits.setText("");

    }

    public void loadComboBoxCourse() {
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";       
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career) tempCareers.getNode(i).getData();
                temporal = c.getId() + "-" + c.getDescription();
                this.cmbCarrerId.getItems().add(temporal);
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cmbCarrerId.setValue(temporal);
    }

    @FXML
    private void btnAdd(ActionEvent event) throws ListException {
        if(this.cmbCarrerId.getValue().equals(null)){
           callAlert("alert", "Error", "Choose a carreer to add the course");
        }else{ 
        int i = 0;
        String x = "";
            while (!("-").contains("" + this.cmbCarrerId.getValue().charAt(i))) {
                x += this.cmbCarrerId.getValue().charAt(i);
                i++;
            }
                Course crse = new Course(this.txtId.getText(), this.txtName.getText(), Integer.parseInt(this.txtCredits.getText()), Integer.parseInt(x));
                if (Util.Utility.setListCourse(crse)==false) {
                    callAlert("alert", "Error", "The data of the new course matches \n with an already existent course \n Please check your entries");
                } else {
                    this.btnClean(event);
                    this.btnAdd.setDisable(true);
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

    @FXML
    private void keyPressed(KeyEvent event) { //Es key Released pero por conveniencia el nombre es keyPressed
        if (this.txtName.getText().length() != 0 && this.txtId.getText().length() != 0 && this.txtCredits.getText().length() != 0) {
            this.btnAdd.setDisable(false);
        } else {
            this.btnAdd.setDisable(true);
        }
    }
}
