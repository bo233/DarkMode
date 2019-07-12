package com.bo233.darkmode.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MyBroadcastReceiver extends BroadcastReceiver {
//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
//    private MyProperties properties ;
    public MyBroadcastReceiver(){
        super();
//        properties = new MyProperties(MyProperties.SETTINGPATH);
        MyProperties.init();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "beginAlarm":
                MyProperties.setProperty(MyProperties.KEY_SWITCH, "true");
                AppKiller.killSelectedApps();
                Log.d("myReceive", "open:true");
                break;
            case "endAlarm":
                MyProperties.setProperty(MyProperties.KEY_SWITCH, "false");
                AppKiller.killSelectedApps();
                Log.d("myReceive", "open:false");
                break;
            case "setNightModeView":
                Log.d("myReceiver", "setNightModeView");
                collapseStatusBar(context);
                break;
        }
    }


    /**
     *
     * 收起通知栏
     * @param context
     */
    public static void collapseStatusBar(Context context) {
        try {
            @SuppressLint("WrongConstant") Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;

            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }


}
