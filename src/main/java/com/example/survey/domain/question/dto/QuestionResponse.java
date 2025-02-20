package com.example.survey.domain.question.dto;

import com.example.survey.domain.option.dto.ChoiceResponse;
import com.example.survey.domain.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionResponse {
    private Long questionId;
    private String content;
    private String questionType;
    private List<ChoiceResponse> options;
    private Boolean required;
    private Integer orderNumber;

    // QuestionResponse -> Question 변환
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .content(question.getContent())
                .questionType(question.getQuestionType().toString())
                .options(question.getChoices().stream()
                        .map(ChoiceResponse::from)
                        .toList())
                .required(question.getRequired())
                .orderNumber(question.getOrderNumber())
                .build();
    }
}
