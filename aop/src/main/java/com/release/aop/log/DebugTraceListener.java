package com.release.aop.log;

import android.content.Context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Mr.release
 * @create 2020/8/18
 * @Describe
 */

public interface DebugTraceListener {
    /**
     * 打印日志
     *
     * @param tag     日志标志
     * @param message 日志信息
     */
    void logger(String tag, String message);

    /**
     * 打印方法切面分析
     *
     * @param joinPoint       切点
     * @param analyze         分析注解{@link Analyze}
     * @param methodSignature 执行方法
     * @param duration        方法执行时间
     */
    void onAspectAnalyze(ProceedingJoinPoint joinPoint, Analyze analyze, MethodSignature methodSignature, long duration);

    /**
     * 统计页面停留时长
     * @param context
     * @param className
     */
    void onPageStart(Context context,String className);

    /**
     *统计页面停留时长
     * @param context
     * @param className
     */
    void onPageEnd(Context context,String className);
}
