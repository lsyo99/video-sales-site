//package org.ItBridge.domain.token.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.ItBridge.Common.error.ErrorCode;
//import org.ItBridge.Common.exception.ApiException;
//import org.ItBridge.domain.token.Controller.model.TokenDto;
//import org.ItBridge.domain.token.Ifs.TokenHelperIfs;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//@Service
//@RequiredArgsConstructor
//public class TokenService {
//    private final TokenHelperIfs tokenHelperIfs;
//
//    public TokenDto issueAccessToken(Long userId){
//        var data = new HashMap<String, Object>();
//        data.put("userId",userId);
//        return tokenHelperIfs.issueAccessToken(data);
//    }
//    public TokenDto issueRefreshToken(Long userId){
//        var data = new HashMap<String, Object>();
//        data.put("userId",userId);
//        return tokenHelperIfs.issueRefreshToken(data);
//    }
//    public Long validationToken(String token){
//        var map = tokenHelperIfs.validationTokenWithThrow(token);
//        var userId = map.get("userId");
//        Objects.requireNonNull(userId,()->{throw new ApiException(ErrorCode.NULL_POINT);
//        });
//        return Long.parseLong(userId.toString());
//
//    }
//}