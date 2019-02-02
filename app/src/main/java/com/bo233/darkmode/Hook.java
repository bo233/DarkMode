package com.bo233.darkmode;


import android.app.Activity;
import android.content.res.XResources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bo233.darkmode.util.HookText;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class Hook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    private Properties properties;
    private final int textColor = 0xff808080;
    private final int backgndColor = 0xff101010;

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (resparam.packageName.equals("com.android.systemui"))
            return;

//        if (resparam.packageName.equals("com.android.settings")) {
////            resparam.res.setReplacement(resparam.packageName, "Color", "textColor", 0x404040);
////            resparam.res.setReplacement("com.android.settings:color/colorTitle", "#404040");
//            Log.d("HookText", resparam.packageName + "Done.");
//        }

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        ClassLoader loader = loadPackageParam.classLoader;

        properties = new Properties();
        try {
            properties.load(new FileReader(MainPreferences.PROP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(loadPackageParam.packageName.equals("com.bo233.darkmode")) {
            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainActivity", loadPackageParam.classLoader,
                    "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }

        if(!loadPackageParam.packageName.equals("com.android.systemui"))
            hookExec(loader);

    }

    private void hookExec(ClassLoader classLoader){
//        Boolean open = properties.getProperty("open")!=null && properties.getProperty("open").equals("true");
//        if(open){
////            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainPreferences", classLoader,
////                    "isOpen", XC_MethodReplacement.returnConstant(true));
//            Log.d("hookExec", "open:true");
//        }
//        else
//            Log.d("hookExec", "open:false");

        try{
            hookText(classLoader);
//            hookText2(classLoader);
            hookDrawColor(classLoader);
            hookBackgroundColor(classLoader);
            hookBackgroundDrawable(classLoader);
        } catch (Exception e){
                e.printStackTrace();
        }

    }

    //?????????????????????????????????????
    private void hookText(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        int temp = (int)param.args[0];
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = textColor;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });


    }


    private void hookText2(ClassLoader classLoader){
//        XposedHelpers.callMethod(, "setTextColor", "0x404040");
    }

//    @Override
//    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        if (!resparam.packageName.equals("com.android.systemui"))
//            return;
//
//        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar", new XC_LayoutInflated() {
//            @Override
//            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
//                TextView clock = (TextView) liparam.view.findViewById(
//                        liparam.res.getIdentifier("clock", "id", "com.android.systemui"));
//                clock.setTextColor(Color.RED);
//            }
//        });
//    }

    private void hookDrawColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.graphics.Canvas", classLoader,
                "drawColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                        return;

//                    int curColor=(int)param.args[0];
                    int curColor=(int)param.args[0];

                    int red = ((curColor&0xff0000)>>16);
                    int green = ((curColor&0xff00)>>8);
                    int blue = (curColor&0x0000ff);
                    if(red>=235&&green>=235&&blue>=235)
                        param.args[0] = backgndColor;
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
    }

    private void hookBackgroundColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "setBackgroundColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                        return;

                        param.args[0] = backgndColor;
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
    }

    private void hookBackgroundDrawable(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "setBackgroundDrawable", Drawable.class, new XC_MethodHook() { //此方法在API级别16中已弃用
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                        return;

                    param.args[0] = new ColorDrawable(backgndColor);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
    }
}

