package id.lazday.streaming.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String convert (String date){
        try {
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
            Date newDate=spf.parse(date);
            spf= new SimpleDateFormat("MMM, dd yyyy");
            date = spf.format(newDate);
        } catch (Exception e){
            e.printStackTrace();
        }

        return date;
    }

}
