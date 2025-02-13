package org.ItBridge.Config.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    // 제외할 경로 설정
    private final List<String> OPEN_API = List.of(
            "/open-api/**",
            "/home/**",
            "/proxy/image",
            "/public/assets",
            "/health-check",
            "/payment/**",
            "/lecture/**",
            "/notice/**",
            "/static/savevideo/**",
            "/discount",
            "/new"

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

//        registry.addViewController("/{spring:[^.]+}")
//                .setViewName("forward:/index.html");
        registry.addViewController("/{spring:[a-zA-Z0-9-_]+}").setViewName("forward:/index.html");
        registry.addViewController("/**/{spring:[a-zA-Z0-9-_]+}").setViewName("forward:/index.html");
        registry.addViewController("/lecture/{id}").setViewName("forward:/index.html");
        registry.addViewController("/notice/{page}").setViewName("forward:/index.html");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/api/lectures/videos/**")
                .excludePathPatterns("/static/savevideo/**")
                .excludePathPatterns(SWAGGER) // Swagger 문서
                .excludePathPatterns(DEFAULT_EXCLUDED) // 기본 제외 경로
                .excludePathPatterns(OPEN_API) // API 요청
                .excludePathPatterns("/css/**", "/js/**", "/image/**","/savevideo/**" ,"/favicon.ico", "/static/**")
               ;

        log.info("AuthorizationInterceptor is registered as an interceptor.");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/savevideo/**")
//                .addResourceLocations("classpath:/src/main/resources/static/savevideo/")
//                .setCachePeriod(3600).resourceChain(true);
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:static/css/")
                .setCachePeriod(3600)
                .resourceChain(true);
        registry.addResourceHandler("/js/**")
//                .addResourceLocations("classpath:/static/js/")
                .addResourceLocations("classpath:/static/js/")
                .setCachePeriod(3600)
                .resourceChain(true);
        registry.addResourceHandler("/image/**")
                .addResourceLocations("classpath:/static/image/")
                .setCachePeriod(3600);
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
