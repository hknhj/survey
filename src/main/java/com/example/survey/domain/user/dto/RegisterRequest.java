package com.example.survey.domain.user.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
}
