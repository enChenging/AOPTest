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
//声明注解的作用域  放在什么上面
@Target(ElementType.METHOD)
//声明注解的生命周期  就是它的存在周期  源码期 < 编译期 < 运行期
@Retention(RetentionPolicy.CLASS)
public @interface Async {
}
