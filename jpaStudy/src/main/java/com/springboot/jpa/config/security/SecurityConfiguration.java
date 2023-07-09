package com.springboot.jpa.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtTokenProvider provider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                // UI 사용 비활성화
                .httpBasic().disable()

                // REST API에서는 CSRF 보안이 필요 없기 때문에 비활성화
                .csrf().disable()

                // 세션은 로그인 폼에서 사용하므로, 이 프로젝트에선 필요 없다 => STATELESS 설정.
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ).and()

                // 리소스 권한 설정
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger/**",
                        "/sign-api/exception"
                ).permitAll()
                .antMatchers(
                        "/sign-api/sign-in",
                        "/sign-api/sign-up",
                        "/sign-api/exception"
                ).permitAll()
                .antMatchers("**exception**").permitAll()
                .anyRequest().hasRole("ADMIN").and()

                // 권환 통과하지 못하는 예외 발생시 예외 전달
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()).and()
                
                // 인증 과정에서 예외 발생 시 예외 전달
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()

                // UsernamePasswordAuthenticationFilter 보다 먼저 JwtAuthenticationFilter 가 실행되게 설정
                // JwtAuthenticationFilter에서 인증 완료 시 UsernamePasswordAuthenticationFilter는 그냥 통과됨
                .addFilterBefore(
                        new JwtAuthenticationFilter(provider),
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
