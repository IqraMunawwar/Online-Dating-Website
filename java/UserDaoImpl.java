import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Statement;
import java.time.LocalTime;
import javax.faces.bean.SessionScoped;


// this will be used/intergrated with the search for profiles function
@SessionScoped
public class UserDaoImpl {
    
    private final String SQL_GET_ALL_USERS = "select id, name, age, gender, city from dating_app";
    private final String SQL_GET_USERS = "select id, name, age, gender, city from dating_app where age = ? and gender = ? and city = ? ";
    private final String SQL_GET_USER = "select id, name, age, gender, city, lastseen from dating_app where id = ?";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    private String id;

    public User getUser() {
        return user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private User user;
    
    private String userName;
    
    private String age;
    
    private String gender;
    
    private String location;
    
    public String getuserName() {
        return userName;
    }

    public void setgetuserName(String userName) {
        this.userName = userName;
    }
    public String getage() {
        return age;
    }

    public void setgetage(String age) {
        this.age = age;
    }
     public String getgender() {
        return gender;
    }

    public void setgetgender(String gender) {
        this.gender = gender;
    }
     public String getlocation() {
        return location;
    }

    public void setgetlocation(String location) {
        this.location = location;
    }
    
    
    private List<User> userList;
        
//    Connection CONN = Database.getConnection();
//    public String retrieveUsers() {
//       
//        try(PreparedStatement ps = CONN.prepareStatement(SQL_GET_USERS)){
//			ps.setString(1, user.getAge());
//			ps.setString(2,user.getGender());
//			ps.setString(3, user.getLocation());
//			try(ResultSet rs=ps.executeQuery()){
//                            while(rs.next()){
//                                User member = new User();
//                                member.setID(rs.getString(1));
//                                member.setUsername(rs.getString(2));
//                                member.setAge(rs.getString(3));
//                                member.setGender(rs.getString(4));
//                                member.setLocation(rs.getString(5));
//                                userList.add(member);
//                            }
//			}
//		}catch(SQLException e){
//			Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
//		}
//        return "newSearch";
//    }
//    
//    public String retrievAllUsers() {
//        try(PreparedStatement ps = CONN.prepareStatement(SQL_GET_ALL_USERS)){
//			try(ResultSet rs=ps.executeQuery()){
//                            while(rs.next()){
//                                User member = new User();
//                                member.setID(rs.getString(1));
//                                member.setUsername(rs.getString(2));
//                                member.setAge(rs.getString(3));
//                                member.setGender(rs.getString(4));
//                                member.setLocation(rs.getString(5));
//                                userList.add(member);
//                            }
//			}
//		}catch(SQLException e){
//			Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
//		}
//        return "newSearch";
//    }


    
}
