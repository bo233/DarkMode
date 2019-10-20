package com.bo233.darkmode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyProperties {
    public static final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
//    private String filePath;
    private static File PROP_FILE;
//    private final static File PROP_FILE = new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
    //    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private static final String comment = "This is a settings.";
    private static Properties properties;

    public static final String KEY_SWITCH = "open";
    public static final String TIME_SWITCH = "time_switch";
    public static final String BEGIN_HOUR = "beginning_hour";
    public static final String BEGIN_MIN = "beginning_min";
    public static final String END_HOUR = "ending_hour";
    public static final String END_MIN = "ending_min";
    public static final String SELF_SETTING_PKG_NAME = "self_setting_pkg_name";
    public static final String SELF_SETTING = "self_setting";
    public static final String MODE_OFF = "off", MODE_NORMAL = "normal", MODE_SELF = "self";
//    public static final String SET_VIEW = "set_view";


    public static void init(){
        if(properties == null) {
            properties = new Properties();
            PROP_FILE = new File(SETTINGPATH);
            try {
                properties.load(new FileReader(PROP_FILE));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!PROP_FILE.exists()) {
                PROP_FILE.getParentFile().mkdir();
                setProperty(KEY_SWITCH, "false");
                setProperty(TIME_SWITCH, "false");
//                setProperty(SET_VIEW, "false");
                setProperty(BEGIN_HOUR, "0");
                setProperty(BEGIN_MIN, "0");
                setProperty(END_HOUR, "0");
                setProperty(END_MIN, "0");
            }
        }
        ModeProperties.init();

    }


    public static boolean setProperty(String key, String value){
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


    public static String getProperty(String key){
        return properties.getProperty(key);
    }


    public static class ModeProperties{
        private static Properties modeProp;
        private static File MODE_LIST_FILE;
        private static final String modeFilePath = "/sdcard/Android/data/com.bo233.darkmode/mode_list.ini";
        private static final String modeComment = "This is mode list.";

        public static void init(){
            if(modeProp == null) {
                modeProp = new Properties();
                MODE_LIST_FILE = new File(modeFilePath);
                try {
                    modeProp.load(new FileReader(MODE_LIST_FILE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!MODE_LIST_FILE.exists()) {
                    MODE_LIST_FILE.getParentFile().mkdir();
                }
            }
        }

        public static boolean setProperty(String key, String value){
//        boolean isSuccessful = true;
            modeProp.setProperty(key, value);
            try {
                modeProp.store(new FileOutputStream(MODE_LIST_FILE),modeComment);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        public static String getProperty(String key){
            return modeProp.getProperty(key);
        }
    }


    public static class KillProperties{
        private static Properties killProp;
        private static File KILL_LIST_FILE;
        private static final String killFilePath = "/sdcard/Android/data/com.bo233.darkmode/kill_list.ini";
        private static final String killComment = "This is kill list.";
        public static final String KILL_ENABLE = "true", KILL_DISABLE = "false";

        public static void init(){
            if(killProp == null) {
                killProp = new Properties();
                KILL_LIST_FILE = new File(killFilePath);
                try {
                    killProp.load(new FileReader(KILL_LIST_FILE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!KILL_LIST_FILE.exists()) {
                    KILL_LIST_FILE.getParentFile().mkdir();
                }
            }
        }

        public static boolean setProperty(String key, String value){
//        boolean isSuccessful = true;
            killProp.setProperty(key, value);
            try {
                killProp.store(new FileOutputStream(KILL_LIST_FILE), killComment);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        public static String getProperty(String key){
            return killProp.getProperty(key);
        }

        // 将文件中值为KILL_ENABLE的文件选出来
        public static List<String> traverseProperty(){
            List<String> pkgs = new ArrayList<>();

            for (String key : killProp.stringPropertyNames()) {
                if(getProperty(key).equals(KILL_ENABLE))
                    pkgs.add(key);
            }

            return pkgs;
        }
    }
}
