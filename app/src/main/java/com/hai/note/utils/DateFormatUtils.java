package com.hai.note.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hai on 06/07/2018.
 */

public class DateFormatUtils {
    public static final String DATE_TIME="hh:mm dd/MM/yyyy";
    public static final String DATE="dd/MM/yyyy";
    public static final String TIME="hh:mm";

    public static String dateFormat(Date date,String format){
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }
}
