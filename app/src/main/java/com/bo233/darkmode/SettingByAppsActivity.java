package com.bo233.darkmode;

import android.support.v4.app.Fragment;
import com.bo233.darkmode.support.SingleFragmentActivity;
import com.bo233.darkmode.util.AppListFragment;

public class SettingByAppsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AppListFragment();
    }
}
