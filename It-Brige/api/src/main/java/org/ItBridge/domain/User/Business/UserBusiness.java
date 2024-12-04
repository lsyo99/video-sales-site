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
import org.ItBridge.domain.User.Converter.UserConverter;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.token.Business.TokenBusiness;
import org.ItBridge.domain.token.Controller.model.TokenResponse;

@Business
@RequiredArgsConstructor
@Slf4j
public class UserBusiness {
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    public UserResponse register(UserRegisterRequest request) {
        // 회원가입 요청을 엔티티로 변환
        var userEntity = userConverter.toEntity(request);

        // 회원가입 처리
        var savedEntity = userService.register(userEntity);

        // UserEntity를 UserResponse로 변환
        return userConverter.toResponse(savedEntity);
    }



    public TokenResponse login(UserLoginRequest request) {
        // 사용자 인증 (이메일과 비밀번호 검증)
        var userEntity = userService.login(request.getEamil(), request.getPassword());

        if (userEntity == null) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND, "Invalid email or password");
        }

        // 토큰 발급
        return tokenBusiness.issueToken(userEntity);
    }

}
