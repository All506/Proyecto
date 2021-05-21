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
public class Security {
    private String user;
    private String password;

    public Security(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public Security(Security sec){
        this.user = sec.getUser();
        this.password = sec.getPassword();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
 


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Security{" + "user=" + user + ", password=" + password + '}';
    }
    
    public String[] dataName(){
        String[] dataName = {"user","password"};
        return dataName;
    }
    
    public String[] data(){
        String[] data = {this.getUser(),this.password};
        return data;
    }
}
