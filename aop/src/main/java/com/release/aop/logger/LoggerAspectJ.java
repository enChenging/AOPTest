package com.release.aop.logger;

import android.text.TextUtils;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

import java.lang.reflect.Method;

/**
 * @author Mr.release
 * @create 2020/8/20
 * @Describe
 */
@Aspect
public class LoggerAspectJ {

    @Around("execution(@com.release.aop.logger.Logger * *(..))")
    public Object doLoogerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * 执行原始方法 获得耗时
         */
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        /**
         * 获得方法签名
         */
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Logger logger = method.getAnnotation(Logger.class);

        /**
         * 根据方法签名 获得参数 type name value
         */
        Class[] parameterTypes = methodSignature.getParameterTypes();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!isFirst)
                builder.append(",");
            isFirst = false;
            builder.append(parameterTypes[i].getSimpleName());
            builder.append(" ");
            builder.append(parameterNames[i]);
            builder.append(" = ");
            builder.append(args[i]);
        }

        //根据方法签名 获得返回类型
        String type = methodSignature.getReturnType().getSimpleName();

        /**
         * 获得源码文件位置信息 拼接日志支持可跳转格式的字符串
         */
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        int lineNumber = sourceLocation.getLine();
        String fileName = sourceLocation.getFileName();
        String fullclassName = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getMethod().getName();

        StringBuffer content = new StringBuffer();
        content.append(":\n");
        content.append("╔═══════════════════════════════════════════════════════════════════════════\n");
        content.append("║当前线程:");
        content.append(Thread.currentThread().getName());
        content.append("\n");
        content.append("╟───────────────────────────────────────────────────────────────────────────\n");
        content.append("║函数:");
        content.append(fullclassName);
        content.append(".");
        content.append(methodName);
        content.append("(");
        content.append(fileName);
        content.append(":");
        content.append(lineNumber);
        content.append(")\n");
        content.append("║参数:");
        content.append(builder);
        content.append("\n");
        content.append("╟───────────────────────────────────────────────────────────────────────────\n");
        content.append("║返回:");
        content.append(type);
        content.append(" ");
        content.append(TextUtils.equals(type.toLowerCase(), "void") ? "" : result);
        content.append("\n");
        content.append("║耗时:");
        content.append(end - start);
        content.append("ms\n");
        content.append("╚═══════════════════════════════════════════════════════════════════════════");

        Log.println(logger.value(), methodSignature.getDeclaringType().getSimpleName(), content.toString());
        return result;
    }

    /**
     * 切入点表达式
     * 被Async 注解 返回void的函数
     */
    private static final String POINTCUT_METHOD = "execution(@com.release.aoptest.Logger.Logger * *(..))";

    /**
     * 切入点
     * 所有通过Async 注解的方法
     */
    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithLogger() {

    }
}
