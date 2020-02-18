/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class isInteger {
    
    public boolean isInt(String a) {
        try {
            //if a is not an integer, an exception will be thrown out
            int i = Integer.parseInt(a);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }  
}
