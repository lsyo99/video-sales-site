package org.ItBridge.Config.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정 (비활성화)
                .csrf(AbstractHttpConfigurer::disable)
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll() // REST API 경로
                        .requestMatchers("/css/**", "/js/**", "/images/**","/savevideo/**","/image/**","/index.html").permitAll() // 정적 리소
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        .requestMatchers("/user/**").hasRole("USER")   // 사용자만 접근 가능
                        .anyRequest().permitAll()                     // 나머지 요청은 모두 허용
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")                          // 커스텀 로그인 페이지
                        .defaultSuccessUrl("/redirect", true)         // 로그인 성공 시 리다이렉트
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/")                        // 로그아웃 성공 시 리다이렉트
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}