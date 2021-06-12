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
    
    private int id;
    private String description;

    public static int consecutivo;
    
    public Career(int id, String description) {
        this.description = description;
    }
    
    public static void setConsecuntivo(int size){
        consecutivo = size;
    }
    
    public void setIDConsecutivo(){
        this.id = consecutivo;
        consecutivo++;
    }
    
    public int getConsecutivo(){
        return consecutivo;
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
