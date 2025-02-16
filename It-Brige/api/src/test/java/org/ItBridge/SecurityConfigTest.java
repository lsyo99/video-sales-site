//package org.ItBridge;
//
//import org.ItBridge.Interceptor.AuthorizationInterceptor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import static org.mockito.Mockito.doReturn;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest(properties = "spring.profiles.active=test") // ✅ 테스트 환경에서 실행
//public class SecurityConfigTest {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private AuthorizationInterceptor authorizationInterceptor;  // ✅ 인터셉터 Mocking 처리
//
//
//
//    @BeforeEach
//    void setUp() throws Exception {
//        doReturn(true).when(authorizationInterceptor).preHandle(any(), any(), any()); // ✅ 인증 우회 설정
//    }
//
//
//    @Test
//    void testPasswordEncoder() {
//        // ✅ 비밀번호 인코딩 테스트
//        String rawPassword = "test1234";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//
//        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
//    }
//
//    @TestConfiguration
//    @Profile("test") // ✅ 테스트 환경에서만 적용
//    public static class TestSecurityConfig {
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable()) // ✅ 최신 방식으로 CSRF 비활성화
//                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // ✅ 모든 요청 허용
//                    .formLogin(form -> form.disable()) // ✅ 최신 방식
//                    .httpBasic(basic -> basic.disable()); // ✅ 최신 방식
//
//            return http.build();
//        }
//
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder(); // ✅ 비밀번호 인코더 추가
//        }
//    }
//}
