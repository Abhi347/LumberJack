[![AUR](https://img.shields.io/aur/license/yaourt.svg?maxAge=2592000)](https://raw.githubusercontent.com/Abhi347/LumberJack/master/LICENSE) 
[![](https://jitpack.io/v/Abhi347/LumberJack.svg)](https://jitpack.io/#Abhi347/LumberJack)
[![Build Status](https://travis-ci.org/Abhi347/LumberJack.svg?branch=master)](https://travis-ci.org/Abhi347/LumberJack)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2912b6db60a64009bf780afd2780e52e)](https://www.codacy.com/app/josh-abhi143/LumberJack?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Abhi347/LumberJack&amp;utm_campaign=Badge_Grade)

# LumberJack - Simple Android Logger
LumberJack is a simple logging library for Android which allows you to send log to Logcat, File System or your server (Coming soon). 
It's easy to start and ever easier to customise.

## Features
 * Logging to Logcat or File system or both
 * Easy customisation
 * Log only when a specified filter is satisfied
 * Can programmatically disable the log

## Planned feature
 * More Customization support
 * Sending Log data to a custom server
 * Emailing Log file

## Setup
LumberJack is hosted at jitpack.io. The instructions are as follows -   
Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

    allprojects {
		    repositories {
    			...
		    	maven { url "https://jitpack.io" }
    		}
    }

Step 2. Add the dependency

    dependencies {
	        compile 'com.github.Abhi347:LumberJack:0.1.0'
	  }

## Usage
Just use it as you are using Log class. For eg. instead of calling `Log.d()`, call `LumberJack.d()`
    
    LumberJack.v(tag, message);
    LumberJack.d(tag, message);
    LumberJack.i(tag, message);
    LumberJack.w(tag, message);
    LumberJack.e(tag, message);
    
Starting from 0.1.0 instead of `LumberJack`, you can also use `LJ` class as an alias. This can be done with all the LumberJack methods.

    LJ.v(tag, message);
    LJ.d(tag, message);
    LJ.i(tag, message);
    LJ.w(tag, message);
    LJ.e(tag, message);

You can choose to skip the tag. When you do that a default tag is chosen. The current default tag is `LumberJack`.

    LumberJack.v(message); //or LJ.v(message)
    LumberJack.d(message); //or LJ.d(message)
    LumberJack.i(message); //or LJ.i(message)
    LumberJack.w(message); //or LJ.w(message)
    LumberJack.e(message); //or LJ.e(message)

You can set Log Filtering by choosing the required Log Level -

    LumberJack.setLogLevel(LogLevel.Info);

Or you can choose to disable all logging

    LumberJack.setLogLevel(LogLevel.None);

## Advanced Usage
The default logging is done to Logcat, you can choose to Log in a file. Please note that Logging to server is not implemented yet.
First you'll have to add the required permissions in your app's `AndroidManifest.xml`

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

From Android 6.0 (API level 23), you'll also need to get [Runtime Permissions](https://developer.android.com/training/permissions/requesting.html) for `WRITE_EXTERNAL_STORAGE` permission.

The next step is to change the log type in `onCreate()` method of your `MainActivity.java` (Or anywhere you want to change the Log Type)

    LumberJack.setLogType(LogType.File);

Or if you want to have multiple LogType, you can pass an array.

    LumberJack.setLogTypes(new LogType[]{LogType.Logcat, LogType.File});

Although a default file path will be chosen in your External Storage, Public Document Directory or your SD card, depending upon which version of Android you're running; it's entirely possible to change the path or just the name of the logging file. (Provided that you have permissions to write at that path).

    LumberJack.setLogFilePath(fullFilePathIncludingFileNameAndExtension);

You can change the default Tag to be chosen (when a tag is not supplied with logging method)

    LumberJack.setDefaultTag("Default Tag");
    
For more usage, please check the example module (app module) in the source.

## Contributions
Feel free to report bugs, feedback or even suggest new features. I'd love to make it a great library.

## Donate
[Paypal](https://paypal.me/Abhi347/5)

## Warning
LumberJack is not a production ready library (We haven't reached 1.0.0 yet) and thus you should not use it in a production ready code. Please read the license term carefully before including it in your projects.
