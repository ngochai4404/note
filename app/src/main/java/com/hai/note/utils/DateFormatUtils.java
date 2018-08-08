package com.hai.note.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hai on 06/07/2018.
 */

public class DateFormatUtils {
    public static final String DATE_TIME = "hh:mm dd/MM/yyyy";
    public static final String DATE_TIME_TYPE_2 = "dd/MM hh:mm";
    public static final String DATE = "dd/MM/yyyy";
    public static final String TIME = "hh:mm";

    public static String dateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String dateFormat(String date, String typeInput, String typeOutput) throws ParseException {
        SimpleDateFormat sdfInput = new SimpleDateFormat(typeInput);
        SimpleDateFormat sdfOutput = new SimpleDateFormat(typeOutput);
        Date d = sdfInput.parse(date);
        return sdfOutput.format(d);
    }

    public static long getTimeMilisecond(String date, String typeInput) throws ParseException {
        SimpleDateFormat sdfInput = new SimpleDateFormat(typeInput);
        Date d = sdfInput.parse(date);
        return d.getTime();

    }
}
