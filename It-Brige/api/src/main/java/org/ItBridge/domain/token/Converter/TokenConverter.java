package org.ItBridge.domain.token.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.token.Controller.model.TokenDto;
import org.ItBridge.domain.token.Controller.model.TokenResponse;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {
    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken){
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken()) // 수정된 매핑
                .refreshTokenExpiredAt(refreshToken.getExpiredAt()) // 수정된 매핑
                .build();
    }
    public TokenResponse toResponse(String accessToken, String refreshToken, String username, Long userId) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(username) // 사용자 이름 포함
                .userId(userId)
                .build();
    }
}