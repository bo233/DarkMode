package com.bo233.darkmode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
    private String filePath;
    private File PROP_FILE;
//    private final static File PROP_FILE = new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
    //    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private final String comment = "This is a settings.";
    private static Properties properties;

    public static final String KEY_SWITCH = "open";
    public static final String TIME_SWITCH = "time_switch";
    public static final String BEGIN_HOUR = "beginning_hour";
    public static final String BEGIN_MIN = "beginning_min";
    public static final String END_HOUR = "ending_hour";
    public static final String END_MIN = "ending_min";

    public MyProperties(String path){
        PROP_FILE = new File(path);
        filePath = path;
        properties = new Properties();
        try {
            properties.load(new FileReader(PROP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!PROP_FILE.exists()) {
            PROP_FILE.getParentFile().mkdir();
            this.setProperty(KEY_SWITCH, "false");
            this.setProperty(TIME_SWITCH, "false");
            this.setProperty(BEGIN_HOUR, "0");
            this.setProperty(BEGIN_MIN, "0");
            this.setProperty(END_HOUR, "0");
            this.setProperty(END_MIN, "0");

        }
    }

    public boolean setProperty(String key, String value){
//        boolean isSuccessful = true;
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(PROP_FILE),comment);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
