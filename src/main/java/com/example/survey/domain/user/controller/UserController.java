package com.example.survey.domain.user.controller;

import com.example.survey.domain.user.dto.LoginRequest;
import com.example.survey.domain.user.dto.LoginResponse;
import com.example.survey.domain.user.dto.RegisterRequest;
import com.example.survey.domain.user.dto.RegisterResponse;
import com.example.survey.domain.user.service.UserService;
import com.example.survey.global.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        // 회원가입 서비스 호출
        RegisterResponse registerResponse = userService.register(registerRequest);

        // DefaultResponseDto 생성
        DefaultResponse<RegisterResponse> response = DefaultResponse.response(
                "회원 가입이 완료되었습니다.",
                registerResponse
        );

        // HTTP 201 + 응답 반환
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        // 로그인 서비스 호출
        LoginResponse loginResponse = userService.login(loginRequest);

        // DefaultResponseDto 생성
        DefaultResponse<LoginResponse> response = DefaultResponse.response(
                    "로그인에 성공하였습니다 " + loginResponse.getUsername() + "님",
                    loginResponse
        );

        // HTTP 200 + 응답 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
