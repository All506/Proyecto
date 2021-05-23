/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author <Sebastián Navarro Martínez (C05510)>
 */
public class Course {
    
   //Atributes
    private String id;
    private String name;
    private int credits;
    private int careerId;
    
    //Constructor
    public Course(String id, String name, int credits, int careerID) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.careerId = careerID;
    }
    
    //Constructor for the table
    public Course(Course crse){
        this.id = crse.id;
        this.name = crse.name;
        this.credits = crse.credits;
        this.careerId = crse.careerId; 
    }

    public Course() {
        
    }
    
    //Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCareerId() {
        return careerId;
    }

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }
    
    //Stringers
    @Override
    public String toString() {
        return "Course[" + "id=" + id + ", name=" + name + ", credits=" + credits + ", careerID=" + careerId + ']';
    }
    
    public String[] dataName(){
        String[] dataName = {"id","careerId","name","credits"};
        return dataName;
    }
    
    public String[] data(){
        String[] data = {id,Integer.toString(careerId),name,Integer.toString(credits)};
        return data;
    }
}
