package com.example.survey.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RegisterResponse {
    private Long userId;
    private String email;
    private String username;
    private LocalDateTime createdAt;
}
