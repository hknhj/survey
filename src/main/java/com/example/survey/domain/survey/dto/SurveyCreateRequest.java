package com.example.survey.domain.survey.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SurveyCreateRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
