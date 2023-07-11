package com.wpay.core.merchant.global.aspect;

import com.wpay.core.merchant.global.dto.BaseValidation;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class UseCaseAspect {
//com.wpay.core.merchant.application.service.*.execute(..)
    @Before("execution(* com.wpay.core.merchant.application.service.*.execute(..))")
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());


        /* Request validation check */
        int i=0;
        for(Object o : joinPoint.getArgs()){
            log.info(">> JoinPoint Args[{}] Object Name [{}] [{}]", i++, o.getClass().getName(), (o instanceof BaseValidation));
            if(o instanceof BaseValidation){
                final BaseValidation baseValidation = (BaseValidation)joinPoint.getArgs()[1];
                baseValidation.validateSelf();
                log.info("Validation check success");
                break;
            }
        }
//
    }

    @After("execution(* com.wpay.core.merchant.application.service.*.execute(..))")
    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.application.service.*.execute(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.application.service.*.execute(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
