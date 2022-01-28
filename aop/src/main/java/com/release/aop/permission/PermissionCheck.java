package com.release.aop.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 *
 * @author Mr.release
 * @create 2020/8/18
 * @Describe
 */

/**
 * SOURCE,  编译时扔掉,进入不到字节码
 * CLASS,   保留到字节码时,但不能通过反射看到
 * RUNTIME; 保留到最后
 */
@Retention(RetentionPolicy.RUNTIME)

/**
 *  缩小限制范围
 *   TYPE,
 *   FIELD, 成员变量
 *   METHOD, 方法
 *   PARAMETER,
 *   CONSTRUCTOR,
 *   OCAL_VARIABLE,
 *   ANNOTATION_TYPE,
 *   PACKAGE,
 *   TYPE_PARAMETER,
 *   TYPE_USE;
 */
@Target(ElementType.METHOD)
public @interface PermissionCheck {

    String[] checkString();//权限数组

    boolean access() ;//如果无权限是否允许后续任务
}


