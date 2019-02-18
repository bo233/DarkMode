package com.bo233.darkmode;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaRouter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.SimpleCallback;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button test = (Button)findViewById(R.id.button);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "模块未激活", Toast.LENGTH_SHORT).show();
//            }
//        });

        if (!isModuleActive()){
            Toast.makeText(this, "模块未激活", Toast.LENGTH_LONG).show();
        }
        else {
//            Toast.makeText(this, "模块已激活", Toast.LENGTH_LONG).show();
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
