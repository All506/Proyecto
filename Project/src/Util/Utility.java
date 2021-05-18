/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.AlertController;
import Controller.LogInController;
import Objects.Career;
import Domain.DoublyLinkList;
import Domain.ListException;
import Domain.SinglyLinkList;
import Objects.Student;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utility {

    //Constructores de las listas
    private static SinglyLinkList lStudent = new SinglyLinkList();
    private static DoublyLinkList lCareer = new DoublyLinkList();

    //GETS DE LAS LISTAS 
    public static SinglyLinkList getListStudents() {
        return lStudent;
    }
    
    public static DoublyLinkList getListCareer() {
        return lCareer;
    }

    //DELETE NODES DE LAS LISTAS
    public static void deleteNodeLStudent(Student std){
        try {
            if (lStudent.contains(std)){
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
    
     public static boolean setListCareer(Career car) throws ListException {
        boolean flag = false;
        if (Utility.lCareer.isEmpty()) {
            Utility.lCareer.add(car);
            flag = true;
        } else {
            if (!lCareer.contains(car)) {
                lCareer.add(car);
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
                return car1.getId() == car2.getId();
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

        return "unknown";
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy")
                .format(date);
    }

}
