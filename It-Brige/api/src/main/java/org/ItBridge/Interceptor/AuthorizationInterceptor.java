//package org.ItBridge.Interceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.ItBridge.Common.error.ErrorCode;
//import org.ItBridge.Common.error.TokenErrorcode;
//import org.ItBridge.Common.exception.ApiException;
//import org.ItBridge.domain.token.Business.TokenBusiness;
//import org.springframework.http.HttpMethod;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class AuthorizationInterceptor implements HandlerInterceptor {
//    private final TokenBusiness tokenBusiness;
//    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
//            "/proxy/*",
//            "/open-api/lecture/*",
//            "/public/assets",
//            "/health-check"
//    );
//    @Override
//    public boolean preHandle(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler) throws Exception {
//        log.info("Authorization interceptor url : {} ", request.getRequestURL());
//
//        //WEB, chorm의 경우  get,post option = pass
//        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
//            return true;
//        }
//        //js, html png resource를 요청하는 경우 pass
//        if (handler instanceof ResourceHttpRequestHandler) {
//            return true;
//        }
//        String uri = request.getRequestURI();
//
//        // 경로 리스트에 포함되어 있으면 인증 우회
//        if (EXCLUDED_PATHS.stream().anyMatch(uri::startsWith)) {
//            return true; // 인증 우회
//        }
//
////        //header 검증
//        var accessToken = request.getHeader("authorization-token");
////
//        if (accessToken == null) {
//            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
//        }
//        var userId = tokenBusiness.validationToken(accessToken);
//        if (userId != null) {
//            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
//            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
//
//            return true;
//        }
////refreshtoken
//        var refreshToken = request.getHeader("refresh-token");
//        if (refreshToken != null) {
//            try {
//                var userid = tokenBusiness.validationToken(refreshToken);
//                if (userid != null) {
//                    var newAccessTokenResponse = tokenBusiness.reissueToken(userid);
//                    response.setHeader("new-authorization-token", newAccessTokenResponse.getAccessToken());
//                    setUserIdInContext(userId);
//                    return true;
//                }
//            } catch (ApiException e) {
//                log.error("Refresh token validation failed: {}", e.getMessage());
//                throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
//            }
//        }
////
//        throw new ApiException(ErrorCode.BAD_REQUST, "인증 실패");
//////        return false; //인증 실패
////        return true;
////        //TODO 만료된 토큰 발행시 토큰 재발행(login중에만 )
////        //TODO}
//    }
//    private void setUserIdInContext(Long userId) {
//        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
//        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
//    }}
////}
//----------위에는 됫던것 아래는 시도

package org.ItBridge.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.error.TokenErrorcode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.token.Business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    // 인증 우회 경로
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/proxy/image",
            "/open-api/lecture",
            "/public/assets",
            "/health-check",
            "/mypage/mypage"
    );

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("Authorization interceptor URL: {}", uri);

        // OPTIONS 메서드 및 리소스 요청 우회
        if (HttpMethod.OPTIONS.matches(request.getMethod()) || handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // 우회 경로 체크
        if (EXCLUDED_PATHS.stream().anyMatch(uri::startsWith)) {
            log.info("Bypassed authentication for URI: {}", uri);
            return true;
        }

        // 토큰 검증
        var accessToken = request.getHeader("authorization-token");
        if (accessToken == null) {
            log.warn("Authorization token not found for URI: {}", uri);
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
        }

        // 유효 토큰인지 확인
        var userId = tokenBusiness.validationToken(accessToken);
        if (userId != null) {
            setUserIdInContext(userId);
            return true;
        }

        // Refresh 토큰 검증 및 재발행 처리
        return handleRefreshToken(request, response);
    }

    // Refresh 토큰 처리 로직
    private boolean handleRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = request.getHeader("refresh-token");
        if (refreshToken != null) {
            try {
                var userId = tokenBusiness.validationToken(refreshToken);
                if (userId != null) {
                    var newAccessTokenResponse = tokenBusiness.reissueToken(userId);
                    response.setHeader("new-authorization-token", newAccessTokenResponse.getAccessToken());
                    setUserIdInContext(userId);
                    log.info("Reissued access token for user ID: {}", userId);
                    return true;
                }
            } catch (ApiException e) {
                log.error("Refresh token validation failed: {}", e.getMessage());
                throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
            }
        }
        throw new ApiException(ErrorCode.BAD_REQUST, "Authentication failed");
    }

    // RequestContext에 사용자 ID 설정
    private void setUserIdInContext(Long userId) {
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
        // 추가로 username을 저장하려면 별도의 호출이 필요
        requestContext.setAttribute("username", "your_username_value", RequestAttributes.SCOPE_REQUEST);
    }
}
