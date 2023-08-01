package com.wpay.core.merchant.global.aspect;

import com.wpay.common.global.aspect.BaseAspect;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.dto.SelfCrypto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class MerchantWebAdapterAspect extends BaseAspect {

    private final HttpServletRequest request;

    @Before("execution(* com.wpay.core.merchant.*.adapter.in.web.*Controller.*Run(..))")
    @Override
    public void before(JoinPoint joinPoint) {
        log.info("[Before] => {}", joinPoint.getSignature().getName());
        this.baseCommandValidateCryptoSelf(joinPoint, request);
    }

    @After("execution(* com.wpay.core.merchant.*.adapter.in.web.*Controller.*Run(..))")
    @Override
    public void after(JoinPoint joinPoint) {
        log.info("[After] => {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.wpay.core.merchant.*.adapter.in.web.*Controller.*Run(..))", returning = "result")
    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] => {} [result] => {}", joinPoint.getSignature().getName(),
                (Objects.nonNull(result)) ? result.toString() : new Object());
        /* result가 Null 이거나 타입이 ResponseEntity이 아니면 하위 로직 PASS */
        if(Objects.isNull(result) || Boolean.FALSE.equals((result instanceof ResponseEntity))) { return; }
        /* api uri path 세팅 */
        this.resultSetUriPath(result, request);
        /* ResponseEntity body 타입이 BaseResponse 맞는지? */
        final ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        if(Objects.isNull(responseEntity.getBody()) || Boolean.FALSE.equals(responseEntity.getBody() instanceof BaseResponse)) { return; }
        /* ResponseEntity body의 Data 객체가 SelfCrypto를 상속 받았는지?? 상속 받았으면 데이터 암호화 진행. */
        final BaseResponse baseResponse = (BaseResponse) responseEntity.getBody();
        if(baseResponse.getData() instanceof SelfCrypto) {
            log.info("BaseResponse.data 암호화 시작 [ASIS: {}]", baseResponse.getData().toString());
            ((SelfCrypto)baseResponse.getData()).resetFieldDataCrypto();
            log.info("BaseResponse.data 암호화 완료 [TOBE: {}]", baseResponse.getData().toString());
        }
    }

    @AfterThrowing(pointcut = "execution(* com.wpay.core.merchant.*.adapter.in.web.*Controller.*Run(..))", throwing = "e")
    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("[AfterThrowing] => {} [{}] => {}", joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());
    }
}
