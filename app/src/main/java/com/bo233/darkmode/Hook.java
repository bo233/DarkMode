package com.bo233.darkmode;


import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class Hook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    private Properties properties;
    private final int textColor = 0xff808080;
    private final int backgndColor = 0xff101010;

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (resparam.packageName.equals("com.android.systemui"))
            return;

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        ClassLoader loader = loadPackageParam.classLoader;
        String packageName = loadPackageParam.packageName;

        properties = new Properties();
        try {
            properties.load(new FileReader(MainPreferences.PROP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(packageName.equals("com.bo233.darkmode")) {
            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainActivity", loadPackageParam.classLoader,
                    "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }

        if(!packageName.equals("com.android.systemui"))
            hookExec(loader, packageName);

    }

    private void hookExec(ClassLoader classLoader, String packageName){

        try{
            hookText(classLoader);
//            hookText2(classLoader);
            hookDrawColor(classLoader);
            hookViewBackground(classLoader);
//            hookBackgroundDrawable(classLoader);
        } catch (Exception e){
                e.printStackTrace();
        }

    }

    private void hookText(ClassLoader classLoader){

        //////////////Hook setTextColor(int)
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = textColor;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });

        //////////////Hook setTextColor(ColorStateList)
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", ColorStateList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        ColorStateList c = ColorStateList.valueOf(textColor);

                        param.args[0] = c;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });

        //////////////Hook setHintTextColor(int)
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setHintTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = textColor;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });

        //////////////Hook setHintTextColor(ColorStateList)
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setHintTextColor", ColorStateList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(properties.getProperty("open")==null || properties.getProperty("open").equals("false"))
                            return;

                        ColorStateList c = ColorStateList.valueOf(textColor);

                        param.args[0] = c;
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });


    }


//    private void hookText2(ClassLoader classLoader){
//        XposedHelpers.callMethod(, "setTextColor", "0x404040");
//    }

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

//    private void hookBackgroundColor(ClassLoader classLoader){
//        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
//                "setBackgroundColor", int.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
//                        return;
//
//                        param.args[0] = backgndColor;
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                }
//            });
//    }
//
//    private void hookBackgroundDrawable(ClassLoader classLoader){
//        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
//                "setBackgroundDrawable", Drawable.class, new XC_MethodHook() { //此方法在API级别16中已弃用
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
//                        return;
//
//                    param.args[0] = new ColorDrawable(backgndColor);
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                }
//            });
//    }

    private void hookViewBackground(ClassLoader classLoader){
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

