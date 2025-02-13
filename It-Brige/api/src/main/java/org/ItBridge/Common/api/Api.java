package org.ItBridge.Common.api;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ItBridge.Common.error.ErrorCodeIfs;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;
    //성공을 위한 api 스펙
    public static <T> Api<T> ok(T data){
        var api = new Api<T>();
        api.body = data;
        api.result = Result.ok();
        return api;
    }

    public static <T> Api<T> ERROR(T result){
        var api = new Api();
        api.body = result;
        return api;
    }
    public static Api<Object> ERROR(Result result){
        var api = new Api();
        api.body = result;
        return api;
    }
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        var api = new Api();
        api.body = Result.ERROR(errorCodeIfs);
        return api;
    }
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs,Throwable tx){
        var api = new Api();
        api.body = Result.ERROR(errorCodeIfs,tx);
        return api;
    }
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs,String description){
        var api = new Api();
        api.body = Result.ERROR(errorCodeIfs,description);
        return api;
    }
}
