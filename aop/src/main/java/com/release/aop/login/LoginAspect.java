package com.release.aop.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Mr.release
 * @create 2020/8/22
 * @Describe
 */

@Aspect
public class LoginAspect {

    @Around("execution(@com.release.aop.login.CheckLogin * *(..))")
    public Object doCheckLoginMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);
        Log.w("LoginAspect", "beforeDebugTraceMethod: " + method.toString());
        //获取上下文的方法

        Context context = null;
        try {
            if (joinPoint.getThis() instanceof Fragment)
                context = ((Fragment) joinPoint.getThis()).getActivity();
            else
                context = (Context) joinPoint.getThis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (context != null) {

            Activity activity = null;
            if (joinPoint.getThis() instanceof Activity) {
                activity = (Activity) joinPoint.getThis();
            } else if (joinPoint.getThis() instanceof Fragment) {
                activity = ((Fragment) joinPoint.getThis()).getActivity();
            }

            if (Constants.isLogin) {
                return joinPoint.
                        proceed();
            } else {
                activity.finish();
                activity.startActivity(new Intent(activity,checkLogin.className()));
                return null;
            }
        }
        return joinPoint.proceed();
    }
}