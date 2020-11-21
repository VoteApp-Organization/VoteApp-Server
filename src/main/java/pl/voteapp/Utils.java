package pl.voteapp;

import java.sql.Date;

public class Utils {

    public static Date getCurrentDate(Integer year, Integer month, Integer day){
        Date dateobj = new Date(year, month, day);
        return dateobj;
    }
}
