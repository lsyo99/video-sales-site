package org.ItBridge.domain.token.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;

import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Controller.model.TokenResponse;
import org.ItBridge.domain.token.Converter.TokenConverter;
import org.ItBridge.domain.token.Service.TokenService;

import java.util.Optional;

@RequiredArgsConstructor
@Business
@Slf4j
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;
    private final UserService userService;

    // 사용자 엔티티에서 userId와 username을 추출하여 accessToken과 refreshToken을 발급
    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    var userId = ue.getId();       // 사용자 ID 추출
                    var username = ue.getName();  // 사용자 이름 추출

                    // 토큰 발급 시 userId와 username을 함께 전달
                    var accessToken = tokenService.issueAccessToken(userId, username);
                    var refreshToken = tokenService.issueRefreshToken(userId);

                    // 응답 객체에 accessToken, refreshToken, username 포함
                    return tokenConverter.toResponse(accessToken, refreshToken, username);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
    public Long validationToken(String validationAccessToken) {
        return tokenService.validationToken(validationAccessToken);
    }

    public TokenResponse reissueTokens(String refreshToken) {
        // 리프레시 토큰 검증 및 사용자 정보 추출
        var userId = tokenService.validationToken(refreshToken);
        var userName = userService.getUserId(userId).getName();


        // 새로운 액세스 토큰과 리프레시 토큰 발급
        var newAccessToken = tokenService.issueAccessToken(userId,userName);
        var newRefreshToken = tokenService.issueRefreshToken(userId);

        // 토큰 응답 객체 반환
        return tokenConverter.toResponse(newAccessToken, newRefreshToken,userName);
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

                    // 응답 객체에 accessToken, refreshToken, username 포함
                    return tokenConverter.toResponse(accessToken, refreshToken, username);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
