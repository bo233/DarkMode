package com.bo233.darkmode;


import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.bo233.darkmode.util.MyProperties;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


//, IXposedHookInitPackageResources
public class Hook implements IXposedHookLoadPackage {
//    private MyProperties properties;
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

//        properties = new MyProperties(SETTINGPATH);
        MyProperties.init();

        if(packageName.equals("com.bo233.darkmode")) {
            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainActivity", loadPackageParam.classLoader,
                    "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }


        if(!packageName.equals("com.android.systemui"))
            hookExec(loader, packageName);

    }

    private void hookExec(ClassLoader classLoader, String packageName){
//        Log.d("233Xposed", MyProperties.getProperty(MyProperties.KEY_SWITCH));

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


    private void hookText(ClassLoader classLoader){

        /**
         * Hook setTextColor(int)
         */
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = textColor;
                    }

                });


        /**
         * Hook setTextColor(ColorStateList)
         */
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setTextColor", ColorStateList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
                            return;

                        ColorStateList c = ColorStateList.valueOf(textColor);

                        param.args[0] = c;
                    }

                });

        /**
         * Hook setHintTextColor(int)
         */
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setHintTextColor", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = textColor;
                    }

                });

        /**
         * Hook setHintTextColor(ColorStateList)
         */
        XposedHelpers.findAndHookMethod("android.widget.TextView", classLoader,
                "setHintTextColor", ColorStateList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
                            return;

                        ColorStateList c = ColorStateList.valueOf(textColor);

                        param.args[0] = c;
                    }

                });


    }



    private void hookDrawColor(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.graphics.Canvas", classLoader,
                "drawColor", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                        return;

//                    int curColor=(int)param.args[0];
//
//                    int red = ((curColor&0xff0000)>>16);
//                    int green = ((curColor&0xff00)>>8);
//                    int blue = (curColor&0x0000ff);
//                    if(red>=235&&green>=235&&blue>=235)
                        param.args[0] = backgndColor;
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
                        super.beforeHookedMethod(param);
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = backgndColor;
                    }

                });

        /**
         * hook setBackgroundDrawable(Drawable)
         */
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "setBackgroundDrawable", Drawable.class, new XC_MethodHook() { //此方法在API级别16中已弃用
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = new ColorDrawable(backgndColor);
                    }

                });

        /**
         * hook setBackground(Drawable)
         */
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "setBackground", Drawable.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;

                        param.args[0] = new ColorDrawable(backgndColor);
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
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
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
                            if (MyProperties.getProperty("open") == null || MyProperties.getProperty("open").equals("false"))
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
                            if (MyProperties.getProperty("open") == null || MyProperties.getProperty("open").equals("false"))
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

