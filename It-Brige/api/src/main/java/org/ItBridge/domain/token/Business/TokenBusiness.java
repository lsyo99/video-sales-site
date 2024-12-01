//package org.ItBridge.domain.token.Business;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.ItBridge.Common.annotation.Business;
//import org.ItBridge.Common.error.ErrorCode;
//import org.ItBridge.Common.exception.ApiException;
//import org.ItBridge.domain.token.Controller.model.TokenResponse;
//import org.ItBridge.domain.token.Converter.TokenConverter;
//import org.ItBridge.domain.token.Service.TokenService;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Business
//@Slf4j
//public class TokenBusiness {
//    private final TokenService tokenService;
//    private final TokenConverter tokenConverter;
//
//    // user entity, user id 추출 -> access,refresh token 발행, converter를 token response로 변경
//    public TokenResponse issueToken(UserEntity userEntity){
//        return Optional.ofNullable(userEntity)
//                .map(ue-> {
//                    return ue.getId();
//                })
//                .map(userId -> {
//                    var accessToken = tokenService.issueAccessToken(userId);
//                    var refreshToken = tokenService.issueRefreshToken(userId);
//                    return tokenConverter.toResponse(accessToken,refreshToken);
//                })
//                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
//    }
//    public Long validationToken(String validationAccessToken){
//
//        var userId = tokenService.validationToken(validationAccessToken);
//
//        return userId;
//    }
//}
