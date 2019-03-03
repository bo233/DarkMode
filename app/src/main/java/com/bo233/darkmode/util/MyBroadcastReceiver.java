package com.bo233.darkmode.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";
    private MyProperties properties ;
    public MyBroadcastReceiver(){ //???????????????????????????
        super();
//        properties = new MyProperties(MyProperties.SETTINGPATH);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("beginAlarm".equals(intent.getAction())) {
//            properties.setProperty(MyProperties.KEY_SWITCH, "true");
            AppKiller.killSelectedApps();
            Log.d("myReceive", "open:true");
        }
        else if ("endAlarm".equals(intent.getAction())) {
//            properties.setProperty(MyProperties.KEY_SWITCH, "false");
            AppKiller.killSelectedApps();
            Log.d("myReceive", "open:false");
        }
    }
}
