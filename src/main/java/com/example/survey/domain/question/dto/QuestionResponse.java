package com.example.survey.domain.question.dto;

import com.example.survey.domain.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {
    private Long questionId;
    private Long surveyId;
    private String content;
    private String questionType;
    private Boolean required;
    private Integer orderNumber;
}
