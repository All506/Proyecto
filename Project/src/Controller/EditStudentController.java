/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.NewStudentController.maskID;
import static Controller.NewStudentController.maskPhoneNumber;
import static Controller.NewStudentController.maskStudentID;
import Domain.ListException;
import Domain.Node;
import Domain.SinglyLinkList;
import Objects.Student;
import XML.FileXML;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class EditStudentController implements Initializable {

    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ComboBox<Integer> cmbCareerID;
    @FXML
    private TextField txtEmail;
    private TextField txtStudentID;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtLastname;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private TextField txtFirstname;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClean;
    @FXML
    private ComboBox<String> cmbId;

    SinglyLinkList students = new SinglyLinkList();
    @FXML
    private TextField txtId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Mask for ID
            maskID(txtId);

            //Mask for PhoneNumber
            maskPhoneNumber(txtPhoneNumber);

            //Mask FirstNamen, LastName y Address
            maskText(txtLastname);
            maskText(txtFirstname);
            maskText(txtAddress);

            this.btnModify.setVisible(false);
            students = Util.Utility.getListStudents();

            for (int i = 1; i <= students.size(); i++) {
                Student s = (Student) students.getNode(i).getData();
                cmbId.getItems().add(s.getStudentID());
            }
            cmbCareerID.setValue(1);

        } catch (ListException ex) {
            Logger.getLogger(EditStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public static void maskStudentID(TextField txtStudentID) {
        txtStudentID.setOnKeyTyped((KeyEvent event) -> {
            if (event.getCharacter().trim().length() == 0) {
                if (txtStudentID.getText().length() == 6) {
                    txtStudentID.setText(txtStudentID.getText().substring(0, 5));
                    txtStudentID.positionCaret(txtStudentID.getText().length());
                }
            } else {
                if (txtStudentID.getText().length() == 6) {
                    event.consume();
                }
            }
        });
    }

    public static void maskID(TextField txtID) {
        txtID.setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtID.getText().length() == 6) {
                    txtID.setText(txtID.getText().substring(0, 5));
                    txtID.positionCaret(txtID.getText().length());
                }
            } else {
                if (txtID.getText().length() == 10) {
                    event.consume();
                }

                if (txtID.getText().length() == 6) {
                    txtID.setText(txtID.getText() + "0");
                    txtID.positionCaret(txtID.getText().length());
                }
            }
        });
    }

    public static void maskPhoneNumber(TextField txtPhoneNumber) {
        txtPhoneNumber.setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }
            if (event.getCharacter().trim().length() == 0) {
                if (txtPhoneNumber.getText().length() == 6) {
                    txtPhoneNumber.setText(txtPhoneNumber.getText().substring(0, 5));
                    txtPhoneNumber.positionCaret(txtPhoneNumber.getText().length());
                }
            } else {
                if (txtPhoneNumber.getText().length() == 9) {
                    event.consume();
                }
                if (txtPhoneNumber.getText().length() == 4) {
                    txtPhoneNumber.setText(txtPhoneNumber.getText() + "-");
                    txtPhoneNumber.positionCaret(txtPhoneNumber.getText().length());
                }

            }
        });
    }

    @FXML
    private void btnModify(ActionEvent event) throws ListException, ParseException {

        //Convertir Date a LocalDate
        java.util.Date d = java.sql.Date.valueOf(dpBirthday.getValue());
        //Se elmina y luego se anhade

        Student std = new Student(Integer.parseInt(txtId.getText()), this.cmbCareerID.getValue(),
                String.valueOf(this.cmbId.getValue()), this.txtLastname.getText(), this.txtFirstname.getText(),
                this.txtPhoneNumber.getText(), this.txtEmail.getText(), this.txtAddress.getText(), d);

        try {
            if (Util.Utility.emailChecker(txtEmail.getText())) {//Check the correct email
                Util.Utility.getListStudents().remove(std);
                Util.Utility.setListStudent(std);
                
                callAlert("notification", "Notification", "The Student was update");
            } else {
                callAlert("alert", "Error", "The completion of the mail must be @gmail.com");
            }
            
            
            
        } catch (Exception e) {
            callAlert("alert", "Error", "Only numerical numbers can be written. \n Please check the spaces");//If the user put any blank spaces
        }

    }

    @FXML
    private void btnClean(ActionEvent event) {
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtId.setText("");
        txtAddress.setText("");
        txtLastname.setText("");
        txtFirstname.setText("");
        btnModify.setVisible(false);
    }

    @FXML
    private void cmbId(ActionEvent event) throws ListException {
        btnModify.setVisible(true);
        Student temp = new Student(0, 0, cmbId.getValue().toString(), "", "", "", "", "", null);
        if (students.contains(temp)) {
            temp = look4student(temp);
            this.txtAddress.setText(temp.getAddress());
            this.txtEmail.setText(temp.getEmail());
            this.txtFirstname.setText(temp.getFirstname());
            this.txtLastname.setText(temp.getLastname());
            this.txtPhoneNumber.setText(temp.getPhoneNumber());
            this.txtId.setText(String.valueOf(temp.getId()));
            //Convertir Date a LocalDate
            LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(temp.getBirthday()));
            this.dpBirthday.setValue(localDate);
        }
    }

    private Student look4student(Student temp) throws ListException {
        //Busca la información de un estudiante en la lista y devuelve al estudiante
        if (!students.isEmpty()) { //Si la lista esta vacia
            Node aux = students.getNode(1);
            while (aux != null) { //Va a analizar incluso el primer elemento para ver si es igual al objeto
                Student s = (Student) aux.data;
                if (s.getStudentID().equalsIgnoreCase(temp.getStudentID())) {
                    return (Student) aux.data;
                } else {
                    aux = aux.next;
                }

            }

        }
        return null;
    }

    private boolean checkBlankSpaces() {
        boolean flag = false;
        if (txtId.getText().equals("") || txtEmail.getText().equals("") || txtAddress.getText().equals("") || txtStudentID.getText().equals("") || txtLastname.getText().equals("") || txtFirstname.getText().equals("")) {
            flag = true;
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
