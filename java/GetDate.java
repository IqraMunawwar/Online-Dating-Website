/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class GetDate {
    public static final String formattedDate = "yyyy-MM-dd HH:mm:ss";
    public static String FormatDateTime()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(formattedDate);
        return sdf.format(cal.getTime());
    }

    public static String FormatDateTime2(String dt) throws ParseException //used to convert date/time retrived from database into readable format
    {        
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt);
        String converted = new SimpleDateFormat("MMMMM dd yyyy h:mm a").format(date);
        
        return converted;
    }
}
