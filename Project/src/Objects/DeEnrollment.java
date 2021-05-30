/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author LuisGa
 */
public class DeEnrollment {
    
    private int id;
    private Date date;
    private String studentID;
    private String courseID;
    private String schedule;
    private String remark;

    public DeEnrollment(int id, Date date, String studentID, String courseID, String schedule,String remark) {
        this.id = id;
        this.date = date;
        this.studentID = studentID;
        this.courseID = courseID;
        this.schedule = schedule;
        this.remark=remark;
        
    }

    public DeEnrollment() {
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

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DeEnrollment{" + "id=" + id + ", date=" + date + ", studentID=" + studentID + ", courseID=" + courseID + ", schedule=" + schedule + ", remark=" + remark + '}';
    }
    
    public String[] dataName(){
        String[] data = {"id","date","studentId","courseId","schedule"};
        return data;
    }
    
    public String[] getData(){
        LocalDate dataFormat = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(this.date));
        String[] data = {String.valueOf(id),dataFormat.toString(),this.studentID, this.courseID, this.schedule};
        return data;
    }
    
}
