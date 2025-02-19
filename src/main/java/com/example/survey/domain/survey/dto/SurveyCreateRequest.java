package com.example.survey.domain.survey.dto;

import com.example.survey.domain.question.dto.QuestionCreateRequest;
import com.example.survey.domain.survey.domain.Survey;
import com.example.survey.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SurveyCreateRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<QuestionCreateRequest> questions;

    // DTO -> Survey 엔티티 변환
    public Survey toEntity(User user) {
        return Survey.builder()
                .user(user)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
