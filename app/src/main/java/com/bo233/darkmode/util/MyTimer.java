package com.bo233.darkmode.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class MyTimer {
    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
    private MyProperties properties;
    private AlarmManager beginAlarmManager, endAlarmManager;
    private Context context;
    private int beginHour, beginMin, endHour, endMin;
    private Calendar beginTime, endTime;
    private Intent beginIntent, endIntent;
    private PendingIntent beginPendingIntent, endPendingIntent;

    private final String BEGIN_HOUR = "beginning_hour";
    private final String BEGIN_MIN = "beginning_min";
    private final String END_HOUR = "ending_hour";
    private final String END_MIN = "ending_min";

    public MyTimer(Context c){
        context = c;
        properties = new MyProperties(SETTINGPATH);
        beginAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE); //?????????
        beginAlarmManager.setTimeZone("GMT+08:00");
        endAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE); //?????????
        endAlarmManager.setTimeZone("GMT+08:00");
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        beginIntent = new Intent(context, MyBroadcastReceiver.class);
        endIntent = new Intent(context, MyBroadcastReceiver.class);
        beginIntent.setAction("beginAlarm");
        endIntent.setAction("endAlarm");
        beginPendingIntent = PendingIntent.getBroadcast(context, 110, beginIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        endPendingIntent = PendingIntent.getBroadcast(context, 110, endIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        getSettingTime(); //注意增加异常判断和处理

    }

    /**
     * 设置并启用闹钟
     * @return 是否成功
     */
    public boolean start(){
        if(getSettingTime())
            return false;

        beginTime.set(Calendar.HOUR_OF_DAY, beginHour);
        beginTime.set(Calendar.MINUTE, beginMin);
        beginTime.set(Calendar.SECOND, 0);
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
        endTime.set(Calendar.MINUTE, endMin);
        endTime.set(Calendar.SECOND, 0);

        beginAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_DAY,
                beginTime.getTimeInMillis(), beginPendingIntent);
        endAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_DAY,
                endTime.getTimeInMillis(), endPendingIntent);

        return true;
    }

    /**
     *取消已设定的闹钟
     */
    public void cancel(){

    }

    private boolean getSettingTime(){
        boolean isSuccessful = true;
        try{
            beginHour = Integer.parseInt(properties.getProperty(BEGIN_HOUR));
            beginMin = Integer.parseInt(properties.getProperty(BEGIN_MIN));
            endHour = Integer.parseInt(properties.getProperty(END_HOUR));
            endMin = Integer.parseInt(properties.getProperty(END_MIN));
        }catch (Exception e){
            e.printStackTrace();
            isSuccessful = false;
        }

        return isSuccessful;
    }
}
