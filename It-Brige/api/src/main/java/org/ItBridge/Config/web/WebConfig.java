package org.ItBridge.Config.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor; // 주입받을 수 있는 이유는 컴포넌트이기 때문

    //아래의 list는 제외되는 모든 주소를 통과시키기 위해서 생성 swagger, openapi 등등하
    private final List<String> OPEN_API = List.of(
            "/open-api/**",
            "/image/**",
            "/course/**",
            "/home/**"
    );
    private final List<String> DEFAULT_EXCLUDED = List.of(
            "/",
            "/favicon.ico", // 절대 경로로 수정
            "/error"
    );
    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**", // 오타 수정
            "/v3/api-docs/**"
    );
    private final List<String> STATIC_RESOURCES = List.of(
            "/css/**",
            "/js/**",
            "/images/**",
            "/static/**"
    );


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(SWAGGER)
                .excludePathPatterns(DEFAULT_EXCLUDED)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns("/favicon.ico", "/ItBridge-logo.png")
                .excludePathPatterns(STATIC_RESOURCES) // 정적 리소스 제외
         .excludePathPatterns("/**/*.png", "/**/*.jpg", "/**/*.css", "/**/*.js", "/favicon.ico");

        log.info("AuthorizationInterceptor is registered as an interceptor.");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    //추후에 reslover
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(userSessionResolver);
//    }
}
