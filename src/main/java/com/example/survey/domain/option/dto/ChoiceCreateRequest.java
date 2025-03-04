package com.example.survey.domain.option.dto;

import com.example.survey.domain.option.domain.Choice;
import com.example.survey.domain.question.domain.Question;
import lombok.Getter;

@Getter
public class ChoiceCreateRequest {
    private String text;
    private Integer orderNumber;

    public Choice toEntity(Question question) {
        return Choice.builder()
                .question(question)
                .text(text)
                .orderNumber(orderNumber)
                .build();
    }
}
