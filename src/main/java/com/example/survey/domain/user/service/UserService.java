package com.example.survey.domain.user.service;

import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.dto.LoginRequest;
import com.example.survey.domain.user.dto.LoginResponse;
import com.example.survey.domain.user.dto.RegisterRequest;
import com.example.survey.domain.user.dto.RegisterResponse;
import com.example.survey.domain.user.repository.UserRepository;
import com.example.survey.global.JwtTokenProvider;
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
        // RegisterRequest 파싱해서 DB에 저장
        User user = userRepository.save(User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getUsername())
                .build());

        // 저장된 유저의 정보를 토대로 RegisterResponse 생성 후 반환
        return RegisterResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // 이메일을 통해 유저를 찾고, 없으면 null 반환
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        // 이메일이 존재하지 않으면 null 반환
        if (user == null) {
            return null;
        }
        // 비밀번호가 일치하지 않으면 null 반환
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }

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

    private Boolean isEmailValid(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
