package com.wpay.core.merchant.global.aspect;

import com.wpay.common.global.aspect.BaseAspect;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.exception.*;
import com.wpay.common.global.exception.webclient.CustomWebClientRequestException;
import com.wpay.common.global.exception.webclient.CustomWebClientResponseException;
import com.wpay.common.global.exception.webclient.CustomWebClientTimeoutException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
@Aspect
@Component
public class MerchantExternalAdapterAspect extends BaseAspect {
    @Before("execution(* com.wpay.core.merchant.*.adapter.out.external.*External.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.debug("[Before] => {}", joinPoint.getSignature().getName());
        this.validateCryptoSelf(joinPoint, true);
    }

    @After("execution(* com.wpay.core.merchant.*.adapter.out.external.*External.*Run(..))")
    @Override

    public void after(JoinPoint joinPoint) {
        log.debug("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.*.adapter.out.external.*External.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.debug("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(), result);
        if(result instanceof SelfValidating) ((SelfValidating<?>) result).validateCryptoSelf();
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.*.adapter.out.external.*External.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
        final SelfValidating<?>[] activity = { null };
        Arrays.stream(joinPoint.getArgs()).forEach(o -> {
            if((o instanceof SelfValidating<?>) && Objects.isNull(activity[0])) { activity[0] = (SelfValidating<?>)o; }
        });

        String wtid, mid;
        try {
            Class<?> clazz = activity.getClass();
            Method getWtid = clazz.getMethod("getWtid");
            Method getMid = clazz.getMethod("getMid");
            wtid = (String) getWtid.invoke(activity);
            mid = (String) getMid.invoke(activity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new CustomException(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).e(ex).build());
        }

        if (e instanceof CustomWebClientRequestException) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_501).e(e).wtid(wtid).mid(mid)
                    .data(((CustomWebClientRequestException) e).getMapper()).build());
        } else if (e instanceof CustomWebClientResponseException) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_503).e(e).wtid(wtid).mid(mid)
                    .data(((CustomWebClientResponseException) e).getMapper()).build());
        } else if (e instanceof CustomWebClientTimeoutException) {
            final CustomWebClientTimeoutException ex = (CustomWebClientTimeoutException) e;
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_504).e(ex).wtid(wtid).mid(mid)
                    .data(((CustomWebClientTimeoutException) e).getMapper()).build());
        } else if(Boolean.FALSE.equals(e instanceof CustomException)) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_500).e(e).wtid(wtid).mid(mid).build());
        }
    }
}
