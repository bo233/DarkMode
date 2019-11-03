package com.bo233.darkmode.widget;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bo233.darkmode.support.AppAdapter;
import com.bo233.darkmode.support.AppInfo;
import com.bo233.darkmode.util.AppHelper;
import com.bo233.darkmode.util.MyProperties;

import java.util.ArrayList;

import static com.bo233.darkmode.util.AppHelper.getAppList;

public class SystemAppListFragment extends ListFragment {
    private ArrayList<AppInfo> appList = new ArrayList<>();
//    private ArrayList<AppInfo> systemAppList = new ArrayList<>();
    private int typeOfApps = AppHelper.SYSTEM_APP_LIST;
    AppAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppList(appList, typeOfApps);
//        AppAdapter adapter = new AppAdapter(this.getActivity(), appList);
        adapter = new AppAdapter(this.getActivity(), appList);
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
        if(AppAdapter.multiChoiceMode){
//            appList.get(position)
            if(adapter.selectedItems.contains((long)position))
                adapter.selectedItems.remove((long)position);
            else
                adapter.selectedItems.add((long)position);
        }
        else {
            SingleSettingDialog dialog = new SingleSettingDialog(getActivity(), appList.get(position).appName, appList.get(position).pkgName);
            dialog.show();
        }
        //更新appList中的mode
        AppHelper.setUpdateAdapterParam(adapter, appList, position);
//        String mode = MyProperties.ModeProperties.getProperty(appList.get(position).pkgName);
//        if(mode == null || mode.equals(MyProperties.MODE_NORMAL))
//            appList.get(position).darkMode = 1;
//        else if(mode.equals(MyProperties.MODE_OFF))
//            appList.get(position).darkMode = 0;
//        else if(mode.equals(MyProperties.MODE_OFF))
//            appList.get(position).darkMode = 2;
        adapter.notifyDataSetChanged();
    }


}
