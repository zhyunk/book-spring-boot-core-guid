package com.springboot.jpa.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider provider;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String token = provider.resolveToken(request);
        log.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        
        log.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && provider.validateToken(token)) {

            Authentication authentication = provider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(request, response);
    }
}
