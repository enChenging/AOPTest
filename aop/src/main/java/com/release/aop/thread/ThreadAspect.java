package com.release.aop.thread;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Mr.release
 * @create 2020/8/20
 * @Describe
 */
@Aspect
public class ThreadAspect {

    /**
     * 由这个方法替换掉被asyn注解的方法
     * void:支持处理的方法 返回值需要为void
     * @param joinPoint
     */
    @Around("execution(@com.release.aop.Thread.Async void *(..))")
    public void doAsync(final ProceedingJoinPoint joinPoint){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    //执行原始的方法
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Around("execution(@com.release.aop.Thread.Main void *(..))")
    public void doMain(final ProceedingJoinPoint joinPoint){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    //执行原始的方法
                    joinPoint.proceed();
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
