/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Luis Gabriel, Sebastián y Alina
 */
public class TimeTable {

    //Atributes
    private String ID;
    private String period;
    private String schedule1;
    private String schedule2;

    //Constructor
    public TimeTable(String ID, String period, String schedule1, String schedule2) {
        this.ID = ID;
        this.period = period;
        this.schedule1 = schedule1;
        this.schedule2 = schedule2;
    }

    public TimeTable() {
    }

    //Setters and getters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    //- - - - - - - - - - - -- - - - - - - -- - -- - - - -- - -
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    //- - - - - - - - - - - -- - - - - - - -- - -- - - - -- - -
    public String getSchedule1() {
        return schedule1;
    }

    public void setSchedule1(String schedule1) {
        this.schedule1 = schedule1;
    }

    //- - - - - - - - - - - -- - - - - - - -- - -- - - - -- - -
    public String getSchedule2() {
        return schedule2;
    }

    public void setSchedule2(String schedule2) {
        this.schedule2 = schedule2;
    }

    //- - - - - - - - - - - -- - - - - - - -- - -- - - - -- - -
    @Override
    public String toString() {
        return "Shedule 1: "+ schedule1 + " , Shedule 2: " + schedule2;
    }

    public String[] dataName() {
        String[] dataName = {"id", "period", "schedule1", "schedule2"};
        return dataName;
    }

    public String[] data() {
        String[] data = {this.getID(), this.getPeriod(), this.schedule1, this.schedule2};
        return data;
    }

}
