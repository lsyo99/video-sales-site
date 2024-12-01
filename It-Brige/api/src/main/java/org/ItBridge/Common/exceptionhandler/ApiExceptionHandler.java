package org.ItBridge.Common.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)

public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiexception){
        log.error("",apiexception);
        var errorCode = apiexception.getErrorCodeIfs();
        var errorDescription = apiexception.getErrorDescription();
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode,apiexception.getErrorDescription())
                );
    }
}