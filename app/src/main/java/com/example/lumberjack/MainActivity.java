package com.example.lumberjack;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.noob.lumberjack.LJ;
import com.noob.lumberjack.LogLevel;
import com.noob.lumberjack.LogType;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_FILE_WRITE = 1337;
    private CheckBox mLogkatCB, mFileCB, mServerCB,mEmailCB;

    private RadioGroup mFilterLogLevelRadioGroup;
    private RadioGroup mCurrentLogLevelRadioGroup;
    private EditText mTagEdit;
    private EditText mMessageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogkatCB = findViewById(R.id.logcat);
        mFileCB = findViewById(R.id.file);
        mServerCB = findViewById(R.id.server);
        mEmailCB = findViewById(R.id.email);

        mFilterLogLevelRadioGroup = findViewById(R.id.radio_filter);
        mCurrentLogLevelRadioGroup = findViewById(R.id.radio_log_level);
        mTagEdit = findViewById(R.id.tag_edit);
        mMessageEdit = findViewById(R.id.message_edit);

        mTagEdit.setText("Hello");
        mMessageEdit.setText("World");

        takePermissions();
    }

    private void takePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("File Access Permission required")
                        .setMessage("Please allow the app to access your external storage for the log files to be saved. " +
                                "If you're not using the log-type as file, you can ignore this")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                takePermissions();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "External storage access permission denied", Toast.LENGTH_LONG).show();
                            }
                        })
                        .create()
                        .show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FILE_WRITE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FILE_WRITE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    Toast.makeText(this, "File access permission granted", Toast.LENGTH_LONG).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "File access permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void onLogClick(View view) {
        ArrayList<LogType> _logTypes = new ArrayList<>();
        if (mLogkatCB.isChecked()) {
            _logTypes.add(LogType.Logcat);
        }
        if (mFileCB.isChecked()) {
            _logTypes.add(LogType.File);
        }
        if (mServerCB.isChecked()) {
            _logTypes.add(LogType.Server);
        }
        if(mEmailCB.isChecked()){
            _logTypes.add(LogType.Email);
        }
        LogLevel _filterLogLevel = LogLevel.Debug;
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
        LJ.setLogLevel(_filterLogLevel);
        LJ.setLogTypes(_logTypes.toArray(new LogType[]{}));

        int logLevelId = mCurrentLogLevelRadioGroup.getCheckedRadioButtonId();
        if (tag.isEmpty()) {
            switch (logLevelId) {
                case R.id.log_verbose:
                    LJ.v(message);
                    break;
                case R.id.log_debug:
                    LJ.d(message);
                    break;
                case R.id.log_info:
                    LJ.i(message);
                    break;
                case R.id.log_warning:
                    LJ.w(message);
                    break;
                case R.id.log_error:
                    LJ.e(message);
                    break;
            }
        } else {
            switch (logLevelId) {
                case R.id.log_verbose:
                    LJ.v(tag, message);
                    break;
                case R.id.log_debug:
                    LJ.d(tag, message);
                    break;
                case R.id.log_info:
                    LJ.i(tag, message);
                    break;
                case R.id.log_warning:
                    LJ.w(tag, message);
                    break;
                case R.id.log_error:
                    LJ.e(tag, message);
                    break;
            }
        }

    }

    /**
     * click listener for Email button
     * @param view view
     */
    public void onEmailClick(View view){
        try {
            LJ.exportLogsToEmail(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
