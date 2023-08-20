package com.spring.market.config;


import com.spring.market.handler.LoginFailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@Component
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 로그인 실패 핸들러 의존성 주입
     */
    private final LoginFailHandler loginFailHandler;

    /**
     * 비밀번호 암호화 (IoC에 등록 되있어서 빈주입 후 사용하면 된다.)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 관련 설정 하는곳
     */
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .usernameParameter("login_id")
                .passwordParameter("member_password")
                .defaultSuccessUrl("/")
                .failureHandler(loginFailHandler);

        http.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/signin");

        // 인증 및 권한 관리
        http.authorizeRequests(authorize ->
                     authorize.antMatchers("/mypage/**").authenticated()
                             .antMatchers("/chat/**").authenticated()
                             .anyRequest().permitAll()
        );

        return http.build();
    }
}
