package com.example.survey.domain.user.exception;

public class RegisterFailedException extends RuntimeException {
    public RegisterFailedException() {
        super("회원가입에 실패하였습니다.");
    }
}