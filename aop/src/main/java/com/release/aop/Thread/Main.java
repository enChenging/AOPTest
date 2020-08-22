package com.release.aop.Thread;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mr.release
 * @create 2020/8/18
 * @Describe
 */
//这个注解只在method上用
@Target(ElementType.METHOD)
//保留到 作用到class上
@Retention(RetentionPolicy.CLASS)
public @interface Main {
}
