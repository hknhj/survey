package com.example.survey.global.exception;

import com.example.survey.domain.survey.exception.SurveyAuthorizationException;
import com.example.survey.domain.user.exception.LoginFailedException;
import com.example.survey.domain.user.exception.RegisterFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INVALID_PARAMETER.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(RegisterFailedException.class)
    public ResponseEntity<ErrorResponse> handleRegisterFailedException(RegisterFailedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INVALID_PARAMETER.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(SurveyAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleSurveyAuthorizationException(SurveyAuthorizationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.RESOURCE_NOT_FOUND.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                        .message("서버 내부 오류가 발생했습니다.")
                        .build());
    }
}
