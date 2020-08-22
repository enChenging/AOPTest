package com.release.aop.log;

import android.content.Context;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * @author Mr.release
 * @create 2020/8/18
 * @Describe
 */

@Aspect
public class DebugAspectJ {
    private static final String TAG = DebugAspectJ.class.getSimpleName();

    private static DebugTraceListener debugTraceListener;

    public static void setDebugTraceListener(DebugTraceListener debugTraceListener) {
        DebugAspectJ.debugTraceListener = debugTraceListener;
    }

    @Pointcut("execution(@com.release.aop.log.Analyze * *(..))")
    public void aspectAnalyzeAnnotation() {
    }

    @Pointcut("execution(* android.app.Activity+.onCreate(..))")
    public void activityPointcut() {
    }

    @Pointcut("execution(@com.release.aop.log.DebugLog * *(..))")
    public void aspectDebugLogAnnotation() {
    }

    @Around("aspectAnalyzeAnnotation()")
    public void analyze(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Analyze analyze = methodSignature.getMethod().getAnnotation(Analyze.class);
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        if (debugTraceListener != null) {
            debugTraceListener.onAspectAnalyze(joinPoint, analyze, methodSignature, System.currentTimeMillis() - startTimeMillis);
        }
    }

    @Around("aspectDebugLogAnnotation() || activityPointcut()")
    public void debugLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTimeMillis;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SourceLocation location = joinPoint.getSourceLocation();
        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(),
                location.getFileName(), location.getLine(), duration);
        if (debugTraceListener != null) {
            debugTraceListener.logger(TAG, message);
        } else {
            Log.e(TAG + "_NULL", message);
        }
    }

    //"execution(* android.app.Activity+.onCreate(..))"
    @After("execution(* android.app.Activity+.onResume(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        debugTraceListener.onPageStart((Context) joinPoint.getThis(), "页面可见  " + signature.getDeclaringType().getCanonicalName());
    }

    @After("execution(* android.app.Activity+.onPause(..))")
    public void onActivityMethodDestory(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        debugTraceListener.onPageEnd((Context) joinPoint.getThis(), "页面消失  " + signature.getDeclaringType().getCanonicalName());
    }

}
