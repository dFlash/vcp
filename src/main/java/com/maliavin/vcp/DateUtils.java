package com.maliavin.vcp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

}
