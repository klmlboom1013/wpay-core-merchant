package com.wpay.core.merchant.global.aspect;

import com.wpay.core.merchant.global.dto.BaseValidation;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class WebAdapterAspect extends BaseAspect {

    @Before("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());
        /* Request validation check */
        int i=0;
        for(Object o : joinPoint.getArgs()){
            log.debug(">> JoinPoint Args[{}] Object Name [{}] [{}]", i++, o.getClass().getName(), (o instanceof BaseValidation));
            if(o instanceof BaseValidation){
                ((BaseValidation)o).validateSelf();
                log.info("{} Validation check success", o.getClass().getName());
                break;
            }
        }
    }

    @After("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.debug("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
        super.logWriteExceptionStackTrace(e, this);
    }
}
