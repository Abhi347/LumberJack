package com.noob.lumberjack;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by abhi on 23/10/16 for LumberJack.
 */

@SuppressWarnings("WeakerAccess")
public class FileUtil {
    /*public static String getLogFilePath(String fileName) {
        return getLogFilePath(fileName, true);
    }*/

    public static String getLogFilePath(String fileName, boolean shouldConcatDate) {

        if (isExternalStorageWritable()) {
            File _file;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                _file = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);
                if (exists(_file)) {
                    return getAbsoluteFilePath(_file, fileName, shouldConcatDate);
                }
            }

            _file = Environment.getExternalStorageDirectory();
            if (exists(_file)) {
                return getAbsoluteFilePath(_file, fileName, shouldConcatDate);
            }
        }

        File _file = Environment.getDataDirectory();
        if (exists(_file)) {
            return getAbsoluteFilePath(_file, fileName, shouldConcatDate);
        }
        return fileName;
    }

    public static File getLogFile(String fileName) {

        if (isExternalStorageWritable()) {
            File _folder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                _folder = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);
            } else {
                _folder = Environment.getExternalStorageDirectory();
            }
            if (exists(_folder)) {
                return getAbsoluteFile(_folder, fileName);
            }
        }

        File _file = Environment.getDataDirectory();
        if (exists(_file)) {
            return getAbsoluteFile(_file, fileName);
        }
        return _file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getAbsoluteFilePath(File parentDirectory, String fileNameWithoutExtension, boolean shouldConcatDate) {
        String path;
        if (shouldConcatDate) {
            String today = DateUtil.getToday();
            Log.d("Lumber", "today: " + today);
            path = parentDirectory.getAbsolutePath() + File.separator + "LumberJack" + File.separator + fileNameWithoutExtension + today + ".log";
        } else {
            path = parentDirectory.getAbsolutePath() + File.separator + "LumberJack" + File.separator + fileNameWithoutExtension + ".log";
        }
        File _file = new File(path);
        _file.getParentFile().mkdirs();
        if (exists(_file.getParentFile())) {
            return _file.getAbsolutePath();
        }
        return path;
    }

    private static File getAbsoluteFile(File parentDirectory, String fileName) {
        String path = parentDirectory.getAbsolutePath() + File.separator + "LumberJack" + File.separator + fileName;
        File _file = new File(path);
        _file.getParentFile().mkdirs();
        if (exists(_file.getParentFile())) {
            return _file;
        }
        File lumberJackFolder = new File(parentDirectory.getAbsoluteFile(), "LumberJack");
        lumberJackFolder.mkdirs();
        _file = new File(lumberJackFolder, fileName);
        return _file;
    }

    private static boolean exists(File file) {
        return file != null && file.exists();
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    @SuppressWarnings("unused")
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
