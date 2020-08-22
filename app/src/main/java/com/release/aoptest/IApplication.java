package com.release.aoptest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.release.aop.log.Analyze;
import com.release.aop.log.DebugAspectJ;
import com.release.aop.log.DebugTraceListener;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Mr.release
 * @create 2020/8/22
 * @Describe
 */

public class IApplication extends Application implements DebugTraceListener {


    @Override
    public void onCreate() {
        super.onCreate();
        DebugAspectJ.setDebugTraceListener(this);
    }

    @Override
    public void logger(String tag, String message) {
        Log.e(tag,message);
    }

    @Override
    public void onAspectAnalyze(ProceedingJoinPoint joinPoint, Analyze analyze, MethodSignature methodSignature, long duration) {
        Log.e("onAspectAnalyze", analyze.name());
    }

    @Override
    public void onPageStart(Context context, String className) {
        Log.e("onPageStart",className);
    }

    @Override
    public void onPageEnd(Context context, String className) {
        Log.e("onPageEnd",className);
    }
}
