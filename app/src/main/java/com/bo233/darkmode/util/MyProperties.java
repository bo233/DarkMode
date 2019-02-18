package com.bo233.darkmode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public final static File PROP_FILE=new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
    //    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private final String comment = "This is a settings.";
    private Properties properties;

    public MyProperties(){
        properties = new Properties();
        try {
            properties.load(new FileReader(PROP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!PROP_FILE.exists()) {
            PROP_FILE.getParentFile().mkdir();
            this.setProperty("open", "false");
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
}
