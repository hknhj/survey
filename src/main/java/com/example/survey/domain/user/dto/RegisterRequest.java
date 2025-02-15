package com.example.survey.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String password;
    private String username;

    @Builder
    public RegisterRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
