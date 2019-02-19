package com.bo233.darkmode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    private String filePath;
    private File PROP_FILE;
//    private final static File PROP_FILE = new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
    //    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private final String comment = "This is a settings.";
    private Properties properties;

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
            this.setProperty("open", "false");
            this.setProperty("time_switch", "false");
            this.setProperty("beginning_hour", " ");
            this.setProperty("beginning_min", " ");
            this.setProperty("ending_hour", " ");
            this.setProperty("ending_min", " ");

        }
    }

    public boolean setProperty(String key, String value){
        boolean isSuccessful = true;
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(PROP_FILE),comment);
        } catch (IOException e) {
            e.printStackTrace();
            isSuccessful = false;
        }

        return isSuccessful;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
