package org.ItBridge.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.ItBridge.Common.error.TokenErrorcode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.token.Controller.model.TokenDto;
import org.ItBridge.domain.token.Ifs.TokenHelperIfs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {
    @Value("${token.secret.key}")
    private String secretKey;
    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;
    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    //서명을 위한 키,각각 token에 대한 expired을 지정해야되서 yaml
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        var expiredAt = Date.from(expiredLocalDateTime.atZone(
                ZoneId.systemDefault()
        ).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();
        return TokenDto.builder()
                .expiredAt(expiredLocalDateTime)
                .token(jwtToken)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        var expiredAt = Date.from(expiredLocalDateTime.atZone(
                ZoneId.systemDefault()
        ).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();
        return TokenDto.builder()
                .expiredAt(expiredLocalDateTime)
                .token(jwtToken)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            var result = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(result.getBody());


        }catch (Exception e){
            if(e instanceof SignatureException){
                // 토큰 유효하지 않을때
                throw new ApiException(TokenErrorcode.INVALID_TOKEN,e);
            } else if (e instanceof ExpiredJwtException) {
                //만료된 토큰
                throw new ApiException(TokenErrorcode.EXPIRED_TOKEN,e);
            }
            else {
                throw new ApiException(TokenErrorcode.TOKEN_EXCEPTION,e);
            }
        }


    }
}
