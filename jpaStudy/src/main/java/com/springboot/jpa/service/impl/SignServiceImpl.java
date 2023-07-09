package com.springboot.jpa.service.impl;

import com.springboot.jpa.common.CommonResponse;
import com.springboot.jpa.config.security.JwtTokenProvider;
import com.springboot.jpa.data.dto.SignInResultDto;
import com.springboot.jpa.data.dto.SignUpResultDto;
import com.springboot.jpa.data.entity.User;
import com.springboot.jpa.data.repository.UserRepository;
import com.springboot.jpa.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResultDto signup(String id, String password, String name, String role) {
        log.info("[getSignUp Result] 회원가입 정보 전달 ");

        User user;
        if (role.equalsIgnoreCase("admin")) 
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        else
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        
        log.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getName().isEmpty()) {
            log.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignInResultDto signin(String id, String password) throws RuntimeException {
        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        
        User user = userRepository.getByUid(id);
        log.info("[getSignInResult] Id : {}", id);
        
        log.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException();
        
        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
                .build();
        
        log.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
