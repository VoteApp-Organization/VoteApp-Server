package pl.voteapp;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Random;
import java.util.Set;

public class Utils {
    public static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String EMPTY_STRING = "";

    public static Date getCurrentDate(Integer year, Integer month, Integer day) {
        Date dateobj = new Date(year, month, day);
        return dateobj;
    }

    public static Date getCurrentDate() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;
    }

    public static String generateRandom(Set<Long> groupPasswords) {
        Integer passwordLength = 6;
        StringBuilder sb;
        while (true) {
            Random random = new SecureRandom();
            sb = new StringBuilder(passwordLength);
            for (int i = 0; i < passwordLength; i++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            if (!groupPasswords.contains(sb.toString())) {
                break;
            }
        }
        return sb.toString();
    }
}
