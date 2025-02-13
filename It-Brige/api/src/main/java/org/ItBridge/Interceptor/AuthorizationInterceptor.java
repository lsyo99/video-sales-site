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
import java.util.Map;
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
            "/proxy/mp4"

    );

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("Authorization interceptor URL: {}", uri);

        // OPTIONS 메서드 및 리소스 요청 우회
        if (HttpMethod.OPTIONS.matches(request.getMethod()) || handler instanceof ResourceHttpRequestHandler) {
            log.info("Skipping interceptor for OPTIONS or static resources: {}", uri);
            return true;
        }

        // 우회 경로 체크
        if (EXCLUDED_PATHS.stream().anyMatch(uri::startsWith)) {
            log.info("Bypassed authentication for URI: {}", uri);
            return true;
        }

        // Access Token 처리
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            accessToken = request.getHeader("Authorization"); // Authorization 헤더도 체크
        }
        if (accessToken == null) {
            log.warn("Authorization token이 없습니다.");
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND);
        }

        try {
            Map<String, Object> claims = tokenBusiness.validationToken(accessToken);
            Long userId = ((Number) claims.get("userId")).longValue(); // 명시적 타입 변환
            setUserIdInContext(userId);
            return true;

        } catch (ApiException e) {
            log.warn("Access Token 만료. Refresh Token 처리 시도.");
            return handleRefreshToken(request, response);
        }
    }

    // Refresh Token 처리 로직
    // Refresh 토큰 처리 로직
    private boolean handleRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("refresh-token");
        if (refreshToken == null) {
            log.warn("Refresh token이 요청 헤더에 없습니다.");
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND, "Refresh token이 요청 헤더에 없습니다.");
        }

        try {
            // RefreshToken 검증 및 사용자 ID 추출
            Long userId = tokenBusiness.validateAndGetUserId(refreshToken);

            // 새 Access 및 Refresh 토큰 발급
            var newTokens = tokenBusiness.reissueToken(userId);

            // 새 토큰을 응답 헤더에 설정
            response.setHeader("new-authorization-token", newTokens.getAccessToken());
            response.setHeader("new-refresh-token", newTokens.getRefreshToken());

            // RequestContext에 userId 설정
            setUserIdInContext(userId);

            log.info("Reissued tokens successfully for userId: {}", userId);
            return true;

        } catch (ApiException e) {
            log.error("Refresh token validation failed: {}", e.getMessage());
            throw new ApiException(TokenErrorcode.EXPIRED_TOKEN, "리프레시 토큰 처리 실패: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while handling refresh token: {}", e.getMessage());
            throw new ApiException(TokenErrorcode.TOKEN_EXCEPTION, "리프레시 토큰 처리 중 오류가 발생했습니다.");
        }
    }


    // RequestContext에 사용자 ID 및 역할 설정
    private void setUserIdInContext(Long userId) {
        if (userId == null) {
            log.warn("userId가 null입니다. Context에 설정할 수 없습니다.");
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND, "유효하지 않은 userId");
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.error("RequestContextHolder에서 RequestAttributes를 가져올 수 없습니다.");
            throw new ApiException(TokenErrorcode.INVALID_TOKEN, "RequestContext 설정 오류");
        }

        requestAttributes.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
        var userRole = tokenBusiness.getUserRole(userId);
        requestAttributes.setAttribute("userRole", userRole, RequestAttributes.SCOPE_REQUEST);

        log.info("User ID와 역할이 RequestContext에 설정되었습니다. userId: {}, userRole: {}", userId, userRole);
    }
}
