package org.ItBridge.domain.token.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.error.TokenErrorcode;
import org.ItBridge.Common.exception.ApiException;

import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Controller.model.TokenResponse;
import org.ItBridge.domain.token.Converter.TokenConverter;
import org.ItBridge.domain.token.Service.TokenService;
import org.ItBridge.domain.token.helper.JwtTokenHelper;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Business
@Slf4j
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;
    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;

    // 사용자 엔티티에서 userId와 username을 추출하여 accessToken과 refreshToken을 발급
    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    var userId = ue.getId();       // 사용자 ID 추출
                    var username = ue.getName();  // 사용자 이름 추출
                    log.info("Issuing token for userId: {}, username: {}", userId, username);
                    // 토큰 발급 시 userId와 username을 함께 전달
                    var accessToken = tokenService.issueAccessToken(userId, username);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    var userRole = userService.getUserRole(userId);
                    // 응답 객체에 accessToken, refreshToken, username 포함
                    return tokenConverter.toResponse(accessToken, refreshToken, username, userId, userRole);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
//    public Long validationToken(String validationAccessToken) {
//        return tokenService.validationToken(validationAccessToken);
//    }
public Map<String, Object> validationToken(String token) {
    // 토큰을 검증하고 클레임 반환
    Map<String, Object> claims = jwtTokenHelper.validationTokenWithThrow(token);
    log.info("Token claims: {}", claims);

    // userId 검증 (필수 필드 체크)
    if (!claims.containsKey("userId")) {
        throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND, "userId가 토큰에 없습니다.");
    }

    return claims; // 전체 클레임 반환
}

    public TokenResponse reissueTokens(String refreshToken) {
        Map<String, Object> claims = jwtTokenHelper.validationTokenWithThrow(refreshToken);

        Long userId = ((Number) claims.get("userId")).longValue(); // userId를 long 타입으로 변환
        String username = (String) claims.get("username");

        // 새로운 Access 및 Refresh 토큰 발급
        var newAccessToken = tokenService.issueAccessToken(userId, username);
        var newRefreshToken = tokenService.issueRefreshToken(userId);
        var userRole = userService.getUserRole(userId);
        return tokenConverter.toResponse(newAccessToken, newRefreshToken, username, userId, userRole);
    }
    public TokenResponse reissueToken(long userid) {
        UserEntity userEntity = userService.getUserId(userid);
        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    var userId = ue.getId();       // 사용자 ID 추출
                    var username = ue.getName();  // 사용자 이름 추출

                    // 토큰 발급 시 userId와 username을 함께 전달
                    var accessToken = tokenService.issueAccessToken(userId, username);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    var userRole =userService.getUserRole(userId);
                    // 응답 객체에 accessToken, refreshToken, username 포함
                    return tokenConverter.toResponse(accessToken, refreshToken, username,userId, userRole);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public String getUserRole(Long userId) {
        var user = userService.getUserId(userId);
        return user.getAuthority().getName();
    }
    public Long validateAndGetUserId(String token) {
        Map<String, Object> claims = validationToken(token);

        try {
            return ((Number) claims.get("userId")).longValue(); // String이 아닌 long으로 변환
        } catch (ClassCastException e) {
            throw new ApiException(ErrorCode.SERVER_ERROR, "userId의 타입 변환 중 오류 발생");
        }
    }
}
