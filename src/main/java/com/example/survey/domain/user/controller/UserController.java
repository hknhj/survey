package com.example.survey.domain.user.controller;

import com.example.survey.domain.user.dto.RegisterRequest;
import com.example.survey.domain.user.dto.RegisterResponse;
import com.example.survey.domain.user.service.UserService;
import com.example.survey.global.DefaultResponseDto;
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
    public ResponseEntity<DefaultResponseDto<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        // 서비스 호출
        RegisterResponse registerResponse = userService.register(registerRequest);

        // DefaultResponseDto 생성
        DefaultResponseDto<RegisterResponse> response = DefaultResponseDto.response(
                "SUCCESSS",
                "회원 가입이 완료되었습니다.",
                registerResponse
        );

        // HTTP 201 + 응답 반환
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
