package com.wpay.core.merchant.global.aspect;

import net.bytebuddy.asm.Advice;
import org.aspectj.lang.JoinPoint;

public interface BaseAspect {

    void before(JoinPoint joinPoint);

    void after(JoinPoint joinPoint);

    void afterReturning(JoinPoint joinPoint, Object result);

    void afterThrowing(JoinPoint joinPoint, Throwable e);


    default String logWriteExceptionStackTrace(Throwable e){
        final StringBuilder sb = new StringBuilder(e.getClass().getName())
                .append(": ").append(e.getMessage()).append("\n");
        for(StackTraceElement se : e.getStackTrace()) {
            sb.append("    ")
                    .append(se.getClassName()).append("(")
                    .append(se.getMethodName()).append(":")
                    .append(se.getLineNumber()).append(")").append("\n");
        }
        return sb.toString();
    }
}
