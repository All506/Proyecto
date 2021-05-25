/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularLinkList;
import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Course;
import Objects.Security;
import Objects.Student;
import Objects.TimeTable;
import Security.AES;
import XML.FileXML;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Alán
 */
public class MenuController implements Initializable {

    //Se definen las listas
    private SinglyLinkList lStudents;
    private DoublyLinkList lCareers;
    private CircularLinkList lSecurity;
    private CircularLinkList lCourse;
    private SinglyLinkList lSchedules;

    @FXML
    private BorderPane bpMenu;
    @FXML
    private MenuItem mnNewStudent;
    @FXML
    private MenuItem btnModifyStudent;
    @FXML
    private MenuItem btnShowStudents;
    @FXML
    private MenuItem btnDeleteStudent;
    @FXML
    private MenuItem newCareer;
    @FXML
    private MenuItem btnShowCareers;
    @FXML
    private MenuItem btnDeleteOrEditCareer;
    @FXML
    private MenuItem btnLogOut;
    @FXML
    private MenuItem mnNewCourse;
    @FXML
    private MenuItem btnModifyCourse;
    @FXML
    private MenuItem btnDeleteCourse;
    @FXML
    private MenuItem btnShowCourse;
    @FXML
    private MenuItem btnNewSchedule;
    @FXML
    private MenuItem btnNewUser;
    @FXML
    private Menu menuStudent;
    @FXML
    private Menu menuCareer;
    @FXML
    private Menu menuCourse;
    @FXML
    private Menu menuSchedules;
    @FXML
    private Menu menuUser;
    @FXML
    private MenuItem mnEnrollment;
    @FXML
    private MenuItem mnDeEnrollment;

    /**
     * Initializes the controller class.
     */
    
    //CONTRASEÑA PARA DESENCRIPTACION: Proyecto
    private void loadPage(String pageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(pageName + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bpMenu.setCenter(root);
    } // Fin método Load Page

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configMenu();
        SinglyLinkList students = new SinglyLinkList();
        //Se llama al metodo que carga a las listas desde el xml
        loadLists();
    }

    @FXML
    private void mnNewStudent(ActionEvent event) {
        loadPage("/UI/newStudent");
    }

    @FXML
    private void btnModifyStudent(ActionEvent event) {
        loadPage("/UI/modifyStudent");
    }

    @FXML
    private void btnShowStudents(ActionEvent event) {
        this.bpMenu.setCenter(null);
        loadPage("/UI/showStudent");
    }

    @FXML
    private void btnDeleteStudent(ActionEvent event) {
        loadPage("/UI/deleteStudent");
    }

    @FXML
    private void newCareer(ActionEvent event) {
        loadPage("/UI/NewCareer");
    }

    @FXML
    private void btnShowCareers(ActionEvent event) {
        loadPage("/UI/showCareers");
    }

    @FXML
    private void btnDeleteOrEditCareer(ActionEvent event) {
        loadPage("/UI/EditAndDeleteCareer");
    }

    //Se podría llamar al inicio y final
    public void loadLists(){ //Modificarlo para que no se caiga cuando no hay documentos
        SinglyLinkList temp = new SinglyLinkList();
        DoublyLinkList tempCareer = new DoublyLinkList();

        //Se encarga de rellenar las listas desde los XML
        //Se rellena la lista de estudiantes segun el xml Students.xml
        FileXML fXML = new FileXML();
        if (fXML.exist("Students.xml")) {
            lStudents = fXML.readXMLtoStudentList("Students");
            try {
                for (int i = 1; i <= lStudents.size(); i++) {
                    Util.Utility.setListStudent((Student) lStudents.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Se carga la lista de carreras
        if (fXML.exist("Careers.xml")) {
            lCareers = fXML.readXMLtoCareertList();
            try {
                for (int i = 1; i <= lCareers.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListCareer((Career) lCareers.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListCareer().toString());
        }
        
         //Carga y desencripta los cursos
        
        if (fXML.exist("Courses.xml")) {
            lCourse = fXML.readXMLtoCourseList();
            try {
                for (int i = 1; i <= lCourse.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListCourse((Course) lCourse.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListCourse().toString());
        }
        
         //Carga y desencripta los cursos
        
        if (fXML.exist("Schedules.xml")) {
            lSchedules = fXML.readXMLtoScheduleList();
            
            try {
               
                for (int i = 1; i <= lSchedules.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListSchedule((TimeTable) lSchedules.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListSchedule().toString());
        }
        
         //Carga y desencripta los cursos
        
        if (fXML.exist("Courses.xml")) {
            lCourse = fXML.readXMLtoCourseList();
            try {
                for (int i = 1; i <= lCourse.size(); i++) { //Se añaden los objetos del xml a util
                    Util.Utility.setListCourse((Course) lCourse.getNode(i).data);
                }
            } catch (ListException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Lista en util \n " + Util.Utility.getListCourse().toString());
        }
    }

    public void saveData() throws ListException {

        lStudents = Util.Utility.getListStudents(); //Se carga la lista de estudiantes al XML
        lCareers = Util.Utility.getListCareer();
        lSecurity = Util.Utility.getListSecurity();
        lCourse = Util.Utility.getListCourse();
        lSchedules = Util.Utility.getListSchedule();

        FileXML fXML = new FileXML();

        //Guardar datos de los estudiantes
        if (!fXML.exist("Students.xml")) { //Si el archivo no existe
            fXML.createXML("StudentsXML", "Students");
            writeStudents();
        } else {
            fXML.deleteFile("Students");
            fXML.createXML("StudentsXML", "Students");
            writeStudents();
        }

        //Guardar datos de las carreras
        if (!fXML.exist("Careers.xml")) { //Si el archivo no existe
            fXML.createXML("CareersXML", "Careers");
            writeCareers();
        } else {
            fXML.deleteFile("Careers");
            fXML.createXML("CareersXML", "Careers");
            writeCareers();
        }

        //Guardar datos encriptados en XML de User
        if (!fXML.exist("Security.xml")) { //Si el archivo no existe
            fXML.createXML("SecurityXML", "Security");
            writeSecurity();
        } else {
            fXML.deleteFile("Security");
            fXML.createXML("SecurityXML", "Security");
            writeSecurity();
        }
        
        //Guarda datos de la lista de cursos en XML
        
        if (!fXML.exist("Courses.xml")) { //Si el archivo no existe
            fXML.createXML("CoursesXML", "Courses");
            writeCourses();
        } else {
            fXML.deleteFile("Courses");
            fXML.createXML("CoursesXML", "Courses");
            writeCourses();
        }
   
        //Guarda datos de la lista Horarios en XML
        
        //Guarda datos de la lista de cursos en XML
        
        if (!fXML.exist("Schedules.xml")) { //Si el archivo no existe
            fXML.createXML("SchedulesXML", "Schedules");
            writeSchedules();
        } else {
            fXML.deleteFile("Schedules");
            fXML.createXML("SchedulesXML", "Schedules");
            writeSchedules();
        }
    }

    public void writeStudents() throws ListException {
        
        FileXML fXML = new FileXML();
        
        if(Util.Utility.getListStudents().isEmpty()){
           if (fXML.exist("Students.xml")){
               fXML.deleteFile("Students");
       }
       }else{
   
        for (int i = 1; i <= lStudents.size(); i++) {
            Student tempStd = (Student) lStudents.getNode(i).data;
            try {

                fXML.writeXML("Students.xml", "Students", tempStd.dataName(), tempStd.data());
            } catch (TransformerException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    }

    public void writeCareers() throws ListException {
        FileXML fXML = new FileXML();
        
        if(Util.Utility.getListCareer().isEmpty()){
           if (fXML.exist("Careers.xml")){
               fXML.deleteFile("Careers");
       }
       }else{
        
        for (int i = 1; i <= lCareers.size(); i++) {
            Career car = (Career) lCareers.getNode(i).data;
            try {
                fXML.writeXML("Careers.xml", "Careers", car.dataName(), car.data());
            } catch (TransformerException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    }

    public void writeSecurity() throws ListException {
        FileXML fXML = new FileXML();
        
        if(Util.Utility.getListSecurity().isEmpty()){
           if (fXML.exist("Security.xml")){
               fXML.deleteFile("Security");}
       }else{
        
        AES encrypt = new AES();
        for (int i = 1; i <= lSecurity.size(); i++) {
            Security sec = (Security)lSecurity.getNode(i).data;
            //Se encripta la información y se guarda
            Security encSec = new Security(encrypt.encrypt(sec.getUser(),"Proyecto"),encrypt.encrypt(sec.getPassword(),"Proyecto"));
            System.out.println("Encriptado: " + encSec.toString());
            try {
                fXML.writeXML("Security.xml", "User", encSec.dataName(), encSec.data());
            } catch (TransformerException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    }
    
    public void writeCourses() throws ListException {
       FileXML fXML = new FileXML(); 
       
       if(Util.Utility.getListCourse().isEmpty()){
           if (fXML.exist("Courses.xml")){
               fXML.deleteFile("Courses");
       }
       }else{

        for (int i = 1; i <= lCourse.size(); i++) {
            Course tempCourse = (Course) lCourse.getNode(i).data;
            try {

                fXML.writeXML("Courses.xml", "Courses", tempCourse.dataName(), tempCourse.data());
            } catch (TransformerException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       }
    }
    
        public void writeSchedules() throws ListException {
        FileXML fXML = new FileXML();
        
        if(Util.Utility.getListSchedule().isEmpty()){
           if (fXML.exist("Schedules.xml")){
               fXML.deleteFile("Schedules");
       }
       }else{
        
        for (int i = 1; i <= lSchedules.size(); i++) {
            TimeTable schedule = (TimeTable) lSchedules.getNode(i).data;
            try {
                fXML.writeXML("Schedules.xml", "Schedules", schedule.dataName(), schedule.data());
            } catch (TransformerException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    }
    

    @FXML
    private void btnLogOut(ActionEvent event) {
        System.out.println("Clic en logout");
        try {
            saveData(); //Se almacena la información de las listas en XMLs
        } catch (ListException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private  void configMenu(){
        if (!Util.Utility.isKindUser()){
            this.menuCareer.setDisable(true);
            this.menuStudent.setDisable(true);
            this.menuUser.setDisable(true);
            this.menuCourse.setDisable(true);
            this.menuSchedules.setDisable(true);
        }
    }
    
    @FXML
    private void mnNewCourse(ActionEvent event) {
        loadPage("/UI/newCourse");
    }

    @FXML
    private void btnModifyCourse(ActionEvent event) {
        loadPage("/UI/modifyCourse");
    }

    @FXML
    private void btnDeleteCourse(ActionEvent event) {
        loadPage("/UI/deleteCourse");
    }

    @FXML
    private void btnShowCourse(ActionEvent event) {
        loadPage("/UI/showCourse");
    }

    @FXML
    private void btnNewSchedule(ActionEvent event) {
        loadPage("/UI/newTimeTable");
    }

    @FXML
    private void btnNewUser(ActionEvent event) {
        loadPage("/UI/newUser");
    }

    @FXML
    private void mnEnrollment(ActionEvent event) throws ListException {
        if(Util.Utility.isKindUser())
        loadPage("/UI/enrollmentTable");
        else
         if(Util.Utility.getCoursesByCarrerID(""+(Util.Utility.getUserStudent().getCareerID())).isEmpty()){
                     callAlert("alert", "Attention!", "Your career does not yet have courses\nwith defined schedules");
                }else  
                    loadPage("/UI/enrollment");    
   
    }

    @FXML
    private void mnDeEnrollment(ActionEvent event) throws ListException {
         if(Util.Utility.isKindUser())
        loadPage("/UI/DeEnrollmentTable");
        else
         if(Util.Utility.getCoursesByCarrerID(""+(Util.Utility.getUserStudent().getCareerID())).isEmpty()){
                     callAlert("alert", "Attention!", "You do not have enrolled courses");
                }else  
                    loadPage("/UI/DeEnrollment"); 
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
