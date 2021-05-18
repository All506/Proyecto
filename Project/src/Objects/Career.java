/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Alán
 */
public class Career {
    
    int id;
    String description;

    public Career(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public Career(Career car){
        this.id = car.getId();
        this.description = car.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Career{" + "id=" + id + ", description=" + description + '}';
    }
    
     public String[] dataName() {
        String[] dataName = {"id","description"};
        return dataName;
    }

    public String[] data() {
        String[] data = {String.valueOf(id),this.getDescription()};
        return data;
    }
    
}
