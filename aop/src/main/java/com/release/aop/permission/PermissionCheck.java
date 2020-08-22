package com.release.aop.permission;

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
public @interface PermissionCheck {
    String[] checkString();//权限数组
    boolean access();//如果无权限是否允许后续任务
}
