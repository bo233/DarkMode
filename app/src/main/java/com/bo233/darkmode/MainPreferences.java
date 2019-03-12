package com.bo233.darkmode;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TimePicker;

import com.bo233.darkmode.util.MyProperties;
import com.bo233.darkmode.util.MyTimer;

import static com.bo233.darkmode.util.AppKiller.*;

//import cn.addapp.pickers.picker.TimePicker;

public class MainPreferences extends PreferenceFragment {
    private CheckBoxPreference openPreference;
    private CheckBoxPreference setByAppPreference;
    private CheckBoxPreference timePreference;
//    private MyProperties properties;
    private MyTimer timer;

//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.main_preferences);
        openPreference = (CheckBoxPreference) findPreference("key_switch");
        setByAppPreference = (CheckBoxPreference) findPreference("setting_by_apps");
        timePreference = (CheckBoxPreference) findPreference("time_switch");
//        properties = new MyProperties(MyProperties.SETTINGPATH);

        timer = new MyTimer(getActivity());
        timePreference.setSummary(getString(R.string.time_set_summary)+timer.getStringTime());

        openPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean open=(boolean)o;
                Toast.makeText(getActivity(), getString(R.string.restart_app_remind), Toast.LENGTH_SHORT).show();
                MyProperties.setProperty(MyProperties.KEY_SWITCH,open+"");
//                killRunningApps();
//                kill("com.android.settings");
                killSelectedApps();
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
                MyProperties.setProperty(MyProperties.TIME_SWITCH,timeSwitch+"");


                if(timeSwitch) {
                    askAutoSwitchTime();
//                    timePreference.setSummary("勾选后设定时间段，当前的时间段为"+timer.getStringTime());
                    timer.start();

                }
                else{
//                    timePreference.setSummary("勾选后设定时间段");
                    timer.cancel();
                }

                return true;
            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 设定自动开关的时间
     */
    private void askAutoSwitchTime(){

        final TimePickerDialog endingTimeDialog = new TimePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                MyProperties.setProperty(MyTimer.END_HOUR,hourOfDay+"");
                MyProperties.setProperty(MyTimer.END_MIN,minute+"");
                timePreference.setSummary(getString(R.string.time_set_summary)+timer.getStringTime());

            }
        }, Integer.parseInt(MyProperties.getProperty(MyProperties.END_HOUR)), Integer.parseInt(MyProperties.getProperty(MyProperties.END_MIN)), true);

        final TimePickerDialog beginningTimeDialog = new TimePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                MyProperties.setProperty(MyTimer.BEGIN_HOUR,hourOfDay+"");
                MyProperties.setProperty(MyTimer.BEGIN_MIN,minute+"");
                endingTimeDialog.show();
            }
        }, Integer.parseInt(MyProperties.getProperty(MyProperties.BEGIN_HOUR)), Integer.parseInt(MyProperties.getProperty(MyProperties.BEGIN_MIN)), true);

        beginningTimeDialog.setCancelable(false);
        beginningTimeDialog.setCanceledOnTouchOutside(false);
        beginningTimeDialog.setTitle(getString(R.string.end_time_title));
        beginningTimeDialog.show();

        endingTimeDialog.setCancelable(false);
        endingTimeDialog.setCanceledOnTouchOutside(false);
        endingTimeDialog.setTitle(getString(R.string.begin_time_title));

//        beginningTimeDialog.setOnKeyListener();

        beginningTimeDialog.setOnCancelListener(new TimePickerDialog.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog) {
                beginningTimeDialog.show();
                Toast.makeText(getActivity(), getString(R.string.dont_cancel), Toast.LENGTH_SHORT).show();
            }
        });

        endingTimeDialog.setOnCancelListener(new TimePickerDialog.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog) {
                endingTimeDialog.show();
                Toast.makeText(getActivity(), getString(R.string.dont_cancel), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
