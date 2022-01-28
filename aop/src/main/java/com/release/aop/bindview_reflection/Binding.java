package com.release.aop.bindview_reflection;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * 通过反射实现-绑定视图
 * @author yancheng
 * @since 2022/1/28
 */
//public class Binding {
//    public static  void  bind(Activity activity){
//        for (Field field : activity.getClass().getDeclaredFields()) {
//            BindView bindView = field.getAnnotation(BindView.class);
//            if (bindView!=null){
//                field.setAccessible(true);
//                try {
//                    field.set(activity,activity.findViewById(bindView.value()));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
