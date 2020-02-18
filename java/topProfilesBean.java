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
@Named(value = "topProfilesBean")
@RequestScoped
public class topProfilesBean {

    /**
     * Creates a new instance of topProfilesBean
     */
    
    protected ArrayList<User> topProfiles = null; // store top prfiles
    
    public topProfilesBean() {
    }
    
    public void getTopThree() throws ParseException{ // get top 3 males and females
        topProfiles = new ArrayList<User>();
        topProfiles.clear(); // clear any old data 
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String q = "(select * from users where gender = 'male' order by views DESC limit 3) union (select * from users where gender = 'female' order by views DESC limit 3)"; 
        
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            while(rs.next())
            {                
                topProfiles.add(new User(rs.getString("username"),rs.getString("password"),rs.getString("gender"),rs.getString("age"),
                                rs.getString("city"),rs.getString("interest1"),rs.getString("interest2"),rs.getString("interest3"),GetDate.FormatDateTime2(rs.getString("lastonline")), rs.getInt("views")));
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
    }
    
    //return list of top three males and females for use with websuite
    public ArrayList<User> topFemales() throws ParseException{
        getTopThree();
        ArrayList<User> females = new ArrayList<>();
        for (User f : topProfiles) {
            if (f.getGender().equals("female")) {
                females.add(f);
            }
        }
        return females;
    }
    public ArrayList<User> topMales(){
        ArrayList<User> males = new ArrayList<>();
        for (User m : topProfiles) {
            if (m.getGender().equals("male")) {
                males.add(m);
            }
        }
        return males;
    }
}
