/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Student;
import Util.Mail;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.text.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class NewStudentController implements Initializable {
    
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtStudentID;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private ComboBox<String> cmbCareerID;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private Button btnAdd;

    //Lista necesaria para almacenar estudiantes
    private SinglyLinkList students;

    //Variables para almacenar el estudiante
    int id = 0;
    double phoneNumber = 0;
    Date d = null;
    @FXML
    private Button btnClean;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        students = new SinglyLinkList();
        String temporal = "";
        
        //Para cargar un combobox
        this.loadComboBoxCareers();

        //Mask for ID
        maskID(txtID);

        //Mask for PhoneNumber
        maskPhoneNumber(txtPhoneNumber);

        //Mask for StudentID
        maskStudentID(txtStudentID);

        //Mask FirstNamen, LastName y Address
        maskText(txtLastname);
        maskText(txtFirstname);
        maskText(txtAddress);
        
        //ToolTips
        Tooltip t = new Tooltip("Format: X-XXXX-XXXX");
        this.txtID.setTooltip(t);
        Tooltip t2 = new Tooltip("Format: XXXX-XXXX");
        this.txtPhoneNumber.setTooltip(t2);
        Tooltip t3 = new Tooltip("Enter the city you come from");
        this.txtAddress.setTooltip(t3);
        Tooltip t4 = new Tooltip("Example: helloworld@netbeans.com");
        this.txtEmail.setTooltip(t4);
        Tooltip t5 = new Tooltip("Example: C00648");
        this.txtStudentID.setTooltip(t5);
        Tooltip t6 = new Tooltip("Choose or write down your birthday");
        this.dpBirthday.setTooltip(t6);
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
    private void btnAdd(ActionEvent event){
        
        try {
            id = Integer.parseInt(txtID.getText());
            //VERIFICAR SI HAY ESPACIOS VACIOS

            java.util.Date d = java.sql.Date.valueOf(dpBirthday.getValue());
            
            String[] temporal = cmbCareerID.getValue().split("-"); //Hace split del combobox y pasa el resultado del id de carrera seleccionado
            Student std = new Student(id, Integer.parseInt(temporal[0]), txtStudentID.getText(), txtLastname.getText(), txtFirstname.getText(),
                    txtPhoneNumber.getText(), txtEmail.getText(), txtAddress.getText(), d);
            try {
                if (Util.Utility.emailChecker(txtEmail.getText())) {
                    if (Util.Utility.setListStudent(std)) {
                        try {
                            Util.Mail.registerEmail(std.getEmail(), std.data());//Recibe el correo del estudiante,  la infor de estudiante y el motivo.
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        
                        callAlert("notification", "Notification", "User has been registered");
                        System.out.println("La lista en util \n" + Util.Utility.getListStudents().toString());
                        btnClean(event);
                    } else {
                        callAlert("alert", "Error", "User already exist");
                    }                    
                }else{
                    callAlert("alert", "Error", "The completion of the mail must be @gmail.com");
                }
                
            } catch (ListException ex) {
                Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            callAlert("alert", "Error", "Only numerical numbers can be written. \n Please check the spaces");
        }
    }
    
    @FXML
    private void btnClean(ActionEvent event) {
        txtID.setText("");
        cmbCareerID.setValue("");
        txtStudentID.setText("");
        txtLastname.setText("");
        txtFirstname.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        loadComboBoxCareers();
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
    
    public void loadComboBoxCareers() {
        //Para cargar un combobox
        DoublyLinkList tempCareers = new DoublyLinkList();
        tempCareers = Util.Utility.getListCareer();
        String temporal = "";
        
        try {
            for (int i = 1; i <= tempCareers.size(); i++) {
                Career c = (Career) tempCareers.getNode(i).getData();
                temporal = c.getId() + "-" + c.getDescription();
                this.cmbCareerID.getItems().add(temporal);
            }
        } catch (ListException ex) {
            Logger.getLogger(NewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbCareerID.setValue(temporal);
    }
    
}//end class
