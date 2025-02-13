package org.ItBridge.domain.User.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.User.Business.UserBusiness;
import org.ItBridge.domain.User.Controller.Model.UserLoginRequest;
import org.ItBridge.domain.User.Controller.Model.UserRegisterRequest;
import org.ItBridge.domain.User.Controller.Model.UserResponse;
import org.ItBridge.domain.User.Controller.Model.UserRoleResponse;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Business.TokenBusiness;
import org.ItBridge.domain.token.Controller.model.TokenResponse;
import org.ItBridge.domain.token.Service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api")
@Slf4j
public class UserOpenApiController {
    private final UserBusiness userBusiness;
    private final TokenService tokenService;
    private final TokenBusiness tokenBusiness;
    @PostMapping("/login")
    public Api<TokenResponse> login(@Valid @RequestBody Api<UserLoginRequest> request){
        var response = userBusiness.login(request.getBody());
        var username = response.getUsername();
        var userId = response.getUserId();
        var userRole = response.getUserRole();
        RequestContextHolder.getRequestAttributes().setAttribute("username",username, RequestAttributes.SCOPE_SESSION);
        RequestContextHolder.getRequestAttributes().setAttribute("userId",userId, RequestAttributes.SCOPE_SESSION);
        RequestContextHolder.getRequestAttributes().setAttribute("userrole",userRole,RequestAttributes.SCOPE_REQUEST);
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
    @GetMapping("/user/role")
    public Api<UserRoleResponse> getUserRole(HttpServletRequest request) {
        Long userId = (Long) (Long) request.getSession().getAttribute("userId");
        log.info("userid: {}",userId);
        var role = userBusiness.getUserRole(userId);
        return Api.ok(role);
    }
    @PostMapping("/refresh")
    public Api<TokenResponse> refreshAccessToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new ApiException(ErrorCode.SERVER_ERROR,"유효하지 않은 Refresh Token");
        }

        try {
            TokenResponse newTokens = tokenBusiness.reissueTokens(refreshToken);
            return Api.ok(newTokens);
        } catch (ApiException e) {
            throw new ApiException(ErrorCode.SERVER_ERROR,"토큰 재발급 실패", e);
        }
    }

}
