package com.bo233.darkmode.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bo233.darkmode.support.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppHelper {
    private static List<String> allPkgNames = new ArrayList<>();
    private static List<String> killPkgNames = new ArrayList<>();
    private static List<String> normalModePkgNames = new ArrayList<>();
    private static List<String> selfModePkgnames = new ArrayList<>();
    private static PackageManager packageManager;
    private static List<PackageInfo> packages = new ArrayList<>();

    public static final int USER_APP_LIST = 0, SYSTEM_APP_LIST = 1;
    public static final int UPDATE_ADD = 2, UPDATE_REMOVE = 3;

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
        return killPkgNames;
    }

    public static void updateKillPkgNames(int flag, String s){
        if(flag == UPDATE_ADD) {
            killPkgNames.add(s);
            Log.d("AppHelper", s+flag);
        }
        else if(flag == UPDATE_REMOVE){
            killPkgNames.remove(s);
            Log.d("AppHelper", s+flag);
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
                    info.appIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
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
                    info.appIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
                    appList.add(info);
                }
            }

        }
    }
}
