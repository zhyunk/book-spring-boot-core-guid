package com.springboot.jpa.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static com.springboot.jpa.common.InformationApi.HEADER_AUTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService service;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long tokenValidMillisecond = 1000L * 60 * 60;

    public String createToken(String userUid, List<String> roles) {
        log.info("[createToken] 토큰 생성 시작");
        
        Claims claims = Jwts.claims().setSubject(userUid);
        claims.put("roles", roles); // 토큰을 사용하는 사용자 권한 확인을 위해 입력
        Date now = new Date();

        // 토큰 생성
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 필터에서 인증이 성공했을 때 SecurityContextHolder에 저장할 Authentication을 생성
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        UserDetails userDetails = service.loadUserByUsername(this.getUsername(token));

        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails username : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts
                .parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }
    
    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        return request.getHeader(HEADER_AUTH);
    }

    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");

        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            log.info("[validateToken] 토큰 유효 체크 예외 발생");

            return false;
        }
    }
}
