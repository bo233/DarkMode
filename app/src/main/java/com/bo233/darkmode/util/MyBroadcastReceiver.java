package com.bo233.darkmode.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
                break;
        }
    }
}
