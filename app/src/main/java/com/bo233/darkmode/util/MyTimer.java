package com.bo233.darkmode.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class MyTimer {
//    private MyProp properties;
    private AlarmManager alarmManager;
    private Context context;
    private int mBeginHour, mBeginMin, mEndHour, mEndMin;
    private Calendar beginTime, endTime, lightBeginTime, lightEndTime;
    private Intent beginIntent, endIntent, lightBeginIntent, lightEndIntent;
    private PendingIntent beginPendingIntent, endPendingIntent, lightBeginPendingIntent, lightEndPendingIntent;

    public static final String BEGIN_HOUR = "beginning_hour";
    public static final String BEGIN_MIN = "beginning_min";
    public static final String END_HOUR = "ending_hour";
    public static final String END_MIN = "ending_min";

    public MyTimer(Context c){
        context = c;
//        properties = new MyProp(MyProp.SETTINGPATH);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        beginAlarmManager.setTimeZone("GMT+08:00");
//        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        endAlarmManager.setTimeZone("GMT+08:00");
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        lightBeginTime = Calendar.getInstance();
        lightEndTime = Calendar.getInstance();

        beginIntent = new Intent(context, MyBroadcastReceiver.class).setAction("beginAlarm");
        endIntent = new Intent(context, MyBroadcastReceiver.class).setAction("endAlarm");
        lightBeginIntent = new Intent(context, MyBroadcastReceiver.class).setAction("lightSensorOn");
        lightEndIntent = new Intent(context, MyBroadcastReceiver.class).setAction("lightSensorOff");

        beginPendingIntent = PendingIntent.getBroadcast(context, 0, beginIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        endPendingIntent = PendingIntent.getBroadcast(context, 0, endIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        lightBeginPendingIntent = PendingIntent.getBroadcast(context, 0,
                lightBeginIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        lightEndPendingIntent = PendingIntent.getBroadcast(context, 0,
                lightEndIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        updateTime();

    }

    /**
     * 设置并启用按时间切换的闹钟
     */
    public void startTimeAlarm(){
//        if(updateTime())
//            return false;
        updateTime();

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, beginTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, beginPendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, endTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, endPendingIntent);

        Log.d("MyBroadcaster", "sent");

//        return true;
    }

    /**
     *取消已设定的按时间切换的闹钟
     */
    public void cancelTimeAlarm(){
        alarmManager.cancel(beginPendingIntent);
        alarmManager.cancel(endPendingIntent);
        Log.d("MyBroadcaster", "cancelTimeAlarm");
    }

    public void startLightAlarm(){
        updateTime();

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lightBeginTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, lightBeginPendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lightEndTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, lightEndPendingIntent);
    }

    public void cancelLightAlarm(){
        alarmManager.cancel(lightBeginPendingIntent);
        alarmManager.cancel(lightEndPendingIntent);
    }

    public String getStringTime(){
        updateTime();
        String s = new SimpleDateFormat("HH:mm", Locale.CHINA).format(beginTime.getTime()) + " - "+ new SimpleDateFormat("HH:mm", Locale.CHINA).format(endTime.getTime());
        return s;
    }

    /**
     * 从ini文件中读取设定的时间
     */
    private void updateTime(){
        try{
            mBeginHour = Integer.parseInt(MyProp.getProp(BEGIN_HOUR).trim());
            mBeginMin = Integer.parseInt(MyProp.getProp(BEGIN_MIN).trim());
            mEndHour = Integer.parseInt(MyProp.getProp(END_HOUR).trim());
            mEndMin = Integer.parseInt(MyProp.getProp(END_MIN).trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        beginTime.set(Calendar.HOUR_OF_DAY, mBeginHour);
        beginTime.set(Calendar.MINUTE, mBeginMin);
        beginTime.set(Calendar.SECOND, 0);
        endTime.set(Calendar.HOUR_OF_DAY, mEndHour);
        endTime.set(Calendar.MINUTE, mEndMin);
        endTime.set(Calendar.SECOND, 0);

        lightBeginTime.setTime(beginTime.getTime());
        lightBeginTime.add(Calendar.MINUTE, -10);
        lightEndTime.setTime(endTime.getTime());
        lightEndTime.add(Calendar.MINUTE, 10);

    }
}
