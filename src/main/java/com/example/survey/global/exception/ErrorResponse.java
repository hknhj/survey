package com.example.survey.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ErrorResponse {
    private String code;
    private String message;
}