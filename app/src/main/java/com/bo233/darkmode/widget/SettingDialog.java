package com.bo233.darkmode.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import com.bo233.darkmode.R;
import com.bo233.darkmode.util.AppHelper;

public class SettingDialog {
    public static void showSetModeDialog(final Context context, final String pkgName){
        final String radioItems[] = new String[]{context.getString(R.string.normal_mode), context.getString(R.string.self_mode),
                context.getString(R.string.off_mode)};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(context);
        radioDialog.setTitle(R.string.mode_set_title);

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context,radioItems[which], Toast.LENGTH_SHORT).show();

            }
        });

        radioDialog.setPositiveButton(R.string.commit,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        int choice = lw.getCheckedItemPosition();
                        switch (choice){
                            case 0: //normal mode
                                AppHelper.setMode(pkgName, AppHelper.MODE_NORMAL);
                                break;
                            case 1: //self mode
                                AppHelper.setMode(pkgName, AppHelper.MODE_SELF);
                                break;
                            case 2: //off
                                AppHelper.setMode(pkgName, AppHelper.MODE_OFF);
                                break;
                            default:
                                break;
                        }
                        AppHelper.updateAdapterByModeSet();
                        dialog.dismiss();
                    }
                });

        radioDialog.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }


    public static void showSetKillDialog(final Context context, final String pkgName){
        final String radioItems[] = new String[]{context.getString(R.string.kill_mode), context.getString(R.string.dont_kill_mode)};

        AlertDialog.Builder radioDialog = new AlertDialog.Builder(context);
        radioDialog.setTitle(R.string.kill_set_title);

    /*
        设置item 不能用setMessage()
        用setSingleChoiceItems
        items : radioItems[] -> 单选选项数组
        checkItem : 0 -> 默认选中的item
        listener -> 回调接口
    */
        radioDialog.setSingleChoiceItems(radioItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context,radioItems[which], Toast.LENGTH_SHORT).show();
            }
        });

        radioDialog.setPositiveButton(R.string.commit,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        int choice = lw.getCheckedItemPosition();
//                        Log.d("hhh233", choice+"");
                        switch (choice){
                            case 0: //kill
                                AppHelper.updateKillPkgNames(pkgName, AppHelper.UPDATE_ADD);
                                break;
                            case 1: //don't kill
                                AppHelper.updateKillPkgNames(pkgName, AppHelper.UPDATE_REMOVE);
                                break;
                            default:
                                break;
                        }
//                        AppHelper.updateAdapterByModeSet();
                        dialog.dismiss();
                    }
                });

        radioDialog.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        radioDialog.create().show();
    }


    public static void showSelfModeHelpDialog(final Context context){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);

        normalDialog.setTitle(R.string.self_set_help_title);
        normalDialog.setMessage(R.string.self_set_help);
        //设置按钮
        normalDialog.setPositiveButton(R.string.commit
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        normalDialog.create().show();

    }

}
