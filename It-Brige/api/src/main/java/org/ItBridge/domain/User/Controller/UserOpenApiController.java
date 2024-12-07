package org.ItBridge.domain.User.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.User.Business.UserBusiness;
import org.ItBridge.domain.User.Controller.Model.UserLoginRequest;
import org.ItBridge.domain.User.Controller.Model.UserRegisterRequest;
import org.ItBridge.domain.User.Controller.Model.UserResponse;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Controller.model.TokenResponse;
import org.ItBridge.domain.token.Service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api")
public class UserOpenApiController {
    private final UserBusiness userBusiness;
    private final TokenService tokenService;
    @PostMapping("/login")
    public Api<TokenResponse> login(@Valid @RequestBody Api<UserLoginRequest> request){
        var response = userBusiness.login(request.getBody());
        var username = response.getUsername();
        RequestContextHolder.getRequestAttributes().setAttribute("username",username, RequestAttributes.SCOPE_SESSION);
        return Api.ok(response);
    }

    @PostMapping("/register")
    public Api<UserResponse> register(@Valid @RequestBody Api<UserRegisterRequest> request) {
        var response = userBusiness.register(request.getBody());
        return Api.ok(response);
    }
    @PostMapping("/logout")
    public Api<UserResponse> logout(@RequestHeader("Authorization-Token") String accessToken) {
        try {
            Long userId = tokenService.validationToken(accessToken); // 사용자 ID 추출
            var response = userBusiness.logout(userId); // 로그아웃 처리
            return Api.ok(response);
        } catch(ApiException e){

            throw new ApiException(ErrorCode.BAD_REQUST, "로그아웃 실패");
        }

    }

}
