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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        //베스트는 이 전에 body 정보와 header정보를 찍어주야한다
        //그러기 위해서는 Contentclasschaing외에 캐싱 해줄수 있는 클래스를 하나 만들어야된다.
        filterChain.doFilter(request,response);

        // request정보
        var headernames = request.getHeaderNames();
        var headervalues = new StringBuilder();

        headernames.asIterator().forEachRemaining(headerKey->{
            var headervalue = request.getHeader(headerKey);
            headervalues.append("[").append(headerKey).append(" :").append(headervalue).append(",").append("] ");

        });
        var requestBody = new String(request.getContentAsByteArray());
        var url = request.getRequestURL();
        var method = request.getMethod();
        log.info(">>>>url: {}, method: {},  header : {} , body : {}",url, method, headervalues, requestBody);

        //reponse정보
        var responseheaderValuss = new StringBuilder();

        response.getHeaderNames().forEach(headerKey->{
            var headervalue = response.getHeader(headerKey);
            responseheaderValuss.append(headerKey).append(" :").append(headervalue).append(",");

        });
        var responseBody = new String(response.getContentAsByteArray());
        log.info("<<<<<< url :{} , method : {} , header: {} , body: {}",url , method,responseheaderValuss,responseBody);

        // 해당 코드 없으면 responser를 사용했기때문에 안내려감
        response.copyBodyToResponse();;
    }


}
