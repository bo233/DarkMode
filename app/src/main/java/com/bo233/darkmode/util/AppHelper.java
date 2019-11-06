package com.bo233.darkmode.util;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.bo233.darkmode.support.AppAdapter;
import com.bo233.darkmode.support.AppInfo;
import com.bo233.darkmode.util.MyProp.KillProp;
import com.bo233.darkmode.util.MyProp.ModeProp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppHelper {
    private static List<String> allPkgNames = new ArrayList<>();
    private static List<String> killPkgNames = new ArrayList<>();
    private static PackageManager packageManager;
    private static List<PackageInfo> packages = new ArrayList<>();

    public static final int USER_APP_LIST = 0, SYSTEM_APP_LIST = 1;
    public static final int ADD = 2, REMOVE = 3;
    public static final int MODE_OFF = 4, MODE_NORMAL = 5, MODE_SELF = 6;

    private static int itemPosition = -1;
    private static AppAdapter adapter = null;
    private static ArrayList<AppInfo> appList = null;
    public static List<String> multiChoicePkgName = new ArrayList<>();

    private AppHelper(){}

    public static void setPackageManager(@NonNull Activity a){
        packageManager = a.getPackageManager();
        packages = packageManager.getInstalledPackages(0);
        updateAllPkgNames();

    }

    private static void updateAllPkgNames(){
        allPkgNames.clear();
        for (PackageInfo packageInfo : packages)
            allPkgNames.add(packageInfo.packageName);

    }

    public static List<String> getAllPkgNames(){
        return allPkgNames;
    }

    public static List<String> getKillPkgNames(){
        KillProp.init();
        killPkgNames = KillProp.traverseProp();
        return killPkgNames;
    }

    public static void setKillPkgNames(String pkgName, int flag){
        KillProp.init();

        if(flag == ADD) {
            killPkgNames.add(pkgName);
            KillProp.setProp(pkgName, KillProp.KILL_ENABLE);
//            Log.d("AppHelper", s+flag);
        }
        else if(flag == REMOVE){
            killPkgNames.remove(pkgName);
            KillProp.setProp(pkgName, KillProp.KILL_DISABLE);
//            Log.d("AppHelper", s+flag);
        }
    }

    public static void setKillPkgNames(List<String> pkgNames, int flag){
        KillProp.init();

        if(flag == ADD) {
            killPkgNames.addAll(pkgNames);
            KillProp.setProp(pkgNames, KillProp.KILL_ENABLE);
//            Log.d("AppHelper", s+flag);
            // 查重
            Set<String> set = new HashSet<>();
            for(String name : killPkgNames)
                set.add(name);
            killPkgNames.clear();
            killPkgNames.addAll(new ArrayList<>(set));
        }
        else if(flag == REMOVE){
            killPkgNames.remove(pkgNames);
            KillProp.setProp(pkgNames, KillProp.KILL_DISABLE);
//            Log.d("AppHelper", s+flag);
        }
    }

    public static void setMode(String pkgName, int flag){
        switch (flag){
            case MODE_OFF:
                MyProp.ModeProp.setProp(pkgName, MyProp.MODE_OFF);
                break;
            case MODE_NORMAL:
                MyProp.ModeProp.setProp(pkgName, MyProp.MODE_NORMAL);
                break;
            case MODE_SELF:
                MyProp.ModeProp.setProp(pkgName, MyProp.MODE_SELF);
                break;
            default:
                break;
        }
    }

    public static void setMode(List<String> pkgNames, int flag){
        switch (flag){
            case MODE_OFF:
                MyProp.ModeProp.setProp(pkgNames, MyProp.MODE_OFF);
                break;
            case MODE_NORMAL:
                ModeProp.setProp(pkgNames, MyProp.MODE_NORMAL);
                break;
            case MODE_SELF:
                ModeProp.setProp(pkgNames, MyProp.MODE_SELF);
                break;
            default:
                break;
        }
    }

    /**
     * 为更新AppAdapter获取参数
     */
    public static void setUpdateAdapterParam(final AppAdapter adapter, final ArrayList<AppInfo> appList, int position){
        AppHelper.adapter = adapter;
        AppHelper.appList = appList;
        itemPosition = position;
    }

    public static void updateAdapterByModeSet(){
        if(AppHelper.itemPosition != -1 && AppHelper.appList != null && AppHelper.adapter != null){
            String mode = ModeProp.getProp(appList.get(itemPosition).pkgName);
            if(mode == null || mode.equals(MyProp.MODE_NORMAL))
                appList.get(itemPosition).darkMode = 1;
            else if(mode.equals(MyProp.MODE_OFF))
                appList.get(itemPosition).darkMode = 0;
            else if(mode.equals(MyProp.MODE_SELF))
                appList.get(itemPosition).darkMode = 2;

            adapter.notifyDataSetChanged();
            AppHelper.itemPosition = -1;
            AppHelper.appList = null;
            AppHelper.adapter = null;
        }
    }

    public static void updateAdapterByKillSet(){
        if(AppHelper.itemPosition != -1 && AppHelper.appList != null && AppHelper.adapter != null){
            String kill = KillProp.getProp(appList.get(itemPosition).pkgName);
            if(kill == null || kill.equals(KillProp.KILL_DISABLE))
                appList.get(itemPosition).killMode = 0;
            else
                appList.get(itemPosition).killMode = 1;

            adapter.notifyDataSetChanged();
            AppHelper.itemPosition = -1;
            AppHelper.appList = null;
            AppHelper.adapter = null;
        }
    }

    /**
     * 获取应用信息列表
     */
    public static void getAppList(ArrayList<AppInfo> appList, int typeOfApps) {
//        PackageManager pm = this.getActivity().getPackageManager();
        // Return a List of all packages that are installed on the device.
//        List<PackageInfo> packages = pm.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
//             判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)	// 非系统应用
            {
                if(typeOfApps == USER_APP_LIST) {
                    AppInfo info = new AppInfo();
                    info.appName = packageInfo.applicationInfo.loadLabel(packageManager)
                            .toString();
                    info.pkgName = packageInfo.packageName;
                    info.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
                    // 获取该应用安装包的Intent，用于启动该应用
//                    info.appIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
                    String mode = MyProp.ModeProp.getProp(info.pkgName);
                    if(mode==null || mode.equals(MyProp.MODE_NORMAL))
                        info.darkMode = 1;
                    else if(mode.equals(MyProp.MODE_OFF))
                        info.darkMode = 0;
                    else if(mode.equals(MyProp.MODE_SELF))
                        info.darkMode = 2;

                    String killMode = KillProp.getProp(info.pkgName);
                    info.killMode = "true".equals(killMode) ? 1 : 0;

                    appList.add(info);
                }
            } else {
                if(typeOfApps == SYSTEM_APP_LIST) {
//             系统应用　　　　　　　　
                    AppInfo info = new AppInfo();
                    info.appName = packageInfo.applicationInfo.loadLabel(packageManager)
                            .toString();
                    info.pkgName = packageInfo.packageName;
                    info.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
                    // 获取该应用安装包的Intent，用于启动该应用
//                    info.appIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
                    String mode = MyProp.ModeProp.getProp(info.pkgName);
                    if(mode==null || mode.equals(MyProp.MODE_NORMAL))
                        info.darkMode = 1;
                    else if(mode.equals(MyProp.MODE_OFF))
                        info.darkMode = 0;
                    else if(mode.equals(MyProp.MODE_SELF))
                        info.darkMode = 2;

                    String killMode = KillProp.getProp(info.pkgName);
                    info.killMode = "true".equals(killMode) ? 1 : 0;

                    appList.add(info);
                }
            }

        }
    }
}
