package com.noob.lumberjack;

/**
 * Created by abhi on 23/10/16.
 */

public enum LogLevel {
    Verbose(0),
    Debug(1),
    Info(2),
    Warning(3),
    Error(4),
    None(100);//None means nothing will log

    private int numLevel;

    LogLevel(int num) {
        numLevel = num;
    }

    public int getNumLevel() {
        return numLevel;
    }
}
