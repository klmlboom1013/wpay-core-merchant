package com.wpay.core.merchant.global.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@Order(1)
@WebFilter(urlPatterns = "/*")
@RequiredArgsConstructor
public class LoggingFilter implements Filter {

    private final HttpServletRequest httpServletRequest;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("==================== {} START ====================", httpServletRequest.getRequestURI());
        chain.doFilter(request, response);
        log.info("==================== {} END ====================", httpServletRequest.getRequestURI());
    }
}
