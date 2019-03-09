package com.bo233.darkmode.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bo233.darkmode.R;

public class SingleSettingDialog extends AlertDialog{

    private Button cancel, modeSet, killSet, selfMode;
    private TextView tv_text, title;
    private String appName;

    public SingleSettingDialog(Context context, String appName) {
        super(context);
        this.appName = appName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_app_setting_dialog);
        cancel = findViewById(R.id.dialog_btn_cancel);
        modeSet = findViewById(R.id.dialog_btn_mode_setting);
        killSet = findViewById(R.id.dialog_btn_killer_setting);
        selfMode = findViewById(R.id.dialog_btn_self_mode);
        tv_text = findViewById(R.id.dialog_text);
        title = findViewById(R.id.dialog_title);

        title.setText(appName);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSettingDialog.this.dismiss();
            }
        });


    }

}
