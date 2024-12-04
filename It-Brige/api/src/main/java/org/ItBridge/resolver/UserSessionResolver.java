//package org.ItBridge.resolver;
//
//import lombok.RequiredArgsConstructor;
//import org.ItBridge.Common.annotation.UserSession;
//import org.ItBridge.domain.User.Service.UserService;
//import org.springframework.core.MethodParameter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//@Component
//@RequiredArgsConstructor
////request요청이 들어오면 실행 aop로
//public class UserSessionResolver implements HandlerMethodArgumentResolver {
//    private final UserService userService;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        //지원하는 파라미터 체크, 어노테이션 체크
//        //1. 어노테이션 있는지 체크
//        var annotation = parameter.hasParameterAnnotation(UserSession.class);
//        //2.파라미터 타입 체크
//        var parameterType = parameter.getParameterType().equals(User.class);
//
//        return (annotation&& parameterType);
//    }
//
//    @Override
//    public Object resolveArgument(
//            MethodParameter parameter, ModelAndViewContainer mavContainer,
//            NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
//            throws Exception {
//        //support parameter에서 true 반환시 여기서 설정
//        //request context Holder에서 찾아오기
//        var requestContxt = RequestContextHolder.getRequestAttributes();
//        var userId = requestContxt.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
//
//        var userEntity = userService.getUser(Long.parseLong(userId.toString()));
//        // 사용자 정보 셋팅
//
//
//        return org.ItBridge.domain.User.Model.User.builder()
//                .id(userEntity.getId())
//                .name(userEntity.getName())
//                .email(userEntity.getEmail())
//                .password(userEntity.getPassword())
//                .build();
//    }
//
//}
//
