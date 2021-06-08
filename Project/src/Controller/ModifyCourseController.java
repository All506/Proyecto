/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Domain.ListException;
import Domain.Node;
import Objects.Course;
import Objects.Student;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class ModifyCourseController implements Initializable {

    @FXML
    private ComboBox<String> cmbCarrers;
    @FXML
    private Button btnClean;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCredits;
    @FXML
    private Button btnModify;

    CircularDoublyLinkList tempCourse = new CircularDoublyLinkList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Carga la lista 
            tempCourse = Util.Utility.getListCourse();

            //Llena combox con la carrera
            for (int i = 1; i <= tempCourse.size(); i++) {
                Course course = (Course) tempCourse.getNode(i).getData();
                cmbCarrers.getItems().add(course.getId() + "-" + course.getName());
            }

        } catch (Exception e) {
        }
        //Mask ID
        maskID(txtID);

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
                if (txtId.getText().length() == 6) {
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
        txtID.setText("");
        txtName.setText("");
        txtCredits.setText("");
        cmbCarrers.setValue("");
    }

    @FXML
    private void btnModify(ActionEvent event) {
        try {
            int saveCareerId;
            String[] valueSelected = cmbCarrers.getValue().split("-");
            if (!txtName.equals("") && !txtID.equals("") && !txtCredits.equals("")){
                Course course = new Course(valueSelected[0], txtName.getText(), Integer.parseInt(txtCredits.getText()), Util.Utility.getCareerByCourse(valueSelected[0]));
                saveCareerId = course.getCareerId();
                Util.Utility.getListCourse().remove(course); //Se elimina el nodo de la lista
                course = new Course(txtID.getText(), txtName.getText(), Integer.parseInt(txtCredits.getText()), saveCareerId);
                Util.Utility.getListCourse().add(course); //Se vuelve a añadir el nodo en la lista con la nueva información
                callAlert("notification", "Course Edited", "Course has been edited sucessfully");
            }
        } catch (Exception e) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void cmbCarrers(ActionEvent event) throws ListException {
        btnModify.setVisible(true);
        Course tempCourse1 = new Course();
        String[] valueSelected = cmbCarrers.getValue().split("-");
        tempCourse1.setId(valueSelected[0]);
        tempCourse1.setName(valueSelected[1]);
        if (tempCourse.contains(tempCourse1)) {
            tempCourse1 = look4Course(tempCourse1);
            this.txtID.setText(tempCourse1.getId());
            this.txtName.setText(tempCourse1.getName());
            this.txtCredits.setText(String.valueOf(tempCourse1.getCredits()));

        }
    }

    private Course look4Course(Course temp) throws ListException {
        //Busca la información de un curso en la lista y devuelve al curso
        if (!tempCourse.isEmpty()) { //Si la lista esta vacia
            Node aux = tempCourse.getNode(1);
            while (aux != null) { //Va a analizar incluso el primer elemento para ver si es igual al objeto
                Course s = (Course) aux.data;
                if (s.getName().equalsIgnoreCase(temp.getName())) {
                    return (Course) aux.data;
                } else {
                    aux = aux.next;
                }

            }

        }
        return null;
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

}//end class
