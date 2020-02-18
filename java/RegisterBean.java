/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
//import javax.faces.bean.RequestScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean {

    /**
     * Creates a new instance of RegisterBean
     */
    private String username;
    private String password;
    private String gender;
    private String age;
    private String location;
    private String interest;
    private String lastOnline = GetDate.FormatDateTime(); // date of creation/being online
    private int views = 0; // number of profile views by non-friends
    
    private String interest2; //for purpose of testing database, will not be used for this project
    private String interest3; //for purpose of testing database, will not be used for this project
    
    public void validateUsername() throws SQLException{ //check if username already exists
        boolean validUsername = User.checkUsernameExists(username); //return true or false if user already registered
        FacesContext context = FacesContext.getCurrentInstance();
        if(validUsername){ // if username exists
            //insert the new user into the database
            insertUser();
            //redirect to front page
            try {
                //context.getExternalContext().invalidateSession();
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            //display an error
            context.addMessage(null, new FacesMessage("Cannot Create a New Account"));
            
        } 
    }
    
    public void insertUser() throws SQLException{
        Connection con = null;
        Statement st = null;
        try {
            con = Database.getConnection();
            st = con.createStatement();
            int r = st.executeUpdate("insert into Users values" + "('" + username + "', '" + password + "', '" + gender 
                    + "', '" + age + "', '" + location.toLowerCase() + "', '" + interest.toLowerCase() + "', '" + interest2.toLowerCase() + "', '" + interest3.toLowerCase() + "', '"
                    + lastOnline + "', '" + views + "')");
                
            
        } catch (Exception ex) {
            System.out.println("Error in Adding User -->" + ex.getMessage());
           
        } finally {
            Database.close(con);
            st.close();
        }
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getInterest2() {
        return interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public String getInterest3() {
        return interest3;
    }

    public void setInterest3(String interest3) {
        this.interest3 = interest3;
    }
    
    
    
}
