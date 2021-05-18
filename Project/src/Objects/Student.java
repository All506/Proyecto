package Objects;

import java.util.Date;


public class Student {
    //ATRIBUTOS DE LA CLASE
    private int id, careerID;
    private String studentID, lastname, firstname, phoneNumber, email, address;
    private java.util.Date birthday;

    public Student(int id, int careerID, String studentID, String lastname, String firstname, String phoneNumber, String email, String address, Date birthday) {
        this.id = id;
        this.careerID = careerID;
        this.studentID = studentID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }

    //Constructor para las tablas
    public Student(Student std) {
        this.id = std.id;
        this.careerID = std.careerID;
        this.studentID = std.studentID;
        this.lastname = std.lastname;
        this.firstname = std.firstname;
        this.phoneNumber = std.phoneNumber;
        this.email = std.email;
        this.address = std.address;
        this.birthday = std.birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCareerID() {
        return careerID;
    }

    public void setCareerID(int careerID) {
        this.careerID = careerID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", careerID=" + careerID + ", studentID=" + studentID + ", lastname=" + lastname + ", firstname=" + firstname + ", phoneNumber=" + phoneNumber + ", email=" + email + ", address=" + address + ", birthday=" + birthday + ']';
    }

    public String[] dataName() {
        String[] dataName = {"studentId","id", "carrerId", "lastName", "firstName", "phoneNumber", "email", "address", "birthday"};
        return dataName;
    }

    public String[] data() {
        String[] data = {studentID,Integer.toString(id), Integer.toString(careerID), lastname, firstname, phoneNumber, email, address, birthday.toString()};
        return data;
    }
}
