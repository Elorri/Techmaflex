package com.techmaflex.app.util;

import java.util.Locale;

public interface DateUtilInterface {


    /**
     * Convert a date in a form of a long to a readable date adapted to user locale and timezone.
     * Note : when no timezone is set, the user default is used, that's why we don't do a
     * setTimezone here.
     */
    String fromLongToStringDate(long dateLong, String stringFormat, Locale locale) ;

    String fromLongToStringDuration(long dateLong, String stringFormat);


    String getFriendlyFormat(long date) ;

    /**
     * This method convert a long representing an instant ex 2016-01-25 19:00:00 to the beginning
     * of the day ex:2016-01-25 00:00:00
     *
     * @param dateInMillis
     * @return long representing the start of the day
     */
    long setZeroDay(long dateInMillis);

    /**
     * This method convert a long representing an instant ex 2016-01-25 19:00:00 to the beginning
     * of the day and the beginning of the year ex:2016-01-01 00:00:00
     *
     * @param dateInMillis
     * @return long representing the start of the day
     */
    long setZeroYear(long dateInMillis) ;

    long yesterdayStart();
    long todayStart();
    long now();
    long tomorrowStart();
    long afterTomorrowStart();
    long nextYearStart() ;

    /**
     * Add any number of days to the current day. Negatives number also possible
     *
     * @param nbDays
     * @param startDateInMillis
     * @return
     */
    long addDay(int nbDays, long startDateInMillis);

    /**
     * Add any number of days to the current day. Negatives number also possible
     *
     * @param nbHours
     * @param startDateInMillis
     * @return
     */
    long addHours(int nbHours, long startDateInMillis);
}
