package org.ItBridge.domain.token.Ifs;

import org.ItBridge.domain.token.Controller.model.TokenDto;

import java.util.Map;

public interface TokenHelperIfs{
    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);

    Map<String,Object> validationTokenWithThrow(String token);
}