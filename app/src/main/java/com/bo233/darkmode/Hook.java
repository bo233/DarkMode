package com.bo233.darkmode;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;

import com.bo233.darkmode.util.MyProperties;

import java.io.FileReader;
import java.io.IOException;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


//, IXposedHookInitPackageResources
public class Hook implements IXposedHookLoadPackage {
    private MyProperties properties;
    private final int textColor = 0xff808080;
    private final int backgndColor = 0xff101010;
    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";

//    @Override
//    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        if (resparam.packageName.equals("com.android.systemui"))
//            return;
//
//    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        ClassLoader loader = loadPackageParam.classLoader;
        String packageName = loadPackageParam.packageName;

        properties = new MyProperties(SETTINGPATH);

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
            hookDrawColor(classLoader);
            hookViewBackground(classLoader);
            hookView(classLoader);
//            hookCardView(classLoader);
        } catch (Exception e){
                e.printStackTrace();
        }

    }

    private void hookCoolapk(ClassLoader classLoader){
          XposedHelpers.findAndHookMethod("CenterV8SFragment$setupThemeSwitch$1", classLoader, //com.coolapk.market.view.center.CenterV8SFragment
                "onCheckedChanged", CompoundButton.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                            return;
                        param.args[1] = true;
                    }
                });
    }

    private void hookText(ClassLoader classLoader){

        /**
         * Hook setTextColor(int)
         */
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


        /**
         * Hook setTextColor(ColorStateList)
         */
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

        /**
         * Hook setHintTextColor(int)
         */
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

        /**
         * Hook setHintTextColor(ColorStateList)
         */
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



    private void hookDrawColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.graphics.Canvas", classLoader,
                "drawColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
                        return;

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



    private void hookViewBackground(ClassLoader classLoader){

        /**
         * hook setBackgroundColor(int)
         */
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

        /**
         * hook setBackgroundDrawable(Drawable)
         */
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

        /**
         * hook setBackground(Drawable)
         */
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "setBackground", Drawable.class, new XC_MethodHook() {
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

        ////////////////hook setBackgroundResource (int resid)//////////////////
//        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
//                "setBackgroundResource", int.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        if(properties.getProperty("open")==null||properties.getProperty("open").equals("false"))
//                            return;
//
//                        param.args[0] = 0; //remove the background
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                    }
//                });

    }

    private void hookView(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
                        Log.d("hookCons", "hooked");
                    }
                });
    }


    private void hookCardView(ClassLoader classLoader){
        try {
            XposedHelpers.findAndHookMethod("android.support.v7.widget.CardView", classLoader,
                    "setCardBackgroundColor", int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            if (properties.getProperty("open") == null || properties.getProperty("open").equals("false"))
                                return;

                            Log.d("hookCardView", "int");
                            param.args[0] = backgndColor;
                        }
                    });

            XposedHelpers.findAndHookMethod("android.support.v7.widget.CardView", classLoader,
                    "setCardBackgroundColor", ColorStateList.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            if (properties.getProperty("open") == null || properties.getProperty("open").equals("false"))
                                return;

                            ColorStateList c = ColorStateList.valueOf(backgndColor);

                            param.args[0] = c;
                            Log.d("hookCardView", "ColorStateList");
                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

