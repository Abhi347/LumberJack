package com.noob.lumberjack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by abhi on 23/10/16.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class Logger {

    //region Singleton
    private static Logger _instance;

    public static Logger getInstance() {
        if (_instance == null) {
            _instance = new Logger();
        }
        return _instance;
    }

    private Logger() {
    }
    //endregion Singleton

    private LogLevel mLogLevel = LogLevel.Debug;
    private ArrayList<LogType> mLogTypes = new ArrayList<>();
    private String mDefaultTag = "LumberJack";
    private String mLogFilePath;
    private File mLogFile;
    private String mLogFileName = "LumberJack";
    private boolean mShouldConcatDate = true;

    //region Logging methods

    public void v(String message) {
        v(mDefaultTag, message);
    }

    public void d(String message) {
        d(mDefaultTag, message);
    }

    public void i(String message) {
        i(mDefaultTag, message);
    }

    public void w(String message) {
        w(mDefaultTag, message);
    }

    public void e(String message) {
        e(mDefaultTag, message);
    }

    public void v(String tag, String message) {
        writeLog(LogLevel.Verbose, tag, message);
    }

    public void d(String tag, String message) {
        writeLog(LogLevel.Debug, tag, message);
    }

    public void i(String tag, String message) {
        writeLog(LogLevel.Info, tag, message);
    }

    public void w(String tag, String message) {
        writeLog(LogLevel.Warning, tag, message);
    }

    public void e(String tag, String message) {
        writeLog(LogLevel.Error, tag, message);
    }

    //endregion Logging methods

    private void writeLog(LogLevel level, String tag, String message) {
        if (mLogLevel.getNumLevel() <= level.getNumLevel()) {
            if (mLogTypes.contains(LogType.Logcat)) {
                writeToLogKat(level, tag, message);
            }
            if (mLogTypes.contains(LogType.File) || mLogTypes.contains(LogType.Email)) {
                writeToFileUsingPath(level, tag, message);
            }
            if (mLogTypes.contains(LogType.Server)) {
                writeToServer(level, tag, message);
            }
        }
    }

    private void writeToLogKat(LogLevel level, String tag, String message) {
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

    private void writeToFileUsingPath(LogLevel level, String tag, String message) {
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

    private void writeToFileUsingFile(LogLevel level, String tag, String message) {
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
    private void writeToServer(LogLevel level, String tag, String message) {
        throw new UnsupportedOperationException("Logging to server is not supported yet");
    }

    public void exportLogsToEmail(Activity activity) {
        loadFilePath();
        File _file = new File(getLogFilePath()); // getting log file from storage
        launchEmailClient(activity,_file); // launching email client
    }

    private void launchEmailClient(Activity activity, File file) {
        try {
            if(activity == null){
                return;
            }
            Uri path = FileProvider.getUriForFile(activity,
                    activity.getPackageName(), file); // identifying the URI of the file path

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("vnd.android.cursor.dir/email");
            intent.putExtra(Intent.EXTRA_STREAM, path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something went wrong, please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void loadFilePath() {
        if (getLogFilePath() == null || getLogFilePath().isEmpty()) {
            setLogFilePath(FileUtil.getLogFilePath(getLogFileName(), mShouldConcatDate));
        }
    }

    //region Accessors
    public LogLevel getLogLevel() {
        return mLogLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
    }

    public LogType[] getLogTypes() {
        return mLogTypes.toArray(new LogType[]{});
    }

    public void setLogTypes(LogType[] logTypes) {
        mLogTypes.clear();
        Collections.addAll(mLogTypes, logTypes);
    }

    public void setLogType(LogType logType) {
        mLogTypes.clear();
        mLogTypes.add(logType);
    }

    public void addLogType(LogType logType) {
        mLogTypes.add(logType);
    }

    public String getDefaultTag() {
        return mDefaultTag;
    }

    public void setDefaultTag(String defaultTagParam) {
        mDefaultTag = defaultTagParam;
    }

    public String getLogFilePath() {
        return mLogFilePath;
    }

    public void setLogFilePath(String logFilePathParam) {
        mLogFilePath = logFilePathParam;
    }

    public String getLogFileName() {
        return mLogFileName;
    }

    public void setLogFileName(String logFileNameParam) {
        mLogFileName = logFileNameParam;
    }

    public boolean isShouldConcatDate() {
        return mShouldConcatDate;
    }

    public void setShouldConcatDate(boolean shouldConcatDate) {
        mShouldConcatDate = shouldConcatDate;
    }

    //endregion
}
