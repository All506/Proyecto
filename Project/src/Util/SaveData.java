/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.MenuController;
import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Career;
import Objects.Course;
import Objects.DeEnrollment;
import Objects.Enrollment;
import Objects.Security;
import Objects.Student;
import Objects.TimeTable;
import Security.AES;
import XML.FileXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Alán
 */
public class SaveData {

        SinglyLinkList lStudents = Util.Utility.getListStudents(); //Se carga la lista de estudiantes al XML
        DoublyLinkList lCareers = Util.Utility.getListCareer();
        CircularLinkList lSecurity = Util.Utility.getListSecurity();
        CircularDoublyLinkList lCourse = Util.Utility.getListCourse();
        SinglyLinkList lSchedules = Util.Utility.getListSchedule();
        CircularDoublyLinkList lEnrollment = Util.Utility.getListEnrollment();

       public void saveData() throws ListException {

        lStudents = Util.Utility.getListStudents(); //Se carga la lista de estudiantes al XML
        lCareers = Util.Utility.getListCareer();
        lSecurity = Util.Utility.getListSecurity();
        lCourse = Util.Utility.getListCourse();
        lSchedules = Util.Utility.getListSchedule();
        lEnrollment = Util.Utility.getListEnrollment();

        FileXML fXML = new FileXML();

        //Guardar datos de los estudiantes
        if (!fXML.exist("Students.xml")) { //Si el archivo no existe
            fXML.createXML("StudentsXML", "Students");
            if (!Util.Utility.getListStudents().isEmpty()) {
                writeStudents();
            }
        } else {
            fXML.deleteFile("Students");
            fXML.createXML("StudentsXML", "Students");
            if (!Util.Utility.getListStudents().isEmpty()) {
                writeStudents();
            }
        }

        //Guardar datos de las carreras
        if (!fXML.exist("Careers.xml")) { //Si el archivo no existe
            fXML.createXML("CareersXML", "Careers");
            if (!Util.Utility.getListCareer().isEmpty()) {
                writeCareers();
            }
        } else {
            fXML.deleteFile("Careers");
            fXML.createXML("CareersXML", "Careers");
            if (!Util.Utility.getListCareer().isEmpty()) {
                writeCareers();
            }
        }

        //Guardar datos encriptados en XML de User
        if (!fXML.exist("Security.xml")) { //Si el archivo no existe
            fXML.createXML("SecurityXML", "Security");
            if (!Util.Utility.getListSecurity().isEmpty()) {
                writeSecurity();
            }
        } else {
            fXML.deleteFile("Security");
            fXML.createXML("SecurityXML", "Security");
            if (!Util.Utility.getListSecurity().isEmpty()) {
                writeSecurity();
            }
        }

        //Guarda datos de la lista de cursos en XML
        if (!fXML.exist("Courses.xml")) { //Si el archivo no existe
            fXML.createXML("CoursesXML", "Courses");
            if (!Util.Utility.getListCourse().isEmpty()) {
                writeCourses();
            }
        } else {
            fXML.deleteFile("Courses");
            fXML.createXML("CoursesXML", "Courses");
            if (!Util.Utility.getListCourse().isEmpty()) {
                writeCourses();
            }
        }

        //Guarda datos de la lista de horarios en XML
        if (!fXML.exist("Schedules.xml")) { //Si el archivo no existe
            fXML.createXML("SchedulesXML", "Schedules");
            if (!Util.Utility.getListSchedule().isEmpty()) {
                writeSchedules();
            }
        } else {
            fXML.deleteFile("Schedules");
            fXML.createXML("SchedulesXML", "Schedules");
            if (!Util.Utility.getListSchedule().isEmpty()) {
                writeSchedules();
            }
        }

        //Guarda datos de la lista de Enrollment en XML
        if (!fXML.exist("Enrollments.xml")) { //Si el archivo no existe
            fXML.createXML("EnrollmentsXML", "Enrollments");
            if (!Util.Utility.getListEnrollment().isEmpty()) {
                writeEnrollments();
            }
        } else {
            fXML.deleteFile("Enrollments");
            fXML.createXML("EnrollmentsXML", "Enrollments");
            if (!Util.Utility.getListEnrollment().isEmpty()) {
                writeEnrollments();
            }
            
        }


            System.out.println(Util.Utility.getListEnrollment().toString());

        //Guarda datos de la lista de DeEnrollment en XML
        if (!fXML.exist("DeEnrollment.xml")) { //Si el archivo no existe
            fXML.createXML("DeEnrollmentXML", "DeEnrollment");
            if (!Util.Utility.getListDeEnrollment().isEmpty()) {
                writeDeEnrollment();
            }

        } else {
            fXML.deleteFile("DeEnrollment");
            fXML.createXML("DeEnrollmentXML", "DeEnrollment");
            if (!Util.Utility.getListDeEnrollment().isEmpty()) {
                writeDeEnrollment();
            }
        }
    }

    public void writeStudents() throws ListException {

        FileXML fXML = new FileXML();

        if (Util.Utility.getListStudents().isEmpty()) {
            if (fXML.exist("Students.xml")) {
                fXML.deleteFile("Students");
            }
        } else {

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

        if (Util.Utility.getListCareer().isEmpty()) {
            if (fXML.exist("Careers.xml")) {
                fXML.deleteFile("Careers");
            }
        } else {

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

        if (Util.Utility.getListSecurity().isEmpty()) {
            if (fXML.exist("Security.xml")) {
                fXML.deleteFile("Security");
            }
        } else {

            AES encrypt = new AES();
            for (int i = 1; i <= lSecurity.size(); i++) {
                Security sec = (Security) lSecurity.getNode(i).data;
                //Se encripta la información y se guarda
                Security encSec = new Security(encrypt.encrypt(sec.getUser(), "Proyecto"), encrypt.encrypt(sec.getPassword(), "Proyecto"));

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

        if (Util.Utility.getListCourse().isEmpty()) {
            if (fXML.exist("Courses.xml")) {
                fXML.deleteFile("Courses");
            }
        } else {

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

        if (Util.Utility.getListSchedule().isEmpty()) {
            if (fXML.exist("Schedules.xml")) {
                fXML.deleteFile("Schedules");
            }
        } else {

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

    public void writeEnrollments() throws ListException {
        FileXML fXML = new FileXML();

        if (Util.Utility.getListEnrollment().isEmpty()) {
            if (fXML.exist("Enrollments.xml")) {
                fXML.deleteFile("Enrollments");
            }
        } else {

            for (int i = 1; i <= lEnrollment.size(); i++) {
                Enrollment enr = (Enrollment) lEnrollment.getNode(i).data;
                try {
                    fXML.writeXML("Enrollments.xml", "Enrollments", enr.dataName(), enr.getData());
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
 
    public void writeDeEnrollment() throws ListException {
        FileXML fXML = new FileXML();

        if (Util.Utility.getListDeEnrollment().isEmpty()) {
            if (fXML.exist("DeEnrollment.xml")) {
                fXML.deleteFile("DeEnrollment");
            }
        } else {

            CircularDoublyLinkList listDeEnrollment = Util.Utility.getListDeEnrollment();
            for (int i = 1; i <= listDeEnrollment.size(); i++) {
                DeEnrollment enr = (DeEnrollment) listDeEnrollment.getNode(i).data;
                try {
                    fXML.writeXML("DeEnrollment.xml", "DeEnrollment", enr.dataName(), enr.getData());
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
}
