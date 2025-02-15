package com.example.survey.domain.user.service;

import com.example.survey.domain.user.domain.User;
import com.example.survey.domain.user.dto.RegisterRequest;
import com.example.survey.domain.user.dto.RegisterResponse;
import com.example.survey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
