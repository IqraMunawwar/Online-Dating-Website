/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author User
 */
@Named(value = "searchResultsBean")
@RequestScoped
public class searchResultsBean {

    /**
     * Creates a new instance of searchResultsBean
     */
    
    public searchResultsBean() {
    }
    
    //on search results page, this send the freind request when the "Send friend request" button is clicked
    public void sendFriendRequest(User reqUser) {
  
    }
    
}
