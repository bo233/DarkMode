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

import com.bo233.darkmode.util.MyProp;
import com.bo233.darkmode.util.MyTimer;

import static com.bo233.darkmode.util.AppKiller.*;

//import cn.addapp.pickers.picker.TimePicker;

public class MainPreferences extends PreferenceFragment {
    private CheckBoxPreference openPref;
    private CheckBoxPreference setByAppPref;
    private CheckBoxPreference timePref;
    private CheckBoxPreference lightSensorPref;
//    private MyProp properties;
    private MyTimer timer;

//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.main_preferences);
        openPref = (CheckBoxPreference) findPreference("key_switch");
        setByAppPref = (CheckBoxPreference) findPreference("setting_by_apps");
        timePref = (CheckBoxPreference) findPreference("time_switch");
        lightSensorPref = (CheckBoxPreference) findPreference("switch_by_light_sensor");
//        properties = new MyProp(MyProp.SETTINGPATH);

        timer = new MyTimer(getActivity());
        timePref.setSummary(getString(R.string.time_set_summary)+timer.getStringTime());

        openPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean open=(boolean)o;
                Toast.makeText(getActivity(), getString(R.string.restart_app_remind), Toast.LENGTH_SHORT).show();
                MyProp.setProp(MyProp.KEY_SWITCH,open+"");
//                killRunningApps();
//                kill("com.android.settings");
                killSelectedApps();
                return true;
            }
        });

        setByAppPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getActivity(), SettingByAppActivity.class);
                startActivity(intent);
                return false;
            }
        });

        timePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean timeSwitch = (boolean)newValue;
                MyProp.setProp(MyProp.TIME_SWITCH, timeSwitch+"");


                if(timeSwitch) {
                    askAutoSwitchTime();
//                    timePref.setSummary("勾选后设定时间段，当前的时间段为"+timer.getStringTime());
                    if("true".equals(MyProp.getProp(MyProp.LIGHT_SENSOR))){
                        timer.cancelLightAlarm();
                        timer.startLightAlarm();
                    }
                    timer.startTimeAlarm();
                }
                else{
//                    timePref.setSummary("勾选后设定时间段");
                    timer.cancelTimeAlarm();
                    lightSensorPref.setChecked(false);
                    MyProp.setProp(MyProp.LIGHT_SENSOR, "false");
                    timer.cancelTimeAlarm();
                    timer.startLightAlarm();
                }

                return true;
            }
        });

        lightSensorPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String s = MyProp.getProp(MyProp.TIME_SWITCH);

                if("true".equals(s)) {
                    boolean b = (boolean) o;
                    MyProp.setProp(MyProp.LIGHT_SENSOR, b + "");
                    timer.cancelTimeAlarm();
                    timer.startLightAlarm();

                    return true;
                }
                else{
                    Toast.makeText(getActivity(), R.string.light_sensor_warning, Toast.LENGTH_SHORT).show();
                    return false;
                }
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

                MyProp.setProp(MyTimer.END_HOUR,hourOfDay+"");
                MyProp.setProp(MyTimer.END_MIN,minute+"");
                timePref.setSummary(getString(R.string.time_set_summary)+timer.getStringTime());

            }
        }, Integer.parseInt(MyProp.getProp(MyProp.END_HOUR)), Integer.parseInt(MyProp.getProp(MyProp.END_MIN)), true);

        final TimePickerDialog beginningTimeDialog = new TimePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                MyProp.setProp(MyTimer.BEGIN_HOUR,hourOfDay+"");
                MyProp.setProp(MyTimer.BEGIN_MIN,minute+"");
                endingTimeDialog.show();
            }
        }, Integer.parseInt(MyProp.getProp(MyProp.BEGIN_HOUR)), Integer.parseInt(MyProp.getProp(MyProp.BEGIN_MIN)), true);

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
