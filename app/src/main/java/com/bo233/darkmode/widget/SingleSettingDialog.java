package com.bo233.darkmode.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bo233.darkmode.R;
import com.bo233.darkmode.util.AppHelper;
import com.bo233.darkmode.util.AppKiller;
import com.bo233.darkmode.util.MyProperties;

public class SingleSettingDialog extends AlertDialog{

    private Button cancel, modeSet, killSet, selfMode;
    private TextView title;
    private String appName;
    private Context context;
    private String pkgName;

    public SingleSettingDialog(Context context, String appName, String pkgName) {
        super(context);
        this.appName = appName;
        this.pkgName = pkgName;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_app_setting_dialog);
        cancel = findViewById(R.id.dialog_btn_cancel);
        modeSet = findViewById(R.id.dialog_btn_mode_setting);
        killSet = findViewById(R.id.dialog_btn_killer_setting);
        selfMode = findViewById(R.id.dialog_btn_self_mode);
        title = findViewById(R.id.dialog_title);

        title.setText(appName);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSettingDialog.this.dismiss();
            }
        });

        modeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog.showSetModeDialog(context, pkgName);
                SingleSettingDialog.this.dismiss();
            }
        });

        killSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SettingDialog.showSetKillDialog(context, pkgName);
                SingleSettingDialog.this.dismiss();
            }
        });

        selfMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog.showSelfModeHelpDialog(context);
                new SelfSettingNotification(context);
                SingleSettingDialog.this.dismiss();
                MyProperties.setProperty(MyProperties.SELF_SETTING_PKG_NAME, pkgName);
//                MyProperties.setProperty(MyProperties.SELF_SETTING, "true");
                AppKiller.kill(pkgName);
            }
        });


    }

}
