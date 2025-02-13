package org.ItBridge.domain.User.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.error.UserErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.User.Controller.Model.UserLoginRequest;
import org.ItBridge.domain.User.Controller.Model.UserRegisterRequest;
import org.ItBridge.domain.User.Controller.Model.UserResponse;
import org.ItBridge.domain.User.Controller.Model.UserRoleResponse;
import org.ItBridge.domain.User.Converter.UserConverter;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Business.TokenBusiness;
import org.ItBridge.domain.token.Controller.model.TokenResponse;
import org.ItBridge.domain.token.helper.JwtTokenHelper;
import org.springframework.web.context.request.RequestContextHolder;

@Business
@RequiredArgsConstructor
@Slf4j
public class UserBusiness {
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;
    private final JwtTokenHelper jwtTokenHelper;

    public UserResponse register(UserRegisterRequest request) {
        // 회원가입 요청을 엔티티로 변환
        var userEntity = userConverter.toEntity(request);

        // 회원가입 처리
        var savedEntity = userService.register(userEntity);

        // UserEntity를 UserResponse로 변환
        return userConverter.toResponse(savedEntity);
    }
    public UserResponse logout(Long userId) {
        // 사용자 관련 토큰 삭제 또는 블랙리스트 등록 (선택 사항)
        log.info("User {} has been logged out", userId);
        RequestContextHolder.resetRequestAttributes();
        return new UserResponse();
    }


    public TokenResponse login(UserLoginRequest request) {
        // 사용자 인증 (이메일과 비밀번호 검증)
        var userEntity = userService.login(request.getEmail(), request.getPassword());

        if (userEntity == null) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND, "Invalid email or password");
        }

        // 토큰 발급
        return tokenBusiness.issueToken(userEntity);
    }


    public UserRoleResponse getUserRole(Long userId) {

        var role =  userService.getUserRole(userId);
        return userConverter.toRoleResponse(role);
    }
}
