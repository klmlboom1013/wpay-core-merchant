package com.wpay.core.merchant.global.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(value = "merchantSecurityBeans")
public class SecurityBeans {

    /**
     * Spring Security 설정
     */
    @Bean(value = "merchantSecurityFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 접근 권한 제어 예외 URI 등록
        http.antMatcher("/merchant/*");
        return http.build();
    }
}
