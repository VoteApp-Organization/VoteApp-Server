package pl.voteapp;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Utils {

    public static Date getCurrentDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        return dateobj;
    }
}
