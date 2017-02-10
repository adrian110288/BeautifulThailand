package com.adrianlesniak.beautifulthailand.utilities;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by adrian on 10/02/2017.
 */

public class CalendarHelper {

    public static String getMonthName(int month, boolean abbrOnly) {

        String monthName;

        switch (month) {
            case Calendar.JANUARY: {
                monthName = abbrOnly ? "Jan" : "January";
                break;
            }
            case Calendar.FEBRUARY: {
                monthName = abbrOnly ? "Feb" : "February";
                break;
            }
            case Calendar.MARCH: {
                monthName = abbrOnly ? "Mar" : "March";
                break;
            }
            case Calendar.APRIL: {
                monthName = abbrOnly ? "Apr" : "April";
                break;
            }
            case Calendar.MAY: {
                monthName = abbrOnly ? "May" : "May";
                break;
            }
            case Calendar.JUNE: {
                monthName = abbrOnly ? "Jun" : "June";
                break;
            }
            case Calendar.JULY: {
                monthName = abbrOnly ? "Jul" : "July";
                break;
            }
            case Calendar.AUGUST: {
                monthName = abbrOnly ? "Aug" : "August";
                break;
            }
            case Calendar.SEPTEMBER: {
                monthName = abbrOnly ? "Sep" : "Septemper";
                break;
            }
            case Calendar.OCTOBER: {
                monthName = abbrOnly ? "Oct" : "October";
                break;
            }
            case Calendar.NOVEMBER: {
                monthName = abbrOnly ? "Nov" : "November";
                break;
            }
            case Calendar.DECEMBER: {
                monthName = abbrOnly ? "Dec" : "December";
                break;
            }
            default: {
                monthName = "";
                break;
            }
        }

        return monthName;
    }

    public static String getHumanReadableDate(long time) {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTimeInMillis(time);

        int day = c.get(Calendar.DAY_OF_MONTH);
        String month = getMonthName(c.get(Calendar.MONTH), true);
        int year = c.get(Calendar.YEAR);

        return String.valueOf(day) + "/" + month + "/" + String.valueOf(year);
    }
}
