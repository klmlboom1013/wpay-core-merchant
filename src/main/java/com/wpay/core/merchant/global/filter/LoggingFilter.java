package com.wpay.core.merchant.global.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
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
class LoggingFilter implements Filter {

    private final HttpServletRequest httpServletRequest;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("==================== {} START ====================", httpServletRequest.getRequestURI());
        this.loggingRequestHeaders();
        this.loggingRequestParams();
        chain.doFilter(request, response);
        log.info("==================== {} END ====================", httpServletRequest.getRequestURI());
    }

    private void loggingRequestHeaders() {
        StringBuilder sb = new StringBuilder();
        this.httpServletRequest.getHeaderNames().asIterator().forEachRemaining((key) ->
                sb.append("    ").append(key).append(" : ").append(this.httpServletRequest.getHeader(key)).append("\n"));
        if(Strings.isNotBlank(sb.toString()))
            log.info("[ Request Headers ]\n{}",sb.toString());
    }

    private void loggingRequestParams() {
        StringBuilder sb = new StringBuilder();
        this.httpServletRequest.getParameterNames().asIterator().forEachRemaining((key) ->
                sb.append("    ").append(key).append(" : ").append(this.httpServletRequest.getHeader(key)).append("\n"));
        if(Strings.isNotBlank(sb.toString()))
            log.info("[ Request Parameters ]\n{}",sb.toString());
    }
}
