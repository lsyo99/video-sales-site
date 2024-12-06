package org.ItBridge.Config.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    // 제외할 경로 설정
    private final List<String> OPEN_API = List.of(
            "/open-api/**",
            "/image/**",
            "/course/**",
            "/home/**"
    );
    private final List<String> DEFAULT_EXCLUDED = List.of(
            "/",
            "/favicon.ico",
            "/error",
            "/login"
    );
    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("Registering view controllers...");

        registry.addViewController("/login")
                .setViewName("forward:/index.html"); // SPA 경로 처리
        registry.addViewController("/")
                .setViewName("forward:/index.html"); // 루트 요청도 SPA로 처리
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(SWAGGER) // Swagger 문서
                .excludePathPatterns(DEFAULT_EXCLUDED) // 기본 제외 경로
                .excludePathPatterns(OPEN_API) // API 요청
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico", "/static/**"); // 정적 리소스 제외

        log.info("AuthorizationInterceptor is registered as an interceptor.");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/","/css/**", "/js/**", "/images/**", "/static/**")
                .addResourceLocations("classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/images/",
                        "classpath:/static/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

        registry.addResourceHandler("/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
                .allowedOrigins("http://localhost:8080") // Swagger UI가 실행 중인 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
