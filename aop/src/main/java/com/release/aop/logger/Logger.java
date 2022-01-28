package com.release.aop.logger;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Mr.release
 * @create 2020/8/18
 * @Describe
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
    int value() default Log.INFO;
}
