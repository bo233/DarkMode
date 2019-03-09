package com.bo233.darkmode.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class SettingDialog {
    public static void showSetModeDialog(final Context context){
        final String radioItems[] = new String[]{"兼容模式","自带夜间模式","关闭"};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(context);
        radioDialog.setTitle("设置模式");

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,radioItems[which], Toast.LENGTH_SHORT).show();
            }
        });

        radioDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }

    public static void showSetKillDialog(final Context context){
        final String radioItems[] = new String[]{"切换夜间模式时强行关闭后台","切换夜间模式时不关闭后台"};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(context);
        radioDialog.setTitle("强杀后台");

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,radioItems[which], Toast.LENGTH_SHORT).show();
            }
        });

        radioDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }

}
