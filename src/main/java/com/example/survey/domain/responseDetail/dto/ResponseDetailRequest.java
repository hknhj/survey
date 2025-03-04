package com.example.survey.domain.responseDetail.dto;

import lombok.Getter;

@Getter
public class ResponseDetailRequest {
    private Long questionId;
    private Long choiceId;
    private String answer;
}
