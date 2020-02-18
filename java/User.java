/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class User { 

    static ArrayList<User> getRequests(String u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
// for the user object

    static ArrayList<User> getFriends(String u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String username;
    private String password;
    private String gender;
    private String age;
    private String location;
    private String interest1;
    private String interest2;
    private String interest3;
    private String lastOnline;
    private int views;
    
    public User loggedUser;
    
    public static ArrayList<User> searchResults;
    
    public User(String u, String p, String g, String a, String l, String i,String i2,String i3, String o, int v){
        
        username = u;
        password = p;
        gender = g;
        age = a;
        location = l;
        interest1 = i;
        interest2 = i2;
        interest3 = i3;
        lastOnline = o;
        views = v;  
        
    }

    public String getLastOnline() throws ParseException {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
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

    public String getInterest1() {
        return interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
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

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
    
    public static boolean checkUsername(String u, String p){ // validate the login username and password to the database, true or false will be passed into LoginBean
        
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select username, password from users where username = ? and password = ? ");
            ps.setString(1, u);
            ps.setString(2, p);
  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                System.out.println(rs.getString("username") + " " + rs.getString("password")); // for testing
                //also create a user object of logged in user
                //loggedUser = new User();                
                return true;
            }
            else {
                return false; // if no matching username and/or password
            }
        } catch (Exception ex) {
            System.out.println("Error in validation -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        
    }
    
    public static boolean checkUsernameExists(String u){ // for registration, check id eneterd username exists or not
        
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Database.getConnection();
            ps = con.prepareStatement("select username from users where username = ?");
            ps.setString(1, u);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) // username exists
            {
                System.out.println(rs.getString("username") + " " + rs.getString("password")); // for testing
                //also create a user object of logged in user
                //loggedUser = new User();                
                return false;
            }
            else {
                return true; // if no matching username and/or password
                //insert into database
            }
        } catch (Exception ex) {
            System.out.println("Error in validation -->" + ex.getMessage());
            return false;
        } finally {
            Database.close(con);
        }
        
    }
    
    //get Users via query from SearchBean then return it to the searchbean
    public static ArrayList<User> getResults(String q) throws ParseException{
       
         searchResults = null;
         searchResults = new ArrayList<User>();
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/khans7943?useSSL=false";       
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
               
        try {                     
            con = DriverManager.getConnection(DB_URL, "khans7943", "0997372");                  
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);

            while(rs.next())
            {                
                searchResults.add(new User(rs.getString("username"),rs.getString("password"),rs.getString("gender"),rs.getString("age"),
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
        
        return searchResults;
    }
    
    
    
    
    
    
    
}
