package com.release.aop.bindview;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过apt实现-绑定视图
 * @author yancheng
 * @since 2022/1/28
 */
public class Binding {
    public static void bind(Activity activity) {
        try {
            //获取类 com.release.aoptest.MainActivityBinding
            Class bindingClass = Class.forName(activity.getClass().getCanonicalName() + "Binding");
            //获取构造
            Constructor constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            //创建对象
            constructor.newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
