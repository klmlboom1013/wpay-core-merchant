package com.wpay.core.merchant.adapter.out.aspect;

import com.wpay.core.merchant.global.aspect.BaseAspect;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Objects;

@Log4j2
@Aspect
@Component
public class PersistenceAspect extends BaseAspect {
    @Before("execution(* com.wpay.core.merchant.adapter.out.persistence.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());

        /* Entity DB Encryption */
        final Object object = joinPoint.getArgs()[1];
        if(Objects.nonNull(object.getClass().getAnnotation(Entity.class))){
            log.info("Entity field data DB encryption >>>>>");
        }

    }

    @After("execution(* com.wpay.core.merchant.adapter.out.persistence.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.adapter.out.persistence.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);

        /* "result" Entity DB Decryption */
        if(Objects.nonNull(result) && Objects.nonNull(result.getClass().getAnnotation(Entity.class))) {
            log.info("Entity field data DB Decryption >>>>>");
        }
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.adapter.out.persistence.*.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
