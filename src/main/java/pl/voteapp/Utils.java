package pl.voteapp;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Utils {

    public static Date getCurrentDate(Integer year, Integer month, Integer day){
        Date dateobj = new Date(year, month, day);
        return dateobj;
    }

    public static Date getCurrentDate(){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        return date;
    }
}
