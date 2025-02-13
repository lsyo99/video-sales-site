package org.ItBridge.domain.token.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.error.TokenErrorcode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.token.Controller.model.TokenDto;
import org.ItBridge.domain.token.Ifs.TokenHelperIfs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        if (!data.containsKey("userId") || !data.containsKey("username")) {
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND, "userId 또는 username 정보가 없습니다.");
        }
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

        var jwtToken = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        log.info("발급된 AccessToken: {}", jwtToken);

        return TokenDto.builder()
                .expiredAt(expiredLocalDateTime)
                .token(jwtToken)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        if (!data.containsKey("userId")) {
            throw new ApiException(TokenErrorcode.AUTHORIZATION_TOKEN_NOT_FOUUND, "userId 정보가 없습니다.");
        }
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        var jwtToken = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        log.info("발급된 RefreshToken: {}", jwtToken);

        return TokenDto.builder()
                .expiredAt(expiredLocalDateTime)
                .token(jwtToken)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info("토큰 검증 성공. 클레임: {}", claims);
            return new HashMap<>(claims);

        } catch (ExpiredJwtException e) {
            throw new ApiException(TokenErrorcode.EXPIRED_TOKEN, "토큰이 만료되었습니다.");
        } catch (JwtException e) {
            log.error("유효하지 않은 토큰: {}", e.getMessage());
            throw new ApiException(TokenErrorcode.INVALID_TOKEN, "유효하지 않은 토큰입니다.");
        }
    }
}
