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
//this for the friends list page and its actions (send/view message for a friend, unfriend/block(maybe)
//@Named(value = "myFriendsBean")
//@RequestScoped
@ManagedBean
@SessionScoped
public class myFriendsBean {

    /**
     * Creates a new instance of myFriendsBean
     */
    
    protected ArrayList<User> friends; // get user friends(status='accepted')
    protected User friend;
    
    
    public myFriendsBean() {
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
    
    
    
    //gets friends list then goes to the friends page
    public void populateFriendsList(String u) throws ParseException, SQLException, IOException{
        friends = null;
        friends = new ArrayList<User>();
        friends = User.getFriends(u);

        
        FacesContext.getCurrentInstance().getExternalContext().redirect("myFriends.xhtml");
    }
    
    
    

}
