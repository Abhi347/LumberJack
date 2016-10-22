package com.example.lumberjack;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.noob.lumberjack.LogLevel;
import com.noob.lumberjack.LogType;
import com.noob.lumberjack.LumberJack;

public class MainActivity extends AppCompatActivity {

    RadioGroup mLogTypeRadioGroup, mFilterLogLevelRadioGroup, mCurrentLogLevelRadioGroup;
    EditText mTagEdit, mMessageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogTypeRadioGroup = (RadioGroup) findViewById(R.id.radio_type);
        mFilterLogLevelRadioGroup = (RadioGroup) findViewById(R.id.radio_filter);
        mCurrentLogLevelRadioGroup = (RadioGroup) findViewById(R.id.radio_log_level);
        mTagEdit = (EditText) findViewById(R.id.tag_edit);
        mMessageEdit = (EditText) findViewById(R.id.message_edit);
    }

    public void onLogClick(View view) {
        int typeId = mLogTypeRadioGroup.getCheckedRadioButtonId();
        LogType _logType = LogType.Logcat;
        LogLevel _filterLogLevel = LogLevel.Debug;
        switch (typeId) {
            case R.id.logcat:
                _logType = LogType.Logcat;
                break;
            case R.id.file:
                _logType = LogType.File;
                break;
            case R.id.server:
                _logType = LogType.Server;
                break;
        }
        int levelId = mFilterLogLevelRadioGroup.getCheckedRadioButtonId();
        switch (levelId) {
            case R.id.verbose:
                _filterLogLevel = LogLevel.Verbose;
                break;
            case R.id.debug:
                _filterLogLevel = LogLevel.Debug;
                break;
            case R.id.info:
                _filterLogLevel = LogLevel.Info;
                break;
            case R.id.warning:
                _filterLogLevel = LogLevel.Warning;
                break;
            case R.id.error:
                _filterLogLevel = LogLevel.Error;
                break;
        }
        String tag = mTagEdit.getText().toString();
        String message = mMessageEdit.getText().toString();

        if (message.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter a message to log")
                    .setNeutralButton("OK", null)
                    .create()
                    .show();
            return;
        }
        LumberJack.setLogLevel(_filterLogLevel);
        LumberJack.setLogType(_logType);

        int logLevelId = mCurrentLogLevelRadioGroup.getCheckedRadioButtonId();
        if (tag.isEmpty()) {
            switch (logLevelId) {
                case R.id.log_verbose:
                    LumberJack.v(message);
                    break;
                case R.id.log_debug:
                    LumberJack.d(message);
                    break;
                case R.id.log_info:
                    LumberJack.i(message);
                    break;
                case R.id.log_warning:
                    LumberJack.w(message);
                    break;
                case R.id.log_error:
                    LumberJack.e(message);
                    break;
            }
        } else {
            switch (logLevelId) {
                case R.id.log_verbose:
                    LumberJack.v(tag, message);
                    break;
                case R.id.log_debug:
                    LumberJack.d(tag, message);
                    break;
                case R.id.log_info:
                    LumberJack.i(tag, message);
                    break;
                case R.id.log_warning:
                    LumberJack.w(tag, message);
                    break;
                case R.id.log_error:
                    LumberJack.e(tag, message);
                    break;
            }
        }

    }
}
