package org.ItBridge.Common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenErrorcode implements ErrorCodeIfs{
    INVALID_TOKEN(400,2000,"유효하지 않은 토큰"),
    EXPIRED_TOKEN(400,2001,"만료된 토큰"),
    TOKEN_EXCEPTION(400,2002,"알수 없는 토큰 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUUND(400,2003,"토큰이 없음")
    ;


    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}