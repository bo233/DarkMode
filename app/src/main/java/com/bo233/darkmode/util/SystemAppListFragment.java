package com.bo233.darkmode.util;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bo233.darkmode.support.AppAdapter;
import com.bo233.darkmode.support.AppInfo;

import java.util.ArrayList;

import static com.bo233.darkmode.util.AppHelper.getAppList;

public class SystemAppListFragment extends ListFragment {
    private ArrayList<AppInfo> appList = new ArrayList<>();
//    private ArrayList<AppInfo> systemAppList = new ArrayList<>();
    private int typeOfApps = AppHelper.SYSTEM_APP_LIST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppList(appList, typeOfApps);
        AppAdapter adapter = new AppAdapter(this.getActivity(), appList);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // 启动所选应用
//        startActivity(appList.get(position).appIntent);
    }
//        PackageManager pm = this.getActivity().getPackageManager();
//        // Return a List of all packages th

    /**
     * 获取应用信息列表
     */
//    private void getAppList() {at are installed on the device.
//        List<PackageInfo> packages = pm.getInstalledPackages(0);
//        for (PackageInfo packageInfo : packages) {
//            // 判断系统/非系统应用
////            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)	// 非系统应用
////            {
//                AppInfo info = new AppInfo();
//                info.appName = packageInfo.applicationInfo.loadLabel(pm)
//                        .toString();
//                info.pkgName = packageInfo.packageName;
//                info.appIcon = packageInfo.applicationInfo.loadIcon(pm);
//                // 获取该应用安装包的Intent，用于启动该应用
//                info.appIntent = pm.getLaunchIntentForPackage(packageInfo.packageName);
//                appList.add(info);
////            } else {
//                // 系统应用　　　　　　　　
////            }
//
//        }
//    }
}
