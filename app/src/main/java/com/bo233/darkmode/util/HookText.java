package com.bo233.darkmode.util;

import android.content.res.XResources;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class HookText implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
//    private String packageName;
//    public HookText(String s){
//        packageName = s;
//    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XResources.setSystemWideReplacement("android", "bool", "config_unplugTurnsOnScreen", false);
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        if (resparam.packageName.equals("com.android.systemui"))
//            return;

        resparam.res.setReplacement(resparam.packageName, "integer", "textColor", 0x404040);
        Log.d("HookText","Done.");

//        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar", new XC_LayoutInflated() {
//            @Override
//            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
//                TextView clock = (TextView) liparam.view.findViewById(
//                        liparam.res.getIdentifier("clock", "id", "com.android.systemui"));
//                clock.setTextColor(Color.RED);
//            }
//        });
    }
}

//    private void hookText2(ClassLoader classLoader){
//        @Override
//        public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
//            // replacements only for SystemUI
//            if (!resparam.packageName.equals("com.android.systemui"))
//                return;
//
//            // different ways to specify the resources to be replaced
//            resparam.res.setReplacement(0x7f080083, "YEAH!"); // WLAN toggle text. You should not do this because the id is not fixed. Only for framework resources, you could use android.R.string.something
//            resparam.res.setReplacement("com.android.systemui:string/quickpanel_bluetooth_text", "WOO!");
//            resparam.res.setReplacement("com.android.systemui", "string", "quickpanel_gps_text", "HOO!");
//            resparam.res.setReplacement("com.android.systemui", "integer", "config_maxLevelOfSignalStrengthIndicator", 6);
//            resparam.res.setReplacement("com.android.systemui", "integer", "textColor", 0x404040);
//
//        }
//    }