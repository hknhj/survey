package com.example.survey.domain.survey.dto;

import com.example.survey.domain.survey.domain.Survey;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SurveyResponse {
    private Long surveyId;
    private Long userId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public SurveyResponse(Survey survey) {
        this.surveyId = survey.getSurveyId();
        this.userId = survey.getUser().getUserId();
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.startDate = survey.getStartDate();
        this.endDate = survey.getEndDate();
    }
}
