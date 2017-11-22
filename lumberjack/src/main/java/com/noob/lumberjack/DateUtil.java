package com.noob.lumberjack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by abhishekjain on 22/11/17 for LumberJack.
 */

class DateUtil {
    private static final String DATE_FORMAT_FILE_DEFAULT = "yyyyMMMdd";

    static String getToday() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_FILE_DEFAULT, Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }
}
