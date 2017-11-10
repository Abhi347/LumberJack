package com.noob.lumberjack;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by abhi on 23/10/16.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class LumberJack {
    private static LogLevel mLogLevel = LogLevel.Debug;
    //private static LogType[] mLogTypes = {LogType.Logcat};
    private static ArrayList<LogType> mLogTypes = new ArrayList<>();
    private static String mDefaultTag = "LumberJack";
    private static String mLogFilePath;
    private static String mLogFileName = "LumberJack.log";

    public static void v(String message) {
        v(mDefaultTag, message);
    }

    public static void d(String message) {
        d(mDefaultTag, message);
    }

    public static void i(String message) {
        i(mDefaultTag, message);
    }

    public static void w(String message) {
        w(mDefaultTag, message);
    }

    public static void e(String message) {
        e(mDefaultTag, message);
    }

    public static void v(String tag, String message) {
        writeLog(LogLevel.Verbose, tag, message);
    }

    public static void d(String tag, String message) {
        writeLog(LogLevel.Debug, tag, message);
    }

    public static void i(String tag, String message) {
        writeLog(LogLevel.Info, tag, message);
    }

    public static void w(String tag, String message) {
        writeLog(LogLevel.Warning, tag, message);
    }

    public static void e(String tag, String message) {
        writeLog(LogLevel.Error, tag, message);
    }

    private static void writeLog(LogLevel level, String tag, String message) {
        if (mLogLevel.getNumLevel() <= level.getNumLevel()) {
            if (mLogTypes.contains(LogType.Logcat)) {
                writeToLogKat(level, tag, message);
            }
            if (mLogTypes.contains(LogType.File)) {
                writeToFile(level, tag, message);
            }
            if (mLogTypes.contains(LogType.Server)) {
                writeToServer(level, tag, message);
            }
        }
    }

    private static void writeToLogKat(LogLevel level, String tag, String message) {
        switch (level) {
            case Verbose:
                Log.v(tag, message);
                break;
            case Debug:
                Log.d(tag, message);
                break;
            case Info:
                Log.i(tag, message);
                break;
            case Warning:
                Log.w(tag, message);
                break;
            case Error:
                Log.e(tag, message);
                break;
        }
    }

    private static void writeToFile(LogLevel level, String tag, String message) {
        loadFilePath();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        String writableText = currentDateTimeString + "\t" + level.toString() + "\t" + tag + "\t" + message + "\n";
        FileOutputStream stream = null;
        try {
            File _file = new File(getLogFilePath());
            stream = new FileOutputStream(_file, true);
            stream.write(writableText.getBytes());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException eParam) {
                    eParam.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private static void writeToServer(LogLevel level, String tag, String message) {
        throw new UnsupportedOperationException("Logging to server is not supported yet");
    }

    private static void loadFilePath() {
        if (getLogFilePath() == null || getLogFilePath().isEmpty()) {
            setLogFilePath(FileUtility.getLogFilePath(getLogFileName()));
        }
    }

    //region Accessors
    public static LogLevel getLogLevel() {
        return mLogLevel;
    }

    public static void setLogLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
    }

    public static LogType[] getLogTypes() {
        return mLogTypes.toArray(new LogType[]{});
    }

    public static void setLogTypes(LogType[] logTypes) {
        mLogTypes.clear();
        Collections.addAll(mLogTypes, logTypes);
    }

    public static void setLogType(LogType logType) {
        mLogTypes.clear();
        mLogTypes.add(logType);
    }

    public static void addLogType(LogType logType) {
        mLogTypes.add(logType);
    }

    public static String getDefaultTag() {
        return mDefaultTag;
    }

    public static void setDefaultTag(String defaultTagParam) {
        mDefaultTag = defaultTagParam;
    }

    public static String getLogFilePath() {
        return mLogFilePath;
    }

    public static void setLogFilePath(String logFilePathParam) {
        mLogFilePath = logFilePathParam;
    }

    public static String getLogFileName() {
        return mLogFileName;
    }

    public static void setLogFileName(String logFileNameParam) {
        mLogFileName = logFileNameParam;
    }
    //endregion
}
