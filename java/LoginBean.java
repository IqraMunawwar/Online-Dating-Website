/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.inject.Named;
//import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable { // gets validates and gets the login for a registred user.

    /**
     * Creates a new instance of LoginBean
     */
    private String loginUsername;
    private String loginPassword;
    
    protected User loggedUser = null; // to store logged in user
    

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    
    public void validateLogin() throws ParseException{ // validate and finally login by creating session and a object
        boolean validUser = User.checkUsername(loginUsername, loginPassword); // validate user entered stuff
        FacesContext context = FacesContext.getCurrentInstance();
        if(validUser){
            context.getExternalContext().getSessionMap().put("user", loginUsername);
            UpdateOnlineDate();
            loginUser();
            try {
                context.getExternalContext().redirect("Home.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            context.addMessage(null, new FacesMessage("Login Failed, please check the username and/or password."));
        } 
    }
    
    public User loginUser() throws ParseException{ // add the logged in username to a user object
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String q = "select * from users where username = '" + loginUsername + "'";
            
        
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            if(rs.next())
            {                
                loggedUser = new User(rs.getString("username"),rs.getString("password"),rs.getString("gender"),
                                rs.getString("age"),rs.getString("city"),rs.getString("interest1"),rs.getString("interest2"),
                                rs.getString("interest3"),  GetDate.FormatDateTime2(rs.getString("lastonline")), rs.getInt("views"));
            }   

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 rs.close();
                 stmt.close();
                 con.close();
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
        return loggedUser;
    }

    public void logout() { // invalid current session and logout
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        UpdateOnlineDate();
        loggedUser = null;
        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getUnreadCount(String s){ //get the number of unread messages from overall
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String q = "select count(*) from (select username2 from messages where username2 = '" + loginUsername + "' and seen = 'unread') as msg";
        int count = 0;    
        
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            if(rs.next())
            {                
                count = rs.getInt(1);
            }                                                                      
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 rs.close();
                 stmt.close();
                 con.close();
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
        
        return count;
    }
    
    //update login date
    public void UpdateOnlineDate(){ // update the last online date on login and logoff
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement st = null;      
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");
            st = con.createStatement();
            int r = st.executeUpdate("Update Users set lastonline = '" + GetDate.FormatDateTime() + "' " 
                    + "where username = '" + loginUsername + "'");   
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 st.close();                                 
                 con.close();    
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
    }
    
    public int getFriendCount(String f){ //get the friend count of a user in requests, matches, or friends list
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String q = "select count(*) from (select username1 as username from friends where username2 = '"+ loginUsername +"' and status = 'accepted' union select username2 as username from friends where username1 = '"+ loginUsername +"' and status = 'accepted') as friends";
        int count = 0;    
        
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            if(rs.next())
            {                
                count = rs.getInt(1);
            }                                                                      
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 rs.close();
                 stmt.close();
                 con.close();
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
        
        return count;
    }
    
    public int getFriendReqCount(String f){ //get the friend reqiest count of a user in requests, matches, or friends list
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String q = "select count(*) from (select username1 as username from friends where username2 = '"+ loginUsername +"') as friends";
        int count = 0;    
        
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            if(rs.next())
            {                
                count = rs.getInt(1);
            }                                                                      
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 rs.close();
                 stmt.close();
                 con.close();
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
        
        return count;
    }
    public void deleteProfile(String u){ //deleted own profile
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement st = null;
       
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            st = con.createStatement();
            int r = st.executeUpdate("delete from users where username = '" + u + "'");   
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
             try {
                 st.close();                                 
                 con.close();    
             }
             catch(Exception e) {
                 e.printStackTrace();
             }                       
        }
        logout();

    }
    
    
    
    
}
