/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.ParseException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

//import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;


/**
 *
 * @author User
 */
@Named(value = "searchBean")
@RequestScoped
public class SearchBean {

    /**
     * Creates a new instance of SearchBean
     */
//    private String username;
//    private String password;
    private String gender;
    private String ageMin = "18";
    private String ageMax = "50";
    private String location;
    private String interest;
    private String interest2; 
    private String interest3; 
    
    //protected String searchQuery;
    
    protected ArrayList<User> results = null; //to hold search results 
    
    public SearchBean() {
        
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterest2() {
        return interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }
    
    

    public String getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(String ageMin) {
        this.ageMin = ageMin;
    }

    public String getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(String ageMax) {
        this.ageMax = ageMax;
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

    public String getInterest3() {
        return interest3;
    }

    public void setInterest3(String interest3) {
        this.interest3 = interest3;
    }

//    public String getSearchQuery() {
//        return searchQuery;
//    }
//
//    public void setSearchQuery(String searchQuery) {
//        this.searchQuery = searchQuery;
//    }

    public ArrayList<User> getResults() {
        return results;
    }

    public void setResults(ArrayList<User> results) {
        this.results = results;
    }
    
    
    
    
    //does and gets the search parameters 
    public String Search(String u) throws ParseException{
        
        

        String query = "Select * from Users where username != '" + u + "' "; // begionning query, 
        String ending = "and username not in (select username1 as username from friends where username2 = '" + u + "' "
                + "and (status = 'pending' or status = 'accepted') union select username2 as username from friends where username1 = '" + u + "' and (status = 'pending' or status = 'accepted'))";
        
        //gender
        String gen = "";
        if(gender.toLowerCase().trim().equals("any")){
            gen = "and (gender = 'male' or gender = 'female') ";
                query = query + gen;
            
                
        } else if(gender.toLowerCase().trim().equals("male")){
                gen = "and gender = 'male' ";
                query = query + gen;
                
        } else if(gender.toLowerCase().trim().equals("female")){
            gen = "and gender = 'female' ";
            query = query + gen;

        }
        
        //age
        int min = Integer.parseInt(ageMin);
        int max = Integer.parseInt(ageMax); //convert
        String ageQ = "";
        String maxA = "";
        String minA = "";
            
        if(!ageMax.trim().equals("") || ageMax.trim() != null && !ageMin.trim().equals("") || ageMin.trim() != null  ){ // first check if it is an integer            
            if(max > min){//check if greater or less than min age                   
                ageQ = "and age >= '" + min + "' and age <= '" + max + "' ";//add min and max age criteria to query
                query = query + ageQ;;

            } else if(min > max){//swap min and max                    
                int temp = min;
                max = min;
                min = temp;
                ageQ = "and age >= '" + min + "' and age <= '" + max + "' ";
                query = query + ageQ;                    ;

            } else if(min == max){ // if min and max age are the same
                ageQ = "and age = '" + min + "' ";
                query = query + ageQ;                    
                 
            }                               
        } else if(ageMax.trim().equals("") || ageMax.trim() == null ){ // if no max age
            minA = "and age >= '" + min + "' ";              //only minmum age used in qury
            query = query + minA;               
              
        } else if(ageMin.trim().equals("") || ageMin.trim() == null ){ //if user did not enter a min age or entered any
            maxA = "and age <= '" + max + "' ";
            query = query + maxA;               
            
        }
        
        //location
        String loc = "";
        boolean con = false;
        if ((location.toLowerCase().trim().equals("") || location.toLowerCase().trim() == null || location.toLowerCase().trim().isEmpty() )){ 
            loc = "";
            query = query + loc;
            
            
        } else if((!location.toLowerCase().trim().equals("") || location.toLowerCase().trim() != null)) {
            loc = "and city like '%" + location + "%' ";
            query = query + loc;
        }
        
        //interests
        String intere = "";
        //check if all 3 search input for all interests are blank, then just create teh query 
        if((interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() == null) && (interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() == null) //if all three inputs are empty
                && (interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() == null)){ 
            query = query + ending;
            
            
            //return;
                    
        }else if((!interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() != null) && (interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() == null) //if only first interest entered
                && (interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() == null)){ 
            
            intere = "and (interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' )";
            query = query + intere;
            
            
        }  else if((interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() == null) && (!interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() != null) //if only second interest entered
                && (interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() == null)){ 
            
            intere = "and (interest1 like '%" + interest2 + "%' or interest2 like '%" + interest2 + "%' or interest3 like '%" + interest2 + "%' )";
            query = query + intere;
            
            
        }  else if((interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() == null) && (interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() == null) // if only third entered
                && (!interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() != null)){ 
            
            intere = "and (interest1 like '%" + interest3 + "%' or interest2 like '%" + interest3 + "%' or interest3 like '%" + interest3 + "%' )";
            query = query + intere;
            
        } else if((!interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() != null) && (!interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() != null) //if first and 2nd inputs enetered
                && (interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() == null)){ 
            
            intere = "and (interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' or interest1 like '%" + interest2 + "%' or interest2 like '%" + interest2 + "%' or interest3 like '%" + interest2 + "%') ";
            query = query + intere;
            
                    
        } else if((!interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() != null) && (interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() == null) //if first and third interests entered
                && (!interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() != null)){ 
            
            intere = "and (interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' or interest1 like '%" + interest3 + "%' or interest2 like '%" + interest3 + "%' or interest3 like '%" + interest3 + "%') ";
            query = query + intere;
           
                    
        }else if((interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() == null) && (!interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() != null) // if 2nd and third inputs enetred
                && (!interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() != null)){ 
            
            intere = "and (interest1 like '%" + interest2 + "%' or interest2 like '%" + interest2 + "%' or interest3 like '%" + interest2 + "%' or interest1 like '%" + interest3 + "%' or interest2 like '%" + interest3 + "%' or interest3 like '%" + interest3 + "%') ";
            query = query + intere;
            
                    
        } else if((!interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() != null) && (!interest2.toLowerCase().trim().equals("") || interest2.toLowerCase().trim() != null) //if all three inputs eneterd
                && (!interest3.toLowerCase().trim().equals("") || interest3.toLowerCase().trim() != null)){ 
            
            intere = "and (interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' or interest1 like '%" + interest2 + "%' or interest2 like '%" + interest2 + "%' or interest3 like '%" + interest2 + "%' or "
                    + "interest1 like '%" + interest3 + "%' or interest2 like '%" + interest3 + "%' or interest3 like '%" + interest3 + "%') ";
            query = query + intere;
                    
        } 
        
//        if(!interest.toLowerCase().trim().equals("") || interest.toLowerCase().trim() != null ){ 
//            
//            intere = "and (interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' or interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%' or "
//                    + "interest1 like '%" + interest + "%' or interest2 like '%" + interest + "%' or interest3 like '%" + interest + "%') ";
//            query = query + intere;
//                    
//        } 
        
        //final query
        query = query + ending;
        //searchQuery = query + ending;
        System.out.println(query);
        
        //put the search results into a arraylist of users
        results = null;
        results = new ArrayList<User>();
        results = User.getResults(query);
        
        
        
        //after clickign search button, duisplay results in another page
        
        return "searchResults.xhtml";
        
    }
    
    /*
                        <!--
                    <h:outputText value="Second Interest: "/>
                    <h:inputText label="Interest2" id="int2" value="#{searchBean.interest2}" required="false" validatorMessage="Please enter a valid word">
                        <f:validateRegex pattern="([a-zA-Z ]+)?"/>
                    </h:inputText>
                    <br/>
                    <h:message for="int2" style="color:red;margin:8px;"/>
                    <br/><br/>

                    <h:outputText value="Third Interest: "/>
                    <h:inputText label="Interest3" id="int3" value="#{searchBean.interest3}" required="false" validatorMessage="Please enter a valid word">
                        <f:validateRegex pattern="([a-zA-Z ]+)?"/>
                    </h:inputText>
                    <br/>
                    <h:message for="int3" style="color:red;margin:8px;"/>
                    <br/><br/> -->
    */

}
