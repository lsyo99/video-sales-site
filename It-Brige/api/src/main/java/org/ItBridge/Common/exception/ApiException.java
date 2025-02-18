package org.ItBridge.Common.exception;

import lombok.Getter;
import org.ItBridge.Common.error.ErrorCodeIfs;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{
    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs){
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs,String errorDescription){
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx){
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }
    public ApiException(ErrorCodeIfs errorCodeIfs,String errorDescription, Throwable tx){
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
}