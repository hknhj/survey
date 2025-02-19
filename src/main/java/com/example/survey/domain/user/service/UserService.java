package com.example.survey.domain.user.service;

import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.dto.LoginRequest;
import com.example.survey.domain.user.dto.LoginResponse;
import com.example.survey.domain.user.dto.RegisterRequest;
import com.example.survey.domain.user.dto.RegisterResponse;
import com.example.survey.domain.user.exception.LoginFailedException;
import com.example.survey.domain.user.exception.RegisterFailedException;
import com.example.survey.domain.user.repository.UserRepository;
import com.example.survey.domain.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public RegisterResponse register(RegisterRequest registerRequest) {

        // 회원가입 validation 확인
        User user = registerValid(registerRequest);

        // 저장된 유저의 정보를 토대로 RegisterResponse 생성 후 반환
        return RegisterResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {

        // 이메일, 비밀번호를 통해 validation 확인
        User user = loginValid(loginRequest);

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(user.getUserId());

        // 로그인 성공하면 LoginResponse 반환
        return LoginResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .token(token)
                .build();
    }

    private User registerValid(RegisterRequest registerRequest) {

        // 해당 이메일이 존재하면 회원가입 실패
        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new RegisterFailedException();

        return User.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .username(registerRequest.getUsername())
                .build();
    }

    // 이메일, 비밀번호를 토대로 valid 확인
    private User loginValid(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new LoginFailedException();

        return user;
    }
}
