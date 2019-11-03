package com.bo233.darkmode.support;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bo233.darkmode.R;

import java.util.HashSet;
import java.util.List;

class ViewHolder {
    public TextView appName, pkgName, modeState, killState;
    public ImageView appIcon;
    public CheckBox checkBox;
}

public class AppAdapter extends BaseAdapter {
    List<AppInfo> appList;
    LayoutInflater infater;
    public HashSet<Long> selectedItems;

    public static boolean multiChoiceMode = false;

    public AppAdapter(Context context, List<AppInfo> apps)
    {
        this.infater = LayoutInflater.from(context);
        this.appList = apps;
        this.selectedItems = new HashSet<Long>();
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return appList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = infater.inflate(R.layout.app_item, null);
            holder.appIcon =  convertView.findViewById(R.id.app_icon);
            holder.appName =  convertView.findViewById(R.id.app_name);
            holder.pkgName =  convertView.findViewById(R.id.pkg_name);
            holder.modeState = convertView.findViewById(R.id.set_mode);
            holder.killState = convertView.findViewById(R.id.kill_mode);
            holder.checkBox = convertView.findViewById(R.id.multi_check_box);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppInfo appInfo = (AppInfo) getItem(position);

        holder.appIcon.setImageDrawable(appInfo.appIcon);
        holder.appName.setText(appInfo.appName);
        holder.pkgName.setText(appInfo.pkgName);
        holder.modeState.setText("当前模式："+(appInfo.darkMode==0?"关闭":(appInfo.darkMode==1?"普通模式":"自带夜间模式")));
        holder.killState.setText("自动关闭后台："+(appInfo.killMode==0?"否":"是"));

        if(selectedItems.contains((long)position)){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }
        if(multiChoiceMode){
            holder.checkBox.setVisibility(View.VISIBLE);

//            holder.check_box_wraper.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.INVISIBLE);
//            holder.check_box_wraper.setVisibility(View.GONE);
        }

        return convertView;
    }

}
