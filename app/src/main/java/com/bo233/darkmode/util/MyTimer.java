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
    private MyProperties properties;
    private AlarmManager beginAlarmManager, endAlarmManager;
    private Context context;
    private static int mBeginHour, mBeginMin, mEndHour, mEndMin;
    private static Calendar beginTime, endTime;
    private Intent beginIntent, endIntent;
    private PendingIntent beginPendingIntent, endPendingIntent;

    public static final String BEGIN_HOUR = "beginning_hour";
    public static final String BEGIN_MIN = "beginning_min";
    public static final String END_HOUR = "ending_hour";
    public static final String END_MIN = "ending_min";

    public MyTimer(Context c){
        context = c;
        properties = new MyProperties(MyProperties.SETTINGPATH);
        beginAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE); //?????????
//        beginAlarmManager.setTimeZone("GMT+08:00");
        endAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE); //?????????
//        endAlarmManager.setTimeZone("GMT+08:00");
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        beginIntent = new Intent(context, MyBroadcastReceiver.class).setAction("beginAlarm");
        endIntent = new Intent(context, MyBroadcastReceiver.class).setAction("endAlarm");
        beginPendingIntent = PendingIntent.getBroadcast(context, 0, beginIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        endPendingIntent = PendingIntent.getBroadcast(context, 0, endIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        updateTime(); //注意增加异常判断和处理

    }

    /**
     * 设置并启用闹钟
     */
    public void start(){
//        if(updateTime())
//            return false;
        updateTime();



        beginAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, beginTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, beginPendingIntent);
        endAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, endTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, endPendingIntent);

        Log.d("MyBroadcaster", "sent");

//        return true;
    }

    /**
     *取消已设定的闹钟
     */
    public void cancel(){
        beginAlarmManager.cancel(beginPendingIntent);
        endAlarmManager.cancel(endPendingIntent);
        Log.d("MyBroadcaster", "cancel");
    }

    public String getStringTime(){ // need modify
        updateTime();
        String s = new SimpleDateFormat("HH:mm", Locale.CHINA).format(beginTime.getTime()) + " - "+ new SimpleDateFormat("HH:mm", Locale.CHINA).format(endTime.getTime());
        return s;
    }

    /**
     * 从ini文件中读取设定的时间
     */
    private void updateTime(){
        try{
            mBeginHour = Integer.parseInt(properties.getProperty(BEGIN_HOUR).trim());
            mBeginMin = Integer.parseInt(properties.getProperty(BEGIN_MIN).trim());
            mEndHour = Integer.parseInt(properties.getProperty(END_HOUR).trim());
            mEndMin = Integer.parseInt(properties.getProperty(END_MIN).trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        beginTime.set(Calendar.HOUR_OF_DAY, mBeginHour);
        beginTime.set(Calendar.MINUTE, mBeginMin);
        beginTime.set(Calendar.SECOND, 0);
        endTime.set(Calendar.HOUR_OF_DAY, mEndHour);
        endTime.set(Calendar.MINUTE, mEndMin);
        endTime.set(Calendar.SECOND, 0);

    }
}
