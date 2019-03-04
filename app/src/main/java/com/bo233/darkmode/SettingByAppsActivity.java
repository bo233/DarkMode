package com.bo233.darkmode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class SettingByAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);

        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

//        tabHost.addTab((tabHost.newTabSpec("user_apps_tab").setIndicator("233").);
        tabHost.addTab(tabHost.newTabSpec("user_apps_tab").setIndicator("用户应用").setContent(R.id.user_apps_fragment));
        tabHost.addTab(tabHost.newTabSpec("system_apps_tab").setIndicator("系统应用").setContent(R.id.system_apps_fragment));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 5, 1, "添加1");
        menu.add(0, 6, 2, "删除1");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 5:
//                Toast.makeText(this, "添加1", Toast.LENGTH_SHORT).show();
                break;
            case 6:
//                Toast.makeText(this, "删除1", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}