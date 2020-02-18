/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
//@Named(value = "myFriendRequestsBean")
//@RequestScoped
@ManagedBean
@SessionScoped
public class myFriendRequestsBean {

    /**
     * Creates a new instance of myFriendRequestsBean
     */
    public myFriendRequestsBean() {
    }
    
    protected ArrayList<User> requests;
    protected User request;
    
    public void populateFriendRequests(String u) throws IOException{ // get profile(s) that have sent a friend request
        
        
        requests = null;
        requests = new ArrayList<User>();
        requests = User.getRequests(u);

        
        FacesContext.getCurrentInstance().getExternalContext().redirect("myFriendRequests.xhtml");
    }
    
//    public ArrayList<User> getFriendRequests(String u) throws ParseException, SQLException{
//        populateFriendRequests(u);//add freidn requests to an arraylist of users
//        
//        return requests;
//        
//    }

    public ArrayList<User> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<User> requests) {
        this.requests = requests;
    }

    
    
    public User getRequest() {
        return request;
    }

    public void setRequest(User request) {
        this.request = request;
    }
    
    
    
    public void acceptRequest(String f, String u) throws IOException{ // accept friend request
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement st = null;
       
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            st = con.createStatement();
            int r = st.executeUpdate("Update friends set status = 'accepted' where username1 = '" + f + "' and username2 = '" + u + "'");   
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
        
        for(int i=0; i<requests.size(); i++)
        {
            if(f.equals(requests.get(i).getUsername()))
            {
                requests.remove(i);
            }
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("myFriendRequests.xhtml");
        //return "myFriendRequests.xhtml";
    }   
    
    public void declineRequest(String f, String u) throws IOException{ //decline friend request
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement st = null;
       
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            st = con.createStatement();
            //delete the friends table entry 
            int r = st.executeUpdate("delete from friends where username1 = '" + f + "' and username2 = '" + u + "'"); 
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
        
        for(int i=0; i<requests.size(); i++)
        {
            if(f.equals(requests.get(i).getUsername()))
            {
                requests.remove(i);
            }
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("myFriendRequests.xhtml");
        //return "myFriendRequests.xhtml";
    }
    
}
