package org.ItBridge.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Slf4j
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // 요청 URL 필터링 (정적 리소스 제외)
        String requestUri = request.getRequestURI();
        if (!requestUri.matches(".*\\.(css|js|png|jpg|jpeg|gif|ico|woff|woff2|ttf|eot)$")) {
            // Request 정보
            var headerNames = request.getHeaderNames();
            var headerValues = new StringBuilder();
            headerNames.asIterator().forEachRemaining(headerKey -> {
                var headerValue = request.getHeader(headerKey);
                headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("], ");
            });

            var requestBody = new String(request.getContentAsByteArray());
            var url = request.getRequestURL();
            var method = request.getMethod();
            log.info(">>>>url: {}, method: {}, header: {}, body: {}", url, method, headerValues, requestBody);
        }

        // 다음 필터 체인 실행
        filterChain.doFilter(request, response);

        if (!requestUri.matches(".*\\.(css|js|png|jpg|jpeg|gif|ico|woff|woff2|ttf|eot)$")) {
            // Response 정보
            var responseHeaderValues = new StringBuilder();
            response.getHeaderNames().forEach(headerKey -> {
                var headerValue = response.getHeader(headerKey);
                responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("], ");
            });

            var responseBody = new String(response.getContentAsByteArray());
            log.info("<<<<<< url: {}, method: {}, header: {}, body: {}", request.getRequestURL(), request.getMethod(), responseHeaderValues, responseBody);
        }

        // 응답 본문 복사
        response.copyBodyToResponse();
    }



}
