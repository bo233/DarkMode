package com.bo233.darkmode.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

public class MyBroadcastReceiver extends BroadcastReceiver {
//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
//    private MyProp properties ;
    private static LightSensorUtils mLightSensorUtils;

    public MyBroadcastReceiver(){
        super();
//        properties = new MyProp(MyProp.SETTINGPATH);
        MyProp.init();
        mLightSensorUtils = LightSensorUtils.getInstance();
//        mLightSensorUtils.init(context);
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        if(!mLightSensorUtils.isInit())
            mLightSensorUtils.init(context);

        switch (intent.getAction()) {
            case "beginAlarm":
                MyProp.setProp(MyProp.KEY_SWITCH, "true");
                AppKiller.killSelectedApps();
                Log.d("myReceive", "open:true");
                break;
            case "endAlarm":
                MyProp.setProp(MyProp.KEY_SWITCH, "false");
                AppKiller.killSelectedApps();
                Log.d("myReceive", "open:false");
                break;
            case "setNightModeView":
                Log.d("myReceiver", "setNightModeView");
                collapseStatusBar(context);
                MyProp.setProp(MyProp.SELF_SETTING, "true");
                break;
            case "lightSensorOn":
                mLightSensorUtils.registerSensor();
                break;
            case "lightSensorOff":
                mLightSensorUtils.unRegisterSensor();
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
