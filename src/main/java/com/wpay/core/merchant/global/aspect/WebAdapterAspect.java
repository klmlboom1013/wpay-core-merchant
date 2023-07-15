package com.wpay.core.merchant.global.aspect;

import com.wpay.core.merchant.global.common.Functions;
import com.wpay.core.merchant.global.dto.BaseValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class WebAdapterAspect implements BaseAspect {

    private final HttpServletRequest request;

    @Before("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());

        /* Request validation check */
        for(Object o : joinPoint.getArgs()) {
            /* BaseValidation 을 상속 받지 않았으면 continue */
            if(Boolean.FALSE.equals(o instanceof BaseValidation)) continue;
            /* set serverName */
            try {
                Class<?> clazz = o.getClass();
                final Method method = clazz.getDeclaredMethod("setServerName", String.class);
                final String param = Functions.getIdcDvdCd.apply(request.getServerName());
                method.invoke(o, param);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                log.warn("WebAdapter commandDTO method invoke Error : {} - {}", e.getClass().getSimpleName(), e.getMessage());
            } finally {
                log.info(o.toString());
            }
            /* validation check */
            ((BaseValidation)o).validateSelf();
            log.info("{} Validation check 정상.", o.getClass().getSimpleName());
        }
    }

    @After("execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.adapter.in.web.*.*(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.debug("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
        log.error(this.logWriteExceptionStackTrace(e));
    }
}
