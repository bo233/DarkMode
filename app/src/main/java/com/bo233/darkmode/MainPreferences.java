package com.bo233.darkmode;

import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainPreferences extends PreferenceFragment {
    private CheckBoxPreference openPreference;
    private CheckBoxPreference test;
    private Properties properties;
    public final static File PROP_FILE=new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
//    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private final String comment = "This is a settings.";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.main_preferences);
        openPreference = (CheckBoxPreference) findPreference("key_switch");
        test = (CheckBoxPreference) findPreference("test");
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

        openPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean open=(boolean)o;
                Toast.makeText(getActivity(), "重新启动所有应用以生效", Toast.LENGTH_SHORT).show();
                properties.setProperty("open",open+"");
                try {
                    properties.store(new FileOutputStream(PROP_FILE),comment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        test.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(isOpen())
                    Toast.makeText(getActivity(), "open:true", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "open:false", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private Boolean isOpen(){
        return false;
    }
}
