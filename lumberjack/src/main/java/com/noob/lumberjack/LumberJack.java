package com.noob.lumberjack;

/**
 * Created by abhi on 23/10/16.
 */
public class LumberJack {
    //region Logging methods

    public static void v(String message) {
        Logger.getInstance().v(message);
    }

    public static void d(String message) {
        Logger.getInstance().d(message);
    }

    public static void i(String message) {
        Logger.getInstance().i(message);
    }

    public static void w(String message) {
        Logger.getInstance().w(message);
    }

    public static void e(String message) {
        Logger.getInstance().e(message);
    }

    public static void v(String tag, String message) {
        Logger.getInstance().v(tag, message);
    }

    public static void d(String tag, String message) {
        Logger.getInstance().d(tag, message);
    }

    public static void i(String tag, String message) {
        Logger.getInstance().i(tag, message);
    }

    public static void w(String tag, String message) {
        Logger.getInstance().w(tag, message);
    }

    public static void e(String tag, String message) {
        Logger.getInstance().e(tag, message);
    }

    //endregion Logging methods

    //region Accessors
    public static LogLevel getLogLevel() {
        return Logger.getInstance().getLogLevel();
    }

    public static void setLogLevel(LogLevel logLevel) {
        Logger.getInstance().setLogLevel(logLevel);
    }

    public static LogType[] getLogTypes() {
        return Logger.getInstance().getLogTypes();
    }

    public static void setLogTypes(LogType[] logTypes) {
        Logger.getInstance().setLogTypes(logTypes);
    }

    public static void setLogType(LogType logType) {
        Logger.getInstance().setLogType(logType);
    }

    public static void addLogType(LogType logType) {
        Logger.getInstance().addLogType(logType);
    }

    public static String getDefaultTag() {
        return Logger.getInstance().getDefaultTag();
    }

    public static void setDefaultTag(String defaultTag) {
        Logger.getInstance().setDefaultTag(defaultTag);
    }

    public static String getLogFilePath() {
        return Logger.getInstance().getLogFilePath();
    }

    public static void setLogFilePath(String logFilePath) {
        Logger.getInstance().setLogFilePath(logFilePath);
    }

    public static String getLogFileName() {
        return Logger.getInstance().getLogFileName();
    }

    public static void setLogFileName(String logFileName) {
        Logger.getInstance().setLogFileName(logFileName);
    }

    public static boolean isShouldConcatDate() {
        return Logger.getInstance().isShouldConcatDate();
    }

    public static void setShouldConcatDate(boolean shouldConcatDate) {
        Logger.getInstance().setShouldConcatDate(shouldConcatDate);
    }

    //endregion Accessors
}
