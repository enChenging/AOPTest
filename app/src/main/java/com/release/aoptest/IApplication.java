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
        //数据埋点
        DebugAspectJ.setDebugTraceListener(this);
    }

    /**
     * 打印日志
     *
     * @param tag     日志标志
     * @param message 日志信息
     */
    @Override
    public void logger(String tag, String message) {
        Log.e(tag, message);
    }

    /**
     * 打印方法切面分析
     *
     * @param joinPoint       切点
     * @param analyze         分析注解{@link Analyze}
     * @param methodSignature 执行方法
     * @param duration        方法执行时间
     */
    @Override
    public void onAspectAnalyze(ProceedingJoinPoint joinPoint, Analyze analyze, MethodSignature methodSignature, long duration) {
        Log.e("onAspectAnalyze", "analyze.name == " + analyze.name() + " methodSignature == " + methodSignature.getMethod() + " duration == " + duration);
    }

    /**
     * 统计页面停留时长
     *
     * @param context
     * @param className
     */
    @Override
    public void onPageStart(Context context, String className) {
        Log.e("onPageStart", className);
    }

    /**
     * 统计页面停留时长
     *
     * @param context
     * @param className
     */
    @Override
    public void onPageEnd(Context context, String className) {
        Log.e("onPageEnd", className);
    }
}
