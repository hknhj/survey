package com.example.survey.domain.question.dto;

import com.example.survey.domain.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String content;
    private String questionType;
    private Boolean required;
    private Integer orderNumber;

    // Question -> QuestionResponse 변환
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .content(question.getContent())
                .questionType(question.getQuestionType().toString())
                .required(question.getRequired())
                .orderNumber(question.getOrderNumber())
                .build();
    }
}
