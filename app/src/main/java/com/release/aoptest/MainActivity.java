package com.release.aoptest;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.release.aop.bindview.Binding;
import com.release.aop.logger.Logger;
import com.release.aop.login.CheckLogin;
import com.release.aop.login.Constants;
import com.release.aop.permission.PermissionCheck;
import com.release.aop.thread.Async;
import com.release.aop.thread.Main;
import com.release.lib_annotations.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vBindViewBtn)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.isLogin = false;
        Binding.bind(this);
    }

    /**
     * BindView
     *
     * @param view
     */
    public void bindView(View view) {
        Toast.makeText(this, "bindView", Toast.LENGTH_SHORT).show();
        mButton.setBackgroundColor(0xFF03DAC5);
    }

    /**
     * 读文件
     *
     * @param view
     */
    @Async
    public void readFile(View view) {
        Log.i("cyc", "读取文件" + Thread.currentThread().toString());
        showResult();
    }


    /**
     * 写文件
     *
     * @param view
     */
    @Async
    public void writeFile(View view) {
        Log.i("cyc", "写入文件" + Thread.currentThread().toString());
        showResult();
    }

    /**
     * 打印Log
     *
     * @param view
     */
    public void printLog(View view) {
        Toast.makeText(this, "打印Log", Toast.LENGTH_SHORT).show();
        sum(6, 6);
    }

    /**
     * 登录
     *
     * @param view
     */
    @CheckLogin(className = LoginActivity.class)
    public void login(View view) {
        Log.i("cyc", "----------login----------");
    }

    /**
     * 请求权限
     *
     * @param view
     */
    @PermissionCheck(access = true, checkString = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA})
    public void requestPermission(View view) {
        Toast.makeText(this, "请求权限", Toast.LENGTH_SHORT).show();
    }

    @Main
    private void showResult() {
        Toast.makeText(this, "更新UI" + Thread.currentThread().toString(), Toast.LENGTH_SHORT).show();
    }

    @Logger(Log.ERROR)
    private int sum(int i, int j) {
        return i + j;
    }

}