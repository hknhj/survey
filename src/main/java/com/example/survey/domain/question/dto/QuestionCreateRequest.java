package com.example.survey.domain.question.dto;

import lombok.Getter;

@Getter
public class QuestionCreateRequest {
    private String content;
    private String questionType;
    private Boolean required;
    private Integer orderNumber;
}
