/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.DeEnrollment;
import Objects.Enrollment;
import Objects.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class DeEnrollmentController implements Initializable {

    @FXML
    private Text txt;
    @FXML
    private TextField txfStudentID;
    @FXML
    private TextField txfLastName;
    @FXML
    private TextField txfFirstName;

    @FXML
    private TextField txfPersID;
    @FXML
    private TextField txfBirthday;
    @FXML
    private TextField txfPhoneNumber;
    @FXML
    private TextField txfEmail;
    @FXML
    private TextField txfAddress;
    @FXML
    private TextField txfCarrer;
    @FXML
    private TableView<List<String>> tblCourses;
    @FXML
    private TableColumn<List<String>, String> colID;
    @FXML
    private TableColumn<List<String>, String> colDescription;
    @FXML
    private TableColumn<List<String>, String> colSchedule;
    @FXML
    private Button btnDeEnroll;

    Enrollment enroll;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Text txtRemark;
    @FXML
    private TextArea txtArea;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;
    @FXML
    private BorderPane bpRoot;
    /**
     * Initializes the controller class.
     */
    Student temp;
    String course;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDeEnroll.setVisible(false);
        rectangle.setVisible(false);
        txtRemark.setVisible(false);
        txtArea.setVisible(false);
        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        txtArea.setText("");

        try {
            Student s = Util.Utility.getUserStudent();
            temp = s;
            txfStudentID.setText(s.getStudentID());
            txfPersID.setText("" + s.getId());
            txfFirstName.setText(s.getFirstname());
            txfLastName.setText(s.getLastname());
            txfAddress.setText(s.getAddress());
            txfBirthday.setText(Util.Utility.dateFormat(s.getBirthday2()));
            txfEmail.setText(s.getEmail());
            txfPhoneNumber.setText(s.getPhoneNumber());
            txfCarrer.setText(Util.Utility.getCarrerByID("" + s.getCareerID()).getDescription());
            loadTable();
        } catch (ListException ex) {
            Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        colID.setOnEditStart(data -> {

            try {
                course = "";
                enroll = getScheduleByCourseID(data.getOldValue());

                btnDeEnroll.setVisible(true);
                btnDeEnroll.setText("De-enroll course: " + Util.Utility.getCourseByID(data.getOldValue()).getName() + "   -   Schedule: " + enroll.getSchedule());
                java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
                course = "Shedule:" + Util.Utility.getPeriodOfStringDate(d) + " / Course: " + Util.Utility.getCourseByID(data.getOldValue()).getId() + "-" + Util.Utility.getCourseByID(data.getOldValue()).getName() + " / Credits:" + Util.Utility.getCourseByID(data.getOldValue()).getCredits()
                        + " / Schedule: " + enroll.getSchedule();
            } catch (ListException ex) {
                Logger.getLogger(DeEnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public Enrollment getScheduleByCourseID(String courseId) throws ListException {

        SinglyLinkList list = Util.Utility.getEnrollmentOfStudentId();
        for (int i = 1; i <= list.size(); i++) {
            Enrollment e = (Enrollment) list.getNode(i).data;
            if (e.getCourseID().equals(courseId)) {
                return e;
            }
        }
        return null;
    }

    public void loadTable() throws ListException {

        colID.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(0));
            }
        });

        colDescription.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(1));
            }
        });

        colSchedule.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().get(2));
            }
        });

        tblCourses.setItems(getData());
    }

    ;
    
    public ObservableList<List<String>> getData() throws ListException {

        SinglyLinkList list = Util.Utility.getEnrollmentOfStudentId();

        final ObservableList<List<String>> data = FXCollections.observableArrayList();
        if (!list.isEmpty()) {

            for (int i = 1; i <= list.size(); i++) {
                Enrollment e = (Enrollment) list.getNode(i).data;
                List<String> arrayList = new ArrayList<>();
                arrayList.add(e.getCourseID());

                arrayList.add(Util.Utility.getCourseByID(e.getCourseID()).getName());
                arrayList.add(e.getSchedule());
                data.add(arrayList);

            }

        }

        return data;
    }

    @FXML
    private void btnDeEnroll(ActionEvent event) throws ListException {

        rectangle.setVisible(true);
        txtRemark.setVisible(true);
        txtArea.setVisible(true);
        btnConfirm.setVisible(true);
        btnCancel.setVisible(true);
        btnDeEnroll.setDisable(true);

    }

    @FXML
    private void btnConfirm(ActionEvent event) throws ListException, Exception {
        if (!txtArea.getText().equalsIgnoreCase("")) {
            java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
            int temp = Util.Utility.getLastDeEnroll();
            DeEnrollment newDeEnroll = new DeEnrollment(temp + 1, d, enroll.getStudentID(), enroll.getCourseID(), enroll.getSchedule(), txtArea.getText());
            Util.Mail.deEnrollmentEmail(txfEmail.getText(), this.temp.data(), txtArea.getText(), course);
            System.out.println("El enroll a registrar es: " + newDeEnroll.toString());
            Util.Utility.setListDeEnrollment(newDeEnroll);
            Util.Utility.removeEnrollment(newDeEnroll);
            btnCancel(event);
            btnDeEnroll.setVisible(false);
            loadPage("/UI/DeEnrollment");
            callAlert("notification", "Notification", "Successfully de-enrolled course");
        } else {
            callAlert("alert", "Error", "Remark is a required field");
        }
        course = "";
    }

    @FXML
    private void btnCancel(ActionEvent event) {

        rectangle.setVisible(false);
        txtRemark.setVisible(false);
        txtArea.setVisible(false);
        btnConfirm.setVisible(false);
        btnCancel.setVisible(false);
        txtArea.setText("");
        btnDeEnroll.setDisable(false);

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

    private void loadPage(String pageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(pageName + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(EnrollmentTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bpRoot.setCenter(root);
    } // Fin método Load Page

}
