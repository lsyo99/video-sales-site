package org.ItBridge.Common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMesage;
    private String resultDescription;


    public static Result ok(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultDescription(ErrorCode.OK.getDescription())
                .resultMesage("Ok")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultDescription(errorCodeIfs.getDescription())
                .resultMesage("에러발생")
                .build();
    }

    //비추
    public static Result ERROR(ErrorCodeIfs errorCodeIfs,Throwable tx){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultDescription(errorCodeIfs.getDescription())
                .resultMesage(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultDescription(errorCodeIfs.getDescription())
                .resultMesage(description)
                .build();
    }
}