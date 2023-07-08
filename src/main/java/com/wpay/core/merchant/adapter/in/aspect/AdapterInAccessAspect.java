package com.wpay.core.merchant.adapter.in.aspect;

import com.wpay.core.merchant.global.aspect.BaseAspect;
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
public class AdapterInAccessAspect extends BaseAspect {

    @Before("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());

        /* Request validation check */
        final BaseValidation baseValidation = (BaseValidation)joinPoint.getArgs()[1];
        baseValidation.validateSelf();
        log.info("Validation check success");
    }

    @After("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
