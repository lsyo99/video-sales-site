package org.ItBridge.domain.User.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.User.Controller.Model.UserRegisterRequest;
import org.ItBridge.domain.User.Controller.Model.UserResponse;

import java.util.Optional;


@Converter
@RequiredArgsConstructor

public class UserConverter {
    public UserEntity toEntity(UserRegisterRequest userRegisterRequest){
        return Optional.ofNullable(userRegisterRequest)
                .map(it ->{
                    return UserEntity.builder()
                            .name(userRegisterRequest.getName())
                            .email(userRegisterRequest.getEmail())
                            .password(userRegisterRequest.getPassword())
                            .build();
                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it->{
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .build();
                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }
}
