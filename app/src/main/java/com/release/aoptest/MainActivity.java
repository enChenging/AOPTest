package com.release.aoptest;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.release.aop.Logger.Logger;
import com.release.aop.R;
import com.release.aop.Thread.Async;
import com.release.aop.Thread.Main;
import com.release.aop.login.CheckLogin;
import com.release.aop.login.Constants;
import com.release.aop.permission.PermissionCheck;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.isLogin = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Async
    public void readFile(View view) {
        Log.i("cyc", "读取文件" + Thread.currentThread().toString());
        showResult();
    }


    @Async
    public void writeFile(View view) {
        Log.i("cyc", "写入文件" + Thread.currentThread().toString());
        showResult();
    }

    @Main
    private void showResult() {
        Toast.makeText(this, "更新UI" + Thread.currentThread().toString(), Toast.LENGTH_SHORT).show();
    }


    public void printLog(View view){
        sum(6,6);
    }

    @Logger(Log.ERROR)
    private int sum(int i,int j){
        return i + j;
    }


    @CheckLogin(className = LoginActivity.class)
    public void login(View view){
        Log.i("cyc","----------login----------");
    }

    @PermissionCheck(access = true,checkString = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA})
    public void requestPermission(View view){

    }
}