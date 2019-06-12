/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.techmaflex.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Elorri on 17/04/2016.
 * Useful class for displaying dates in the correct locale, orworking with them.
 */
public class DateUtilsImpl implements DateUtilInterface {
    public static final String DAY_OF_WEEK_FORMAT = "EEEE";
    public static final String DAY_MONTH_FORMAT = "dd MMMM";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_HOURS_MIN_FORMAT = "dd/MM/yy HH:mm";
    public static final String DATE_HOURS_MIN_SEC_FORMAT = "dd/MM/yy HH:mm:ss";
    public static final String HOURS_MIN_SEC_FORMAT = "HH:mm:ss";
    public static final String HOURS_MIN_SEC_MILLIS_FORMAT = "HH:mm:ss:SSS";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
    //public static final String TIMESTAMP_FORMAT = "yyyyMMdd'T'HH:mm:ss'Z'";
    //public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";



    @Override
    public String fromLongToStringDate(long dateLong, String stringFormat, Locale locale) {
        SimpleDateFormat df = new SimpleDateFormat(stringFormat, locale);
        return df.format(new Date(dateLong));
    }

    @Override
    public String fromLongToStringDuration(long dateLong, String stringFormat) {
        long millis = dateLong % 1000;
        long s = (dateLong / 1000) % 60;
        long m = (dateLong / (1000 * 60)) % 60;
        long h = (dateLong / (1000 * 60 * 60)) % 24;
        switch (stringFormat) {
            case HOURS_MIN_SEC_MILLIS_FORMAT: {
                return String.format("%d:%02d:%02d:%03d", h, m, s, millis);
            }
            case HOURS_MIN_SEC_FORMAT:
            default: {
                return String.format("%02d:%02d:%02d", h, m, s);
            }
        }

    }


    @Override
    public String getFriendlyFormat(long date) {
        long todayStart = todayStart();
        long tomorrowStart = tomorrowStart();
        long in7daysStart = addDay(6, todayStart);
        long nextyearStart = nextYearStart();
        if (date >= tomorrowStart && date < in7daysStart)
            return DAY_OF_WEEK_FORMAT;
        if (date >= in7daysStart && date < nextyearStart)
            return DAY_MONTH_FORMAT;
        else
            return DATE_FORMAT;
    }

    @Override
    public long setZeroDay(long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public long setZeroYear(long dateInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public long yesterdayStart() {
        return addDay(-1, todayStart());
    }

    @Override
    public long todayStart() {
        return setZeroDay(System.currentTimeMillis());
    }

    @Override
    public long now() {
        return System.currentTimeMillis();
    }

    @Override
    public long tomorrowStart() {
        return addDay(1, todayStart());
    }

    @Override
    public long afterTomorrowStart() {
        return addDay(2, todayStart());
    }

    @Override
    public long nextYearStart() {
        long nextYear = addDay(365, System.currentTimeMillis());
        return setZeroYear(nextYear);
    }

    @Override
    public long addDay(int nbDays, long startDateInMillis) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(startDateInMillis));
        endDate.add(Calendar.DATE, nbDays);
        return endDate.getTime().getTime();
    }

    @Override
    public long addHours(int nbHours, long startDateInMillis) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(startDateInMillis));
        endDate.add(Calendar.HOUR_OF_DAY, nbHours);
        return endDate.getTime().getTime();
    }

}
