/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.AlertController;
import Controller.LogInController;
import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Course;
import Objects.Enrollment;
import Objects.Security;
import Objects.Student;
import Objects.TimeTable;
import Security.AES;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.text.MaskFormatter;

public class Utility {

    //Constructores de las listas
    private static SinglyLinkList lStudent = new SinglyLinkList();
    private static SinglyLinkList lSchedule = new SinglyLinkList();
    private static DoublyLinkList lCareer = new DoublyLinkList();
    private static CircularLinkList lCourse = new CircularLinkList();
    private static CircularLinkList lSecurity = new CircularLinkList();
    private static CircularDoublyLinkList lEnrollment = new CircularDoublyLinkList();
    private static boolean kindUser = false; //True if user, false if Student
    private static Student userStudent = null;

    //GETS DE LAS LISTAS 
    public static SinglyLinkList getListStudents() {
        return lStudent;
    }
    
    public static SinglyLinkList getListSchedule() {
        return lSchedule;
    }

    public static DoublyLinkList getListCareer() {
        return lCareer;
    }

    public static CircularLinkList getListCourse() {
        return lCourse;
    }

    public static CircularLinkList getListSecurity() {
        return lSecurity;
    }
    
    public static CircularDoublyLinkList getListEnrollment() {
        return lEnrollment;
    }

    public static boolean isKindUser() {
        return kindUser;
    }

    public static void setKindUser(boolean kindUser) {
        Utility.kindUser = kindUser;
        
    }
    
    public static void setKindUser(boolean kindUser,String userID) throws ListException {
        Utility.kindUser = kindUser;
        Utility.userStudent=getStudentByID(userID);
    }
    
    public static Student getUserStudent() {
        return userStudent;
    }

    //DELETE NODES DE LAS LISTA
    public static void deleteNodeLStudent(Student std) {
        try {
            if (lStudent.contains(std)) {
                lStudent.remove(std);
            }
        } catch (ListException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //SETLIST DE LAS LISTAS
    public static boolean setListStudent(Student std) throws ListException {
        boolean flag = false;
        if (Utility.lStudent.isEmpty()) {
            Utility.lStudent.add(std);
            flag = true;
        } else {
            if (!lStudent.contains(std)) {
                lStudent.add(std);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public static boolean setListSchedule(TimeTable sch) throws ListException {
        boolean flag = false;
        if (Utility.lSchedule.isEmpty()) {
            Utility.lSchedule.add(sch);
            flag = true;
        } else {
            if (!lSchedule.contains(sch)) {
                lSchedule.add(sch);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
    
    public static boolean setListCareer(Career car) throws ListException {
        boolean flag = false;
        if (Utility.lCareer.isEmpty()) {
            car.setIDConsecutivo();
            Utility.lCareer.add(car);
            flag = true;
        } else {
            if (!lCareer.contains(car)) {
                car.setIDConsecutivo();
                lCareer.add(car);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public static boolean setListCourse(Course cou) throws ListException {
        boolean flag = false;
        if (Utility.lCourse.isEmpty()) {
            Utility.lCourse.add(cou);
            flag = true;
        } else {
            if (!lCourse.contains(cou)) {
                lCourse.add(cou);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public static boolean setListSecurity(Security sec) throws ListException {
        boolean flag = false;
        if (Utility.lSecurity.isEmpty()) {
            Utility.lSecurity.add(sec);
            flag = true;
        } else {
            if (!lSecurity.contains(sec)) {
                lSecurity.add(sec);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
    
    public static boolean setListEnrollment(Enrollment enr) throws ListException {
        boolean flag = false;
        if (Utility.lEnrollment.isEmpty()) {
            Utility.lEnrollment.add(enr);
            flag = true;
        } else {
            if (!lEnrollment.contains(enr)) {
                lEnrollment.add(enr);
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
    
    //UTILIDAD 

    public static int random() {
        return 1 + (int) Math.floor(Math.random() * 99);
    }

    public static int random(int bound) {
        //return 1+random.nextInt(bound);
        return 1 + (int) Math.floor(Math.random() * bound);
    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }
    
    public static String hourFormat(int value) {
        return new DecimalFormat("00")
                .format(value);
    }

    public static String $format(double value) {
        return new DecimalFormat("$###,###,###,###.##")
                .format(value);
    }

    public static String perFormat(double value) {
        //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static boolean equals(Object a, Object b) { //Objeto de lista, objeto buscado
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x.equals(y); //Devuelve un booleano comparando los enteros
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.equalsIgnoreCase(s2); //Devuelve un booleano comparando los String
            case "student":
                Student std1 = (Student) a;
                Student std2 = (Student) b;
                return std1.getStudentID().equalsIgnoreCase(std2.getStudentID());
            case "career":
                Career car1 = (Career) a;
                Career car2 = (Career) b;
                return car1.getDescription().compareTo(car2.getDescription()) == 0;
            case "course":
                Course cour1 = (Course) a;
                Course cour2 = (Course) b;
                return cour1.getId().equalsIgnoreCase(cour2.getId()) && cour1.getName().equalsIgnoreCase(cour2.getName()) ;
            case "security":
                Security sec1 = (Security) a;
                Security sec2 = (Security) b;
                return sec1.getUser().equals(sec2.getUser()) && sec1.getPassword().equals(sec2.getPassword());
        }
        return false; //En cualquier otro caso retorna un false
    }

    private static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) { //Es a un entero?
            return "integer";
        }
        if (a instanceof String && b instanceof String) { //Es a un entero?
            return "string";
        }
        if (a instanceof Student && b instanceof Student) {
            return "student";
        }
        if (a instanceof Career && b instanceof Career) {
            return "career";
        }
        if (a instanceof Security && b instanceof Security) {
            return "security";
        }
        if (a instanceof Course && b instanceof Course) {
            return "course";
        }

        return "unknown";
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy")
                .format(date);
    }

    public static boolean emailChecker(String email) {
        //Just send the email and return a boolean if it matches the mail format
        //Nobody knows how the hell the pattern works but it works so...

        //Patrón del correo      
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher coincidence = pattern.matcher(email);
        return coincidence.find();
    }

    public static String getIDofString(String s){
    
    int i = 0;
        String x = "";
        while(!("-").contains(""+s.charAt(i))){
                    x += s.charAt(i);
                    i++;
        }
    
    return x;
    }

    private static Student getStudentByID(String id) throws ListException {
        
        for (int i = 1; i <= lStudent.size(); i++) {
            Student s =(Student) lStudent.getNode(i).data;
            if((s.getId()+"").equals(id)){
            return s;
            
            }
        }
    return null;
    }

    
    public static void replaceListCourse(CircularLinkList listToSend) {
        lCourse = listToSend;
    }
    
}
