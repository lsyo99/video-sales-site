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

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler) throws Exception {
        log.info("Authorization interceptor url : {} ", request.getRequestURL());

        //WEB, chorm의 경우  get,post option = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        //js, html png resource를 요청하는 경우 pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

//        //header 검증
        var accessToken = request.getHeader("authorization-token");
//
        if (accessToken == null) {
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
        }
        var userId = tokenBusiness.validationToken(accessToken);
        if (userId != null) {
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

            return true;
        }
//refreshtoken
        var refreshToken = request.getHeader("refresh-token");
        if (refreshToken != null) {
            var userid = tokenBusiness.validationToken(refreshToken); // Long 타입 반환
            if (userid != null) {
                // 리프레시 토큰이 유효하면 새로운 액세스 토큰 발급
                var newAccessTokenResponse = tokenBusiness.reissueToken(userid);
                var newAccessToken = newAccessTokenResponse.getAccessToken();

                // 새로운 액세스 토큰을 응답 헤더에 추가
                response.setHeader("new-authorization-token", newAccessToken);

                // 사용자 정보를 요청 컨텍스트에 추가
                var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
                requestContext.setAttribute("userId", userid, RequestAttributes.SCOPE_REQUEST);

                return true;
            }
        }
//
        throw new ApiException(ErrorCode.BAD_REQUST, "인증 실패");
////        return false; //인증 실패
//        return true;
//        //TODO 만료된 토큰 발행시 토큰 재발행(login중에만 )
//        //TODO}
    }
    private void setUserIdInContext(Long userId) {
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
    }}
//}
