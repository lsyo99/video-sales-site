//package org.ItBridge.domain.token.Converter;
//
//import lombok.RequiredArgsConstructor;
//import org.ItBridge.Common.annotation.Converter;
//import org.ItBridge.Common.error.ErrorCode;
//import org.ItBridge.Common.exception.ApiException;
//import org.ItBridge.domain.token.Controller.model.TokenDto;
//import org.ItBridge.domain.token.Controller.model.TokenResponse;
//
//import java.util.Objects;
//
//@Converter
//@RequiredArgsConstructor
//public class TokenConverter {
//    public TokenResponse toResponse(TokenDto accesstoken, TokenDto refreshToken){
//        Objects.requireNonNull(accesstoken,()->{throw new ApiException(ErrorCode.NULL_POINT);});
//        Objects.requireNonNull(refreshToken,()->{throw new ApiException(ErrorCode.NULL_POINT);});
//        return TokenResponse.builder()
//                .accessToken(accesstoken.getToken())
//                .accessTokenExpiredAt(accesstoken.getExpiredAt())
//                .refreshToken(accesstoken.getToken())
//                .refreshTokenExpiredAt(accesstoken.getExpiredAt())
//                .build();
//    }
//}