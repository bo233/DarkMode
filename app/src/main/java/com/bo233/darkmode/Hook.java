package com.bo233.darkmode;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class Hook implements IXposedHookLoadPackage {
    private Properties properties;

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        ClassLoader loader = loadPackageParam.classLoader;

        properties = new Properties();
        try {
            properties.load(new FileReader(MainPreferences.PROP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loadPackageParam.packageName.equals("com.bo233.darkmode")) {
            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainActivity", loadPackageParam.classLoader,
                    "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }

        hookExec(loader);


    }

    private void hookExec(ClassLoader classLoader){
        Boolean open = properties.getProperty("open")!=null && properties.getProperty("open").equals("true");
        if(open){
//            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainPreferences", classLoader,
//                    "isOpen", XC_MethodReplacement.returnConstant(true));
            Log.d("hookExec", "open:true");
        }
        else
            Log.d("hookExec", "open:false");

//        hookText(classLoader, open);
        hookText(classLoader);
        hookDrawColor(classLoader);
        hookBackgroundColor(classLoader);
        hookBackgroundDrawable(classLoader);
    }

    private void hookText(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        int temp = (int)param.args[0];
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = 0x20202000;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    private void hookDrawColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.graphics.Canvas", classLoader,
                "drawColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                        return;

//                    int curColor=(int)param.args[0];

                    param.args[0] = 0xababab00;
                }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }

    private void hookBackgroundColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader, "setBackgroundColor", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                    return;

                    param.args[0] = 0xababab00;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }

    private void hookBackgroundDrawable(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader, "setBackgroundDrawable", Drawable.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                    return;

                param.args[0] = new ColorDrawable(0xababab00);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }
}

