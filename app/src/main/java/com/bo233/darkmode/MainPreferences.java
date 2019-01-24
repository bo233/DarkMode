package com.bo233.darkmode;

import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainPreferences extends PreferenceFragment {
    private CheckBoxPreference openPreference;
    private Properties properties;
//    public final static File PROP_FILE=new File("/sdcard/darkmode/settings.ini");
    public final static File PROP_FILE=new File(Environment.getExternalStorageDirectory().getPath()+"settings.ini");
    private final String comment="This is a settings.";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.main_preferences);
        openPreference = (CheckBoxPreference) findPreference("key_switch");
        properties=new Properties();

        try {
            properties.load(new FileReader(PROP_FILE));
            if(!PROP_FILE.exists()||properties.getProperty("open").equals("false")){
                openPreference.setChecked(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!PROP_FILE.exists()){
            PROP_FILE.getParentFile().mkdir();
            properties.setProperty("open","false");
            try {
                properties.store(new FileOutputStream(PROP_FILE),comment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
