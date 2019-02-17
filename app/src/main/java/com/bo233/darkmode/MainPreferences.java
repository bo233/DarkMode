package com.bo233.darkmode;

import android.content.Intent;
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

import cn.addapp.pickers.picker.TimePicker;

public class MainPreferences extends PreferenceFragment {
    private CheckBoxPreference openPreference;
    private CheckBoxPreference setByAppPreference;
    private CheckBoxPreference timePreference;
    private Properties properties;
    public final static File PROP_FILE=new File("/sdcard/Android/data/com.bo233.darkmode/settings.ini");
//    public final static File PROP_FILE = new File(getExternalFilesDir()+"settings.ini");
//    Log.d("MainPreferences", "地址："+Environment.getExternalStorageDirectory().getAbsolutePath());
    private final String comment = "This is a settings.";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.main_preferences);
        openPreference = (CheckBoxPreference) findPreference("key_switch");
        setByAppPreference = (CheckBoxPreference) findPreference("setting_by_apps");
        timePreference = (CheckBoxPreference) findPreference("time_switch");
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

        setByAppPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getActivity(), SettingByAppsActivity.class);
                startActivity(intent);
                return false;
            }
        });

        timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean timeSwitch = (boolean)newValue;
                properties.setProperty("time_switch",timeSwitch+"");
                try {
                    properties.store(new FileOutputStream(PROP_FILE),comment);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(timeSwitch){
                    TimePicker picker = new TimePicker(getActivity(), TimePicker.HOUR_24);
                    picker.setRangeStart(0, 0);
                    picker.setRangeEnd(23, 59);
                    picker.setTopLineVisible(false);
                    picker.setLineVisible(false);
                    picker.setWheelModeEnable(false);
                    picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                        @Override
                        public void onTimePicked(String hour, String minute) {

                        }
                    });
                    picker.show();

                }
                return true;
            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
