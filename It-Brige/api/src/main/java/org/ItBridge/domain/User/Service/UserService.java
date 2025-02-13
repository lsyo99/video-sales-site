package org.ItBridge.domain.User.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.error.UserErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.auth.AuthorityRepository;
import org.ItBridge.db.user.UserEntity;
import org.ItBridge.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    public UserEntity register(UserEntity userEntity) {
        userEntity.setCreated_at(LocalDateTime.now());

        // 기본 권한 설정 (USER)
        if (userEntity.getAuthority() == null) {
            var defaultAuthority = authorityRepository.findByName("USER")
                    .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Default authority not found"));
            userEntity.setAuthority(defaultAuthority);
        }

        return userRepository.save(userEntity);
    }


    public UserEntity login(
            String email,
            String password
    ){
        var entity = getUser(email,password);

        return entity;
    }

    public UserEntity getUser(String email, String password) {

        return userRepository.findFirstByEmailAndPasswordOrderByIdDesc(email,password)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
    public  UserEntity getUserId(long userId){
        return userRepository.findFirstByIdOrderByIdDesc(userId).orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public String getUserRole(Long userId) {
        if (userId == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "userId가 null입니다.");
        }
        return userRepository.findFirstByIdOrderByIdDesc(userId).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"user를 찾을 수 없음"))
                .getAuthority().getName();

    }
//    public UserEntity getUser(
//            Long Id
//    ){
//        return userRepository. findFirstbyIdOrderByIdDesc(
//                        Id
//                )
//                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
//    }


}
