/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.util.Date;

/**
 *
 * @author LuisGa
 */
public class Enrollment {
    
    private int id;
    private Date date;
    private String studentID;
    private String courseID;
    private String schedule;

    public Enrollment(int id, Date date, String studentID, String courseID, String schedule) {
        this.id = id;
        this.date = date;
        this.studentID = studentID;
        this.courseID = courseID;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    
    
}
