package com.noob.lumberjack;

import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by abhi on 23/10/16.
 */

public class FileUtility {
    public static String getLogFilePath(String fileName) {

        if (isExternalStorageWritable()) {
            File _file;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                _file = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);
                if (exists(_file)) {
                    return getAbsoluteFilePath(_file,fileName);
                }
            }

            _file = Environment.getExternalStorageDirectory();
            if (exists(_file)) {
                return getAbsoluteFilePath(_file,fileName);
            }
        }

        File _file = Environment.getDataDirectory();
        if (exists(_file)) {
            return getAbsoluteFilePath(_file,fileName);
        }
        return fileName;
    }

    private static String getAbsoluteFilePath(File parentDirectory, String fileName) {
        String path = parentDirectory.getAbsolutePath() + File.separator + "LumberJack" + File.separator + fileName;
        File _file = new File(path);
        _file.getParentFile().mkdirs();
        if(exists(_file.getParentFile())){
            return _file.getAbsolutePath();
        }
        return path;
    }

    private static boolean exists(File file) {
        return file != null && file.exists();
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
