package com.example.survey.domain.question.dto;

import com.example.survey.domain.question.domain.Question;
import com.example.survey.domain.question.domain.QuestionType;
import com.example.survey.domain.survey.domain.Survey;
import lombok.Getter;

@Getter
public class QuestionCreateRequest {
    private String content;
    private String questionType;
    private Boolean required;
    private Integer orderNumber;

    // DTO -> Question 엔티티 변환
    public Question toEntity(Survey survey) {
        return Question.builder()
                .survey(survey)
                .content(content)
                .questionType(QuestionType.fromString(questionType)) // Enum 변환
                .required(required)
                .orderNumber(orderNumber)
                .build();
    }
}
