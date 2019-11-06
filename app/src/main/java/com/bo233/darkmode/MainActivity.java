package com.bo233.darkmode;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bo233.darkmode.util.AppHelper;
import com.bo233.darkmode.util.MyProp;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.SimpleCallback;

import static com.bo233.darkmode.support.RequireRootPermission.upgradeRootPermission;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyProp.init();
        setContentView(R.layout.activity_main);

        if (!isModuleActive()){
            Toast.makeText(this, "模块未激活", Toast.LENGTH_LONG).show();
        }
        else {
            TextView textView = (TextView) findViewById(R.id.not_active_hint);
            textView.setVisibility(View.GONE);

            request();
        }

    }

    private void request(){
        PermissionManager.Builder()
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE)
                .askAgain(true)
                .askAgainCallback(new AskAgainCallback() {
                    @Override
                    public void showRequestPermission(AskAgainCallback.UserResponse response) {

                    }
                })
                .callback(new SimpleCallback() {
                    @Override
                    public void result(boolean allPermissionsGranted) {
                        if(allPermissionsGranted){
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frag_container,new MainPreferences());
                            fragmentTransaction.commit();
                            if(!upgradeRootPermission(getPackageCodePath()))
                                Toast.makeText(MainActivity.this, "未允许root权限，部分功能可能失效", Toast.LENGTH_LONG).show();
                            AppHelper.setPackageManager(MainActivity.this);
                        }
                    }
                })
                .ask(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);
    }

    private boolean isModuleActive(){
        return false;
    }

}
