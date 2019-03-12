package com.bo233.darkmode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.bo233.darkmode.util.MyFile;
import com.bo233.darkmode.widget.SettingDialog;

public class SettingByAppsActivity extends AppCompatActivity {

    private LinearLayout toolBar, singleToolBar;
    private Button modeSet, killSet, cancel;
    private CheckBox multiChoiceBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);
        MyFile.init(this);

        toolBar = findViewById(R.id.tool_bar);
        modeSet = findViewById(R.id.mode_btn);
        killSet = findViewById(R.id.killer_btn);
        cancel = findViewById(R.id.cancel_btn);
//        multiChoiceBox = findViewById(R.id.multi_choice_box);

        modeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SettingDialog.showSetModeDialog(SettingByAppsActivity.this);
            }
        });

        killSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SettingDialog.showSetKillDialog(SettingByAppsActivity.this);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBar.setVisibility(View.GONE);
//                if(multiChoiceBox==null)
//                    multiChoiceBox = findViewById(R.id.multi_choice_box);
//                multiChoiceBox.setVisibility(View.GONE);
            }
        });

        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

//        tabHost.addTab((tabHost.newTabSpec("user_apps_tab").setIndicator("233").);
        tabHost.addTab(tabHost.newTabSpec("user_apps_tab").setIndicator(getString(R.string.user_apps_title)).setContent(R.id.user_apps_fragment));
        tabHost.addTab(tabHost.newTabSpec("system_apps_tab").setIndicator(getString(R.string.system_aps_title)).setContent(R.id.system_apps_fragment));

        //标签切换事件处理，setOnTabChangedListener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            // tabId是newTabSpec参数设置的tab页名，并不是layout里面的标识符id
            public void onTabChanged(String tabId) {
                if (tabId.equals("user_apps_tab")) {   //第一个标签

                }
                if (tabId.equals("system_apps_tab")) {   //第二个标签

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyFile.saveAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 5, 1, getString(R.string.bulk_process));
        menu.add(0, 6, 2, getString(R.string.help));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 5: //批量操作
                toolBar.setVisibility(View.VISIBLE);
//                if(multiChoiceBox==null)
//                    multiChoiceBox = findViewById(R.id.multi_choice_box);
//                multiChoiceBox.setVisibility(View.VISIBLE);
                break;
            case 6: //帮助
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}