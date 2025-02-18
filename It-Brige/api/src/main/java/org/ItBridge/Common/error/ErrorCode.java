package org.ItBridge.Common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {
    //ok
    OK(200,200,"성공"),
    BAD_REQUST(HttpStatus.BAD_REQUEST.value(),400,"잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500,"서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(),512,"null_point")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
