package org.ItBridge.Common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {
    USER_NOT_FOUND(400, 1404, "사용자 NOT_FOUND");


    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}