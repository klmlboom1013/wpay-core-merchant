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
//        this.loggingRequestBody();
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

    /*
     * HttpServletRequest InputStream 에서 데이터를 꺼내면 buffer 가 비워 지기 때문에
     * Controller DTO 에서는 데이터를 받을 수 없다.
     * 인입 데이터 확인 디버그 용도로만 사용 하고 주석 차리 함.
     */
//    private void loggingRequestBody() {
//        InputStream inputStream;
//        try {
//            inputStream = this.httpServletRequest.getInputStream();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if(Objects.isNull(inputStream)) return;
//
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = br.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = (JSONObject) jsonParser.parse(stringBuilder.toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(Objects.nonNull(jsonObject))
//            log.info("[ Request Body ]\n{}", jsonObject.toJSONString());
//    }
}
