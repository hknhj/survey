package com.example.survey.domain.survey.exception;

public class SurveyAuthorizationException extends RuntimeException {
    public SurveyAuthorizationException() {
        super("해당 설문조사에 대한 권한이 없습니다.");
    }
}