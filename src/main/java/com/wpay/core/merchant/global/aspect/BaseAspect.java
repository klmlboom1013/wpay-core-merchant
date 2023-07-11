package com.wpay.core.merchant.global.aspect;

import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
public abstract class BaseAspect {

    protected void logWriteExceptionStackTrace(Throwable e, Object... args){
        if(Objects.nonNull(args) && args.length > 0)
            log.debug(">> Call Aspect : [{}]", args[0].getClass().getName());

        final StringBuilder sb = new StringBuilder(e.getClass().getName())
                .append(": ").append(e.getMessage()).append("\n");
        for(StackTraceElement se : e.getStackTrace()) {
            sb.append("    ")
                    .append(se.getClassName()).append("(")
                    .append(se.getMethodName()).append(":")
                    .append(se.getLineNumber()).append(")").append("\n");
        }
        log.error(sb.toString());
    }
}
