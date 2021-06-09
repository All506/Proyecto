/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Course;
import Objects.DeEnrollment;
import Objects.Enrollment;
import Objects.Student;
import Objects.TimeTable;
import PDF.FilePDF;
import UI.mainFx;
import XML.FileXML;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private CircularDoublyLinkList lCourse;
    private SinglyLinkList lSchedules;
    private CircularDoublyLinkList lEnrollment;
    private CircularDoublyLinkList lDeEnrollment;

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
    @FXML
    private Menu menuReports;
    @FXML
    private MenuItem reportCareer;
    @FXML
    private MenuItem reportStudent;
    @FXML
    private MenuItem reportCourses;
    @FXML
    private Button btnIgnore;
    @FXML
    private MenuItem reportEnrollment;
    @FXML
    private MenuItem reportDeEnrollment;

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
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Attention!", "There are no careers defined yet.");
        } else {
            loadPage("/UI/newStudent");
        }
    }

    @FXML
    private void btnModifyStudent(ActionEvent event) {
        if (Util.Utility.getListStudents().isEmpty()) {
            callAlert("alert", "Attention!", "There are no students registered yet.");
        } else {
            loadPage("/UI/modifyStudent");
        }
    }

    @FXML
    private void btnShowStudents(ActionEvent event) {
        if (Util.Utility.getListStudents().isEmpty()) {
            callAlert("alert", "Attention!", "There are no students registered yet.");
        } else {
            this.bpMenu.setCenter(null);
            loadPage("/UI/showStudent");
        }
    }

    @FXML
    private void btnDeleteStudent(ActionEvent event) {
        if (Util.Utility.getListStudents().isEmpty()) {
            callAlert("alert", "Attention!", "There are no students registered yet.");
        } else {
            loadPage("/UI/deleteStudent");
        }
    }

    @FXML
    private void newCareer(ActionEvent event) {
        loadPage("/UI/NewCareer");
    }

    @FXML
    private void btnShowCareers(ActionEvent event) {
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Attention!", "There are no careers defined yet.");
        } else {
            loadPage("/UI/showCareers");
        }
    }

    @FXML
    private void btnDeleteOrEditCareer(ActionEvent event) {
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Attention!", "There are no careers defined yet.");
        } else {
            loadPage("/UI/EditAndDeleteCareer");
        }
    }

    //Se podría llamar al inicio y final
    public void loadLists() { //Modificarlo para que no se caiga cuando no hay documentos
        SinglyLinkList temp = new SinglyLinkList();
        DoublyLinkList tempCareer = new DoublyLinkList();

        //Se encarga de rellenar las listas desde los XML
        //Se rellena la lista de estudiantes segun el xml Students.xml
        FileXML fXML = new FileXML();
        if (fXML.exist("Students.xml")) {
            lStudents = fXML.readXMLtoStudentList("Students");

            if (!lStudents.isEmpty()) {
                try {
                    for (int i = 1; i <= lStudents.size(); i++) {
                        Util.Utility.setListStudent((Student) lStudents.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);

                    try {
                        for (int i = 1; i <= lStudents.size(); i++) {
                            Util.Utility.setListStudent((Student) lStudents.getNode(i).data);
//                    System.out.println(lStudents.getNode(i).data.toString());

                        }
                    } catch (ListException ex1) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                }

            }
        }

        //Se carga la lista de carreras
        if (fXML.exist("Careers.xml")) {
            lCareers = fXML.readXMLtoCareertList();
            if (!lCareers.isEmpty()) {
                try {
                    for (int i = 1; i <= lCareers.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListCareer((Career) lCareers.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        //Carga y desencripta los cursos
        if (fXML.exist("Courses.xml")) {
            lCourse = fXML.readXMLtoCourseList();
            if (!lCourse.isEmpty()) {
                try {
                    for (int i = 1; i <= lCourse.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListCourse((Course) lCourse.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        //Carga y desencripta los cursos
        if (fXML.exist("Schedules.xml")) {
            lSchedules = fXML.readXMLtoScheduleList();
            if (!lSchedules.isEmpty()) {
                try {

                    for (int i = 1; i <= lSchedules.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListSchedule((TimeTable) lSchedules.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        //Carga los cursos
        if (fXML.exist("Courses.xml")) {
            lCourse = fXML.readXMLtoCourseList();
            if (!lCourse.isEmpty()) {
                try {
                    for (int i = 1; i <= lCourse.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListCourse((Course) lCourse.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        //Carga los Enrollments
        if (fXML.exist("Enrollments.xml")) {
            lEnrollment = fXML.readXMLtoEnrollmentList();

            if (!lEnrollment.isEmpty()) {
                try {
                    for (int i = 1; i <= lEnrollment.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListEnrollment((Enrollment) lEnrollment.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    for (int i = 1; i <= lEnrollment.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListEnrollment((Enrollment) lEnrollment.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Cargar el lastId de enrollments
                Util.Utility.setLastEnroll(fXML.getLastEnroll());
            }
        } else {
            Util.Utility.setLastEnroll(0);
        }

        //Carga los DeEnrollments
        if (fXML.exist("DeEnrollment.xml")) {
            lDeEnrollment = fXML.readXMLtoDeEnrollmentList();

            if (!lDeEnrollment.isEmpty()) {
                try {
                    for (int i = 1; i <= lDeEnrollment.size(); i++) { //Se añaden los objetos del xml a util
                        Util.Utility.setListDeEnrollment((DeEnrollment) lDeEnrollment.getNode(i).data);
                    }
                } catch (ListException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Cargar el lastId de enrollments
                Util.Utility.setLastDeEnroll(fXML.getLastDeEnroll());
            }
        } else {
            Util.Utility.setLastDeEnroll(0);
        }

    }

    @FXML
    public void btnLogOut(ActionEvent event) throws IOException, Exception {
        deletePDF(); //Elimina los pdf creados para que no haya ningun malentendido con los datos
        Util.SaveData save = new Util.SaveData(); //Se almacena la información de las listas en XMLs
        save.saveData();

        //--------------------------------------------------------------------------------
        Stage stage = (Stage) this.btnIgnore.getScene().getWindow();
        mainFx m = new mainFx();
        m.start(stage);
        //--------------------------------------------------------------------------------

    }

    private void configMenu() { //Para ocultar los botones del menu Controller
        if (!Util.Utility.isKindUser()) { //Si el usuario es falso, quien entró es un estudiante
            this.menuCareer.setVisible(false);
            this.menuStudent.setVisible(false);
            this.btnNewUser.setDisable(true);
            this.btnNewUser.setVisible(false);
            this.menuCourse.setVisible(false);
            this.menuSchedules.setVisible(false);
            this.reportCareer.setVisible(false);
            this.reportCourses.setVisible(false);
            this.reportStudent.setVisible(false);
        }
    }

    @FXML
    private void mnNewCourse(ActionEvent event) {
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Attention!", "There are no careers defined yet.");
        } else {
            loadPage("/UI/newCourse");
        }
    }

    @FXML
    private void btnModifyCourse(ActionEvent event) {
        if (Util.Utility.getListCourse().isEmpty()) {
            callAlert("alert", "Attention!", "There are no courses defined yet.");
        } else {
            loadPage("/UI/modifyCourse");
        }
    }

    @FXML
    private void btnDeleteCourse(ActionEvent event) {
        if (Util.Utility.getListCourse().isEmpty()) {
            callAlert("alert", "Attention!", "There are no courses defined yet.");
        } else {
            loadPage("/UI/deleteCourse");
        }
    }

    @FXML
    private void btnShowCourse(ActionEvent event) {
        if (Util.Utility.getListCourse().isEmpty()) {
            callAlert("alert", "Attention!", "There are no courses defined yet.");
        } else {
            loadPage("/UI/showCourse");
        }
    }

    @FXML
    private void btnNewSchedule(ActionEvent event) {
        if (Util.Utility.getListCourse().isEmpty()) {
            callAlert("alert", "Attention!", "There are no courses defined yet.");
        } else {
            loadPage("/UI/newTimeTable");
        }
    }

    @FXML
    private void btnNewUser(ActionEvent event) {
        loadPage("/UI/newUser");
    }

    @FXML
    private void mnEnrollment(ActionEvent event) throws ListException {
        if (Util.Utility.isKindUser()) {
            loadPage("/UI/enrollmentTable");
        } else if (Util.Utility.getCoursesByCarrerID("" + (Util.Utility.getUserStudent().getCareerID())).isEmpty()) {
            callAlert("alert", "Attention!", "Your career does not yet have courses\nwith defined schedules");
        } else {
            loadPage("/UI/enrollment");
        }

    }

    @FXML
    private void mnDeEnrollment(ActionEvent event) throws ListException {
        if (Util.Utility.isKindUser()) {
            loadPage("/UI/DeEnrollmentTable");
        } else if (Util.Utility.getCoursesByCarrerID("" + (Util.Utility.getUserStudent().getCareerID())).isEmpty()) {
            callAlert("alert", "Attention!", "You do not have enrolled courses");
        } else {
            if (!Util.Utility.getEnrollmentOfStudentId().isEmpty()) {
                loadPage("/UI/DeEnrollment");
            } else {
                callAlert("alert", "Attention!", "You do not have enrolled courses");
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
    private void reportCareer(ActionEvent event) {
        if (Util.Utility.getListCareer().isEmpty()) {
            callAlert("alert", "Attention!", "There are no careers registered yet.");
        } else {
            String pdfName = "Report Careers";
            FilePDF pdf = new FilePDF();
            try {
                if (!pdf.exist(pdfName)) {
                    pdf.careerPDF(pdfName, Util.Utility.getListCareer());
                } else {
                    pdf.deleteFile("Report Careers");
                    pdf.careerPDF(pdfName, Util.Utility.getListCareer());
                }
            } catch (DocumentException | IOException | URISyntaxException e) {
                System.out.println("Error: " + e);
            }
            callAlert("notification", "Notification", "Careers' report has been created");
        }
    }

    @FXML
    private void reportStudent(ActionEvent event) {
        if (Util.Utility.getListStudents().isEmpty()) {
            callAlert("alert", "Attention!", "There are no students registered yet.");
        } else {
            String pdfName = "Report Students";
            FilePDF pdf = new FilePDF();
            try {
                if (!pdf.exist(pdfName)) {
                    pdf.studentPDF(pdfName, Util.Utility.getListStudents());
                } else {
                    pdf.deleteFile("Report Students");
                    pdf.studentPDF(pdfName, Util.Utility.getListStudents());
                }
            } catch (DocumentException | IOException | URISyntaxException e) {
                System.out.println("Error: " + e);
            }
            callAlert("notification", "Notification", "Students' report has been created");
        }
    }

    @FXML
    private void reportCourses(ActionEvent event) {
        if (Util.Utility.getListCourse().isEmpty()) {
            callAlert("alert", "Attention!", "There are no courses registered yet.");
        } else {
            String pdfName = "Report Courses";
            FilePDF pdf = new FilePDF();
            try {
                if (!pdf.exist(pdfName)) {
                    pdf.coursePDF(pdfName, Util.Utility.getListCourse());
                } else {
                    pdf.deleteFile("Report Courses");
                    pdf.coursePDF(pdfName, Util.Utility.getListCourse());
                }
            } catch (DocumentException | IOException | URISyntaxException e) {
                System.out.println("Error: " + e);
            }
            callAlert("notification", "Notification", "Courses' report has been created");
        }
    }

    @FXML
    private void reportEnrollment(ActionEvent event) {
        if (Util.Utility.getListEnrollment().isEmpty()) {
            callAlert("alert", "Attention!", "There are no enrollments registered yet.");
        } else {

            String pdfStudents = "Report Enrollments";
            String pdfStudent = "Report Enrollment Student";
            FilePDF pdf = new FilePDF();
            boolean kindUser = Util.Utility.isKindUser();
            try {
                if (kindUser) {//Admin

                    if (!pdf.exist(pdfStudents)) {
                        pdf.enrollmentPDF(pdfStudents, Util.Utility.getListEnrollment());
                    } else {
                        pdf.deleteFile("Report Enrollments");
                        pdf.enrollmentPDF(pdfStudents, Util.Utility.getListEnrollment());
                    }

                    callAlert("notification", "Notification", "Report Enrollments has been create");

                } else {//Student

                    if (!pdf.exist(pdfStudent)) {
                        pdf.enrollmentStudentPDF(pdfStudent, Util.Utility.getListEnrollment(), Util.Utility.getUserStudent());
                    } else {
                        pdf.deleteFile("Report Enrollment Student");
                        pdf.enrollmentStudentPDF(pdfStudent, Util.Utility.getListEnrollment(), Util.Utility.getUserStudent());
                    }
                    callAlert("notification", "Notification", "Report Student Enrollment has been create");

                }

            } catch (DocumentException | IOException | URISyntaxException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @FXML
    private void reportDeEnrollment(ActionEvent event) {
        if (Util.Utility.getListDeEnrollment().isEmpty()) {
            callAlert("alert", "Attention!", "There are no De-enrollments\nregistered yet.");
        } else {

            String pdfName = "Report DeEnrollments";
            String pdfStudent = "Report DeEnrollment Student";
            FilePDF pdf = new FilePDF();
            boolean kindUser = Util.Utility.isKindUser();
            try {
                if (kindUser) {//Admin

                    if (!pdf.exist(pdfName)) {
                        pdf.deEnrollmentPDF(pdfName, Util.Utility.getListDeEnrollment());
                    } else {
                        pdf.deleteFile("Report DeEnrollments");
                        pdf.deEnrollmentPDF(pdfName, Util.Utility.getListDeEnrollment());
                    }
                    callAlert("notification", "Notification", "DeEnrollments' report has been create");

                } else {//Student

                    if (!pdf.exist(pdfStudent)) {
                        pdf.deEnrollmentStudentPDF(pdfStudent, Util.Utility.getListDeEnrollment(), Util.Utility.getUserStudent());
                    } else {
                        pdf.deleteFile("Report DeEnrollments Student");
                        pdf.deEnrollmentStudentPDF(pdfStudent, Util.Utility.getListDeEnrollment(), Util.Utility.getUserStudent());
                    }
                    callAlert("notification", "Notification", "Student DeEnrollments' reports has been create");

                }
            } catch (DocumentException | IOException | URISyntaxException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    public void deletePDF() {
        FilePDF pdf = new FilePDF();
        try {
            if (pdf.exist("Report Students")) {
                pdf.deleteFile("Report Students");
            }
            if (pdf.exist("Report Careers")) {
                pdf.deleteFile("Report Careers");
            }
            if (pdf.exist("Report Courses")) {
                pdf.deleteFile("Report Courses");
            }
            if (pdf.exist("Report Enrollments")) {
                pdf.deleteFile("Report Enrollments");
            }
            if (pdf.exist("Report DeEnrollments")) {
                pdf.deleteFile("Report DeEnrollments");
            }
            if (pdf.exist("Report Enrollment Student")) {
                pdf.deleteFile("Report Enrollment Student");
            }
            if (pdf.exist("Report DeEnrollment Student")) {
                pdf.deleteFile("Report DeEnrollment Student");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}//end class
