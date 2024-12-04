package org.ItBridge.domain.token.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.token.Controller.model.TokenDto;
import org.ItBridge.domain.token.Ifs.TokenHelperIfs;
import org.ItBridge.domain.token.helper.JwtTokenHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenHelperIfs tokenHelperIfs;
    private final JwtTokenHelper jwtTokenHelper;
    public String issueAccessToken(Long userId, String username) {
        // userId와 username을 토큰 생성에 활용
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        return jwtTokenHelper.issueAccessToken(claims).getToken();
    }

    public String issueRefreshToken(Long userId) {
        // refreshToken은 userId만 포함
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return jwtTokenHelper.issueRefreshToken(claims).getToken();
    }
    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");
        Objects.requireNonNull(userId,()->{throw new ApiException(ErrorCode.NULL_POINT);
        });
        return Long.parseLong(userId.toString());

    }
}