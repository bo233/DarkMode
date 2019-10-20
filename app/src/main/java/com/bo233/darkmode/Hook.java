package com.bo233.darkmode;


import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bo233.darkmode.util.MyProperties;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


//, IXposedHookInitPackageResources
public class Hook implements IXposedHookLoadPackage {
//    private MyProperties properties;
    private static final int textColor = 0xff808080;
    private static final int backgndColor = 0xff101010;
//    public static ClassLoader staticClassLoader = null;
    public static View staticSwitchView = null;
//    public boolean setViewFlag = false; // 判断是否是“设置夜间模式开关”的标志
//    private final String SETTINGPATH = "/sdcard/Android/data/com.bo233.darkmode/settings.ini";

//    @Override
//    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        if (resparam.packageName.equals("com.android.systemui"))
//            return;
//
//    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        ClassLoader loader = loadPackageParam.classLoader;
        String pkgName = loadPackageParam.packageName;

        MyProperties.init();
//        setViewFlag = "true".equals(MyProperties.getProperty(MyProperties.SELF_SETTING));

//        properties = new MyProperties(SETTINGPATH);


        //hook自身以检测模块是否激活
        if(pkgName.equals("com.bo233.darkmode")) {
            XposedHelpers.findAndHookMethod("com.bo233.darkmode.MainActivity", loadPackageParam.classLoader,
                    "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }

//        if(MyProperties.getProperty(MyProperties.SELF_SETTING)!=null &&
//                MyProperties.getProperty(MyProperties.SELF_SETTING).equals("true"))
//            if(MyProperties.getProperty(MyProperties.SELF_SETTING_PKG_NAME)!=null &&
//                    MyProperties.getProperty(MyProperties.SELF_SETTING_PKG_NAME).equals(pkgName))
//                setClassLoader(loader);


        if(!pkgName.equals("com.android.systemui"))
            hookJudge(loader, pkgName);
//            normalHookExec(loader, pkgName);
    }


//    private void setClassLoader(ClassLoader classLoader){
//        this.staticClassLoader = classLoader;
//    }


    /**
     * 通过包名来判断使用何种模式来hook
     */
    private void hookJudge(ClassLoader loader, String pkgName){
//        Log.d("hookJudge", pkgName+":"+MyProperties.ModeProperties.getProperty(pkgName));
//        if(setViewFlag)
//            hookNightModeSwitch(loader);
        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
            return;


        if(MyProperties.ModeProperties.getProperty(pkgName)==null ||
                MyProperties.ModeProperties.getProperty(pkgName).equals(MyProperties.MODE_NORMAL))
            normalHookExec(loader, pkgName);

        else if(MyProperties.ModeProperties.getProperty(pkgName).equals(MyProperties.MODE_OFF))
            return;

        else if(MyProperties.ModeProperties.getProperty(pkgName).equals(MyProperties.MODE_SELF))
            ; // TODO
    }


    private void normalHookExec(ClassLoader classLoader, String pkgName){

        try{
            hookText(classLoader);
            hookDrawColor(classLoader);
            hookViewBackground(classLoader);
            hookMoreView(classLoader);
//            hookCardView(staticClassLoader);

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
//                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                        if(MyProperties.getProperty("open")==null || MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                    if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                        return;

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
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;

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
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;

                        param.args[0] = new ColorDrawable(backgndColor);
                    }

                });

        ////////////////hook setBackgroundResource (int resid)//////////////////
//        XposedHelpers.findAndHookMethod("android.view.View", staticClassLoader,
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


    /**
     * 通过hook住View的onDraw()方法来强行调用setBackgroundColor方法
     */
    private void hookMoreView(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
                "onDraw", Canvas.class, AttributeSet.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        if(MyProperties.getProperty("open")==null||MyProperties.getProperty("open").equals("false"))
//                            return;
                        XposedHelpers.callMethod(param.thisObject, "setBackgroundColor", backgndColor);
//                        Log.d("hookCons", "hooked");
                    }
                });
    }


    /**
     * 根据用户点击来获取对应控件
     */
    public void hookNightModeSwitch(ClassLoader classLoader){
//        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
//                "dispatchTouchEvent", MotionEvent.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        if(MyProperties.getProperty(MyProperties.KEY_SWITCH)!=null &&
//                                MyProperties.getProperty(MyProperties.SELF_SETTING).equals("true")) {
//                            staticSwitchView = (View) param.thisObject;
//                            Log.d("hookSwitch", "hooked dispatchTouchEvent");
//                            Log.d("hookSwitch", staticSwitchView.toString());
//                            MyProperties.setProperty(MyProperties.SELF_SETTING, "false");
//                        }
//                    }
//                });
//
//
//        XposedHelpers.findAndHookMethod("android.view.View", classLoader,
//                "performClick", new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        if(MyProperties.getProperty(MyProperties.KEY_SWITCH)!=null &&
//                                MyProperties.getProperty(MyProperties.SELF_SETTING).equals("true")) {
//                            staticSwitchView = (View) param.thisObject;
//                            Log.d("hookSwitch", "hooked performClick");
//                            Log.d("hookSwitch", staticSwitchView.toString());
//                            MyProperties.setProperty(MyProperties.SELF_SETTING, "false");
//                        }
//                    }
//                });

        XposedHelpers.findAndHookMethod(View.class, "setOnClickListener", View.OnClickListener.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        View view = (View) param.thisObject;
                        final View.OnClickListener listener = (View.OnClickListener) param.args[0];
                        View.OnClickListener newListener=new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                mTouchHandler.hookOnClickListener(v,mFilters);
                                MyProperties.init();

                                if(MyProperties.getProperty(MyProperties.KEY_SWITCH)!=null &&
                                        MyProperties.getProperty(MyProperties.SELF_SETTING).equals("true")){
                                    Log.d("hookSwitch", "hooked listener");
                                    if(v!=null)
                                        Log.d("hookSwitch", v.toString());
                                    MyProperties.setProperty(MyProperties.SELF_SETTING,"false");
                                }

                                if (listener==null){
                                    return ;
                                }else {
                                    listener.onClick(v);
                                }
                            }
                        };
                        param.args[0]=newListener;

                    }
                });


        XposedHelpers.findAndHookMethod(View.class, "dispatchTouchEvent", MotionEvent.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        MyProperties.init();
                        if(MyProperties.getProperty(MyProperties.KEY_SWITCH)!=null &&
                                MyProperties.getProperty(MyProperties.SELF_SETTING).equals("true")) {
                            View v = (View) param.thisObject; //处理v==null
                            Log.d("hookSwitch", "hooked View.dispatchTouchEvent");
                            if(v!=null)
                                Log.d("hookSwitch", v.toString());
                            MyProperties.setProperty(MyProperties.SELF_SETTING, "false");
                        }
                    }
        });


//        XposedHelpers.findAndHookMethod(Activity.class, "dispatchTouchEvent", MotionEvent.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        Activity activity = (Activity) param.thisObject;
//                        View v = activity.findViewById(android.R.id.content);
//                        Log.d("hookSwitch", "hooked Activity.dispatchTouchEvent");
//                        Log.d("hookSwitch", v.toString());
//                    }
//                });
    }



//    public void hookNightModeSwitch(ClassLoader loader){
//        XposedHelpers.findAndHookMethod("android.view.View", loader,
//                "onClick", View.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        if(setViewFlag) {
//                            staticSwitchView = (View) param.args[0];
//                            Log.d("hookSwitch", "hooked");
//                            setViewFlag = false;
//                        }
//                    }
//                });
//    }

//    public static ClassLoader getClassLoader(){
//        return loader;
//    }

//    private void hookCardView(ClassLoader staticClassLoader){
//        try {
//            XposedHelpers.findAndHookMethod("android.support.v7.widget.CardView", staticClassLoader,
//                    "setCardBackgroundColor", int.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
////                            if (MyProperties.getProperty("open") == null || MyProperties.getProperty("open").equals("false"))
////                                return;
//
//                            Log.d("hookCardView", "int");
//                            param.args[0] = backgndColor;
//                        }
//                    });
//
//            XposedHelpers.findAndHookMethod("android.support.v7.widget.CardView", staticClassLoader,
//                    "setCardBackgroundColor", ColorStateList.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
////                            if (MyProperties.getProperty("open") == null || MyProperties.getProperty("open").equals("false"))
////                                return;
//
//                            ColorStateList c = ColorStateList.valueOf(backgndColor);
//
//                            param.args[0] = c;
//                            Log.d("hookCardView", "ColorStateList");
//                        }
//                    });
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}

